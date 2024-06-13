package ttl.mie.controller;

import java.text.DecimalFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ttl.mie.service.TrackPriceService;

@RestController
public class TrackPriceController {

   private final TrackPriceService priceService;
   private DecimalFormat decimalFormat = new DecimalFormat("0.00");

   public TrackPriceController(TrackPriceService priceService) {
      this.priceService = priceService;
   }

   @GetMapping("/price/{trackId}")
   public ResponseEntity<?> getPriceForTrackId(@PathVariable("trackId") int trackId) {
      var newPrice = priceService.getPriceByTrackId(trackId);

      return ResponseEntity.ok(decimalFormat.format(newPrice));
   }

   @GetMapping("/admin/lowerLImit")
   public ResponseEntity<?> getLowerLimit() {
      return ResponseEntity.ok(priceService.getLowerLimit());
   }

   @PutMapping("/admin/lowerLimit/{ll}")
   public ResponseEntity<?> setLowerLimit(@PathVariable("ll") double lowerLimit) {
      priceService.setLowerLimit(lowerLimit);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/admin/upperLimit")
   public ResponseEntity<?> getUpperLimit() {
      return ResponseEntity.ok(priceService.getUpperLimit());
   }

   @PutMapping("/admin/upperLimit/{ul}")
   public ResponseEntity<?> setUpperLimit(@PathVariable("ul") double upperLimit) {
      priceService.setUpperLimit(upperLimit);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/admin/bothLimits")
   public ResponseEntity<?> getBothLimits() {
      String result = priceService.getLowerLimit() + ":" + priceService.getUpperLimit();

      return ResponseEntity.ok(result);
   }

   @PutMapping("/admin/bothLimits/{ll}/{ul}")
   public ResponseEntity<?> setBothLimits(@PathVariable("ll") double lowerLimit,
                                          @PathVariable("ul") double upperLimit) {
      priceService.setLowerLimit(lowerLimit);
      priceService.setUpperLimit(upperLimit);

      return ResponseEntity.noContent().build();
   }
}
