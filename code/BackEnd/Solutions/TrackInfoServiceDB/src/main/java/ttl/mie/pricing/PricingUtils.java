package ttl.mie.pricing;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ttl.mie.domain.track.entity.TrackEntity;

@Component
public class PricingUtils {

   @Autowired
   private PricingProvider pricingProvider;

   public TrackEntity addPriceToTrack(TrackEntity track) {
      BigDecimal price = pricingProvider.getPriceByTrackId(track.getTrackId());
      track.setPrice(price.toString());
      return track;
   }
}
