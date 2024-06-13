package ttl.mie.pricing;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"postgres", "production",  "networkpricing"})
public class TestRestClientPricingProvider {

   @Autowired
   private PricingProvider pricingProvider;

   //*Requires* the TrackPricingService to be
   //running on Port 10002
   @Test
   public void testGetPriceFromNetworkService() {
      BigDecimal price = pricingProvider.getPriceByTrackId(1);

      System.out.println("price: " + price);
   }

}
