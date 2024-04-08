package expeditors.backend.custapp.controller.ssl;

import expeditors.backend.custapp.domain.Customer;
import expeditors.backend.custapp.domain.PhoneNumber;
import expeditors.backend.commonconfig.client.RestClientFactory;
import expeditors.backend.custapp.sql.ScriptFileProperties;
import expeditors.backend.custapp.sql.SqlScriptBase;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("expensive")
@EnabledIf(expression = "#{environment.matchesProfiles('ssltest')}", loadContext = true)
public class CustomerRepoControllerClientSslTest extends SqlScriptBase {

    @LocalServerPort
    private int port;

    @Autowired
    private RestClientFactory clientFactory;
    private RestClient restClient;

    private boolean initDB = true;

//    @Autowired
////    @Qualifier("sslRestTemplate")
//    @Qualifier("fakessltemplate")
//    private RestTemplate sslRestTemplate;

    private String baseUrl;
    private String rootUrl;
    private String oneCustomerUrl;
    private String custWithPhonesUrl;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ScriptFileProperties props;

    @Value("${client.user}") String user;
    @Value("${client.password}") String password;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void uberInit() {
        baseUrl = "https://localhost:" + port;
        rootUrl = "/customerrepo";
        oneCustomerUrl = rootUrl + "/{id}";
        custWithPhonesUrl = rootUrl + "/{id}?phones=true";

        this.restClient = clientFactory.get(baseUrl, user, password);
    }


    @BeforeEach
    public void init() throws SQLException {
        if(initDB) {
            this.runSqlScriptsOnce();
            initDB = false;
        }
    }



    @Test
    public void testGetAll() {
        //This is the Spring REST mechanism to create a paramterized type
        ParameterizedTypeReference<List<Customer>> ptr =
                new ParameterizedTypeReference<List<Customer>>() {};

        ResponseEntity<List<Customer>> response = restClient.get()
                .uri(rootUrl)
                .retrieve()
                .toEntity(ptr);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetOneWithGoodId() {
        ResponseEntity<Customer> response = restClient.get()
                .uri(oneCustomerUrl, 1)
                .retrieve()
                .toEntity(Customer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Customer customer = response.getBody();

        System.out.println(customer);
    }

    @Test
    public void testGetOneWithBadId() {
        ResponseEntity<?> response = restClient.get()
                .uri(oneCustomerUrl, 1000)
                .retrieve()
                .onStatus(code -> code == HttpStatus.NOT_FOUND, (req, resp) -> {
                })
                .toBodilessEntity();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testPostCustomer() throws URISyntaxException {
        //Customer c = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        Customer c = new Customer("New Customer", LocalDate.of(1960, 6, 9),
                Customer.Status.NORMAL, new PhoneNumber("999 99 9999", PhoneNumber.Type.WORK));

        ResponseEntity<Void> result = restClient.post()
                .uri(rootUrl)
                .body(c)
                .retrieve()
                .toBodilessEntity();


        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        String locHdr = result.getHeaders().get("Location").get(0);
        assertNotNull(locHdr);

        initDB = true;
    }

    @Test
    public void testDeleteCustomerWithGoodId() {
        ResponseEntity<?> result = restClient.delete()
                .uri(oneCustomerUrl, 2)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        initDB = true;
    }

    @Test
    public void testDeleteCustomerWithBadId() {
        ResponseEntity<?> result = restClient.delete()
                .uri(oneCustomerUrl, 1000)
                .retrieve()
                .onStatus(code -> code == HttpStatus.NOT_FOUND, (req, resp) -> {
                })
                .toBodilessEntity();


        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateCustomerWithGoodId() {
        Customer customer = restClient.get()
                .uri(oneCustomerUrl, 1)
                .retrieve()
                .body(Customer.class);

        customer.setName("Firlan");

        ResponseEntity<?> result = restClient.put()
                .uri(rootUrl)
                .body(customer)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        customer = restClient.get()
                .uri(oneCustomerUrl, 1)
                .retrieve()
                .body(Customer.class);

        assertEquals("Firlan", customer.getName());

        initDB = true;
    }

    @Test
    public void testUpdateCustomerWithBadId() {
        Customer customer = restClient.get()
                .uri(oneCustomerUrl, 1)
                .retrieve()
                .body(Customer.class);

        customer.setName("Firlan");
        customer.setId(1000);

        ResponseEntity<?> result = restClient.put()
                .uri(rootUrl)
                .body(customer)
                .retrieve()
                .onStatus(st -> st == HttpStatus.NOT_FOUND, (req, resp) -> {})
                .toBodilessEntity();

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testAddPhoneNumberToExistingCustomer() {
        String number = "3839 929 92922";
        String type = "HOME";
        String addPhoneUrl = oneCustomerUrl + "/{phone}/{type}";

        ResponseEntity<Void> addResult = restClient.post()
                .uri(addPhoneUrl, 1, number, type)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, addResult.getStatusCode());

        Customer customer = restClient.get()
                .uri(custWithPhonesUrl, 1)
                .retrieve()
                .body(Customer.class);

        assertEquals(3, customer.getPhoneNumbers().size());

        initDB = true;
    }

    @Test
    public void testDeletePhoneNumberFromExistingCustomer() {
        String number = "222 333-5555";
        String type = "WORK";
        String addPhoneUrl = oneCustomerUrl + "/{phone}/{type}";

        ResponseEntity<Void> addResult = restClient.delete()
                .uri(addPhoneUrl, 1, number, type)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, addResult.getStatusCode());

        Customer customer = restClient.get()
                .uri(custWithPhonesUrl, 1)
                .retrieve()
                .body(Customer.class);

        assertEquals(1, customer.getPhoneNumbers().size());

        initDB = true;
    }
}
