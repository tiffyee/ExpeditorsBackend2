package ttl.larku.controllers.ssl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.web.client.RestClient;
import ttl.larku.jconfig.client.ClientSSLConfig;
import ttl.larku.jconfig.client.RestClientFactory;
import ttl.larku.sql.SqlScriptBase;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("expensive")
@EnabledIf(expression = "#{environment.matchesProfiles('ssltest')}", loadContext = true)
public class RestClientSSLTest extends SqlScriptBase {

   @LocalServerPort
   private int port; // = 8443;

   @Autowired
   private ObjectMapper mapper;

   private RestClient restClient;

   @Value("${CLIENT_PASSWORD}")
   private String password;

   private String baseUrl;
   private String rootUrl;
   private String oneStudentUrl;

   RestClient ratingClient;

   @Autowired
   ClientSSLConfig clientSSLConfig;

   @Autowired
   private RestClientFactory clientFactory;

   @Value("${DT}")
   private String discOggsToken;

   @PostConstruct
   public void init() throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
      baseUrl = "https://localhost:10043/rating";

      String authHeader = "Basic " + Base64.getEncoder()
            .encodeToString("bobby:password".getBytes());

//      var builder = clientSSLConfig.sslRestClientBuilder("password");
//        this.restClient = builder

        this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", authHeader)
            .build();
      //We have to use the non "bundle" version of the clients if we don't
      //put the self-signed certificates to our JDK (see README.SSL)
//      this.restClient = clientFactory.sslClientFromRestClient(baseUrl, "bobby", password);

      //We can use the "...clientFromBundle" clients Only IF we have added the
      //self-signed certificate of the client to the trustStore (e.g. 'cacerts' file) of the JDK
      //running this code. (see README.SSL)
//        this.restClient = clientFactory.sslClientFromBundle(baseUrl, "bobby", password);
      this.ratingClient = clientFactory.sslClientFromBundle(baseUrl, "bobby", password);
   }


   @BeforeEach
   public void setup() {
   }

   @Test
   public void testCallCourseRatingServiceRestClientAndStraighHttpsURL() {
      var myRatingClient = this.restClient;
//        var myRatingClient= clientFactory.sslClientFromBundle("https://localhost:10043/rating", "bobby", password);
//      var myRatingClient= clientFactory.sslClientFromRestClient("https://localhost:10043/rating", "bobby", password);
//        var myRatingClient= clientFactory.sslFromRestTemplate("https://localhost:10043/rating", "bobby", password);
      var response = myRatingClient.get()
            .uri("/{id}", 2)
            .retrieve()
            .toEntity(BigDecimal.class);

      var result = response.getBody();

      System.out.println("Result: " + result);


      assertEquals(HttpStatus.OK, response.getStatusCode());
   }

   @Test
   public void testCallCourseToServiceNotInOurTrustStore() {
      String uri = "https://api.discogs.com/database/search?q=Henry Threadgill's Zooid&release_title=Tomorrow Sunny / The Revelry, Spp";
      var myRatingClient= this.restClient;
//      var myRatingClient= clientFactory.sslClientFromBundle(uri, "bobby", password);

      var response = myRatingClient.get()
            .uri(uri)
            .header("Authorization", discOggsToken)
            .header("User-Agent", "TheThirdLane/1.0 +https://thethirdlane.com")
            .retrieve()
            .toEntity(String.class);

      var result = response.getBody();

      System.out.println("Result: " + result);


      assertEquals(HttpStatus.OK, response.getStatusCode());
   }
}
