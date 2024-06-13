package ttl.larku.rating;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRatingProvider {

   @Autowired
   private RatingProvider provider;

   @Value("${rating.provider.url}")
   private String baseUrl;

   @Value("${CLIENT_PASSWORD}")
   private String pw;

   private RestClientRatingProvider whoFor;

   @PostConstruct
   public void init() throws UnrecoverableKeyException, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
      whoFor = new RestClientRatingProvider(baseUrl, pw);
   }

   @Test
   public void testRatingProviderGivesResult() {
      BigDecimal result = provider.getRating(1, "bobby", "password");

      System.out.println("result: " + result);
   }
}
