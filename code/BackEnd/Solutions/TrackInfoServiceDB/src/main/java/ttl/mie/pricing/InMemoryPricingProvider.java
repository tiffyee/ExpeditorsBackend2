package ttl.mie.pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!networkpricing")
public class InMemoryPricingProvider implements PricingProvider {

   private double upperLimit;

   public InMemoryPricingProvider(@Value("${pricing.track.upper_limit}") double upperLimit) {
      this.upperLimit = upperLimit;
   }

   @Override
   public BigDecimal getPriceByTrackId(int trackId) {
      var rd = ThreadLocalRandom.current().nextDouble(upperLimit);
      var newPrice = new BigDecimal(String.valueOf(rd)).setScale(2, RoundingMode.CEILING);

      return newPrice;
   }

   public double getUpperLimit() {
      return upperLimit;
   }

   public void setUpperLimit(double upperLimit) {
      this.upperLimit = upperLimit;
   }
}
