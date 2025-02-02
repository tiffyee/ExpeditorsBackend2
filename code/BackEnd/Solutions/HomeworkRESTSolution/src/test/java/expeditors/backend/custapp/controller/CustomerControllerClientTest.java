package expeditors.backend.custapp.controller;

import expeditors.backend.custapp.domain.Customer;
import jakarta.annotation.PostConstruct;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerClientTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    private String baseUrl;
    private String rootUrl;
    private String oneCustomerUrl;

    @PostConstruct
    public void init() {
        baseUrl = "http://localhost:" + port;
        rootUrl = "/customer";
        oneCustomerUrl = rootUrl + "/{id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
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
        Customer c = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);

        ResponseEntity<Void> result = restClient.post()
                .uri(rootUrl)
                .body(c)
                .retrieve()
                .toBodilessEntity();


        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        String locHdr = result.getHeaders().get("Location").get(0);
        assertNotNull(locHdr);

    }

    @Test
    public void testDeleteCustomerWithGoodId() {
        //First Create a Customer
        Customer c = new Customer("CustomerForDelete", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);

        ResponseEntity<Void> result = restClient.post()
                .uri(rootUrl)
                .body(c)
                .retrieve()
                .toBodilessEntity();

        //Get the location header
        String locHdr = result.getHeaders().get("Location").get(0);
        System.out.println("locHdr: " + locHdr);

        ResponseEntity<Customer> newResult = restClient.get()
                .uri(locHdr)
                .retrieve()
                .toEntity(Customer.class);

        assertEquals(newResult.getStatusCode(), HttpStatus.OK);
        Customer newCustomer = newResult.getBody();
        assertNotNull(newCustomer);

        result = restClient.delete()
                .uri(locHdr)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
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
}
