package ttl.mie.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TrackPriceService {

   private double upperLimit;
   private double lowerLimit;

   public TrackPriceService(@Value("${pricing.track.lower_limit}") double lowerLimit,
                            @Value("${pricing.track.upper_limit}") double upperLimit ) {
      this.upperLimit = upperLimit;
      this.lowerLimit = lowerLimit;
   }

   public BigDecimal getPriceByTrackId(int trackId) {
      var rd = ThreadLocalRandom.current().nextDouble(lowerLimit, upperLimit);
      var newPrice = new BigDecimal(String.valueOf(rd)).setScale(2, RoundingMode.CEILING);

      return newPrice;
   }

   public double getLowerLimit() {
      return lowerLimit;
   }

   public void setLowerLimit(double lowerLimit) {
      this.lowerLimit = lowerLimit;
   }

   public double getUpperLimit() {
      return upperLimit;
   }

   public void setUpperLimit(double upperLimit) {
      this.upperLimit = upperLimit;
   }
}
