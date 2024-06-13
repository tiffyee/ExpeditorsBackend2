package ttl.mie.domain.track.entity;

import jakarta.persistence.PostLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ttl.mie.pricing.PricingProvider;

//@Component
public class TrackEntityEvenListener {

//   @Autowired
   private PricingProvider pricingProvider;

//   @PostLoad
   public void postLoad(TrackEntity entity) {
      System.out.println("TrackEntity.PostLoad called");
      var price = pricingProvider.getPriceByTrackId(entity.getTrackId());
      entity.setPrice(price.toString());
   }
}
