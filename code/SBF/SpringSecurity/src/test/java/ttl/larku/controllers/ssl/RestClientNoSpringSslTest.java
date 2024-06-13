package ttl.larku.controllers.ssl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import ttl.larku.controllers.rest.RestResultWrapper;
import ttl.larku.domain.Student;
import ttl.larku.jconfig.client.ClientSSLConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * One way to make RestClient calls succeed is to make it depend on a RestTemplate that
 * has SSL configured, like we do in .../security/clientClientSSLConfig.
 * <p>
 * The other way is to add the public key of our self-signed certificate (larkUKeyfile.p12)
 * to our jdks trusted certificate list (jdk_path/lib/security/cacerts).  Instructions on
 * how to do that are in the README.SSL file.
 * <p>
 * _Note that this test will fail if the certificate has not been added into the cacerts
 * file, since here we are working with just a RestClient that has no special ssl config
 * applied to it.
 */
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestClientNoSpringSslTest {


   private String password = System.getenv("CLIENT_PASSWORD");

   private RestClient restClient;

   String baseUrl;
   //String rootUrl = baseUrl + "/adminrest/student";
   String rootUrl;

   ClientSSLConfig clientSSLConfig = new ClientSSLConfig();

   @BeforeAll
   public void beforeAll() throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
      String basicAuthHeader = "basic " + Base64.getEncoder().encodeToString(("bobby" + ":" + password).getBytes());

      var builder = clientSSLConfig.sslRestClientBuilder(password);
//
//      this.restClient = RestClient.builder()
      this.restClient = builder
            .baseUrl("https://localhost:10043/rating")
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", basicAuthHeader)
            .build();

   }

   @Test
   public void testGetCourseRating() {
      ResponseEntity<BigDecimal> response = restClient.get()
            //.uri("https://localhost:10043/rating/{id}", 2)
            .uri("/{id}", 2)
            .retrieve()
            .toEntity(BigDecimal.class);

      assertEquals(HttpStatus.OK, response.getStatusCode());

      BigDecimal rating = response.getBody();

      System.out.println("rating: " + rating);
   }

   @Test
   public void testGetAll() {
      //This is the Spring REST mechanism to create a paramterized type
      ParameterizedTypeReference<RestResultWrapper<List<Student>>> ptr =
            new ParameterizedTypeReference<RestResultWrapper<List<Student>>>() {
            };

      ResponseEntity<RestResultWrapper<List<Student>>> response = restClient.get()
            .uri("https://localhost:8443/adminrest/student")
            .retrieve()
            .toEntity(ptr);

      assertEquals(HttpStatus.OK, response.getStatusCode());

      RestResultWrapper<List<Student>> wrapper = response.getBody();
      assertEquals(RestResultWrapper.Status.Ok, wrapper.getStatus());

      List<Student> students = wrapper.getEntity();

      System.out.println("student: " + students);
   }
}
