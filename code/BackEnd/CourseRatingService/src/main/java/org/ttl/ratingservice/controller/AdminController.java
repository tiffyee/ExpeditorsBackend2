package org.ttl.ratingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.ttl.ratingservice.service.RatingService;

@RestController
@RequestMapping("/admin")
public class AdminController {

   @Autowired
   private RatingService ratingService;

   @GetMapping("/lowerLimit")
   public double getLowerLimit() {
      return ratingService.getLowerLimit();
   }

   @GetMapping("/upperLimit")
   public double getUpperLimit() {
      return ratingService.getUpperLimit();
   }

   @GetMapping("/bothLimits")
   public String getBothLimits() {
      return ratingService.getLowerLimit() + ":" + ratingService.getUpperLimit();
   }

   @PutMapping("/bothLimits/{ll}/{ul}")
   public ResponseEntity<?> setBothLimits(@PathVariable("ll") double lowerLimit,
                                          @PathVariable("ul") double upperLimit) {
      synchronized (this) {
         if(lowerLimit < upperLimit) {
            ratingService.setLowerLimit(lowerLimit);
            ratingService.setUpperLimit(upperLimit);
            return ResponseEntity.noContent().build();
         }
         else {
            return ResponseEntity.badRequest().body("Lower Limit can't be less than Upper Limit: "
                  + lowerLimit + ":" + upperLimit);
         }
      }
   }

   @PutMapping("/lowerLimit/{ll}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void setLowerLimit(@PathVariable("ll") double lowerLimit) {
      if(lowerLimit < getUpperLimit()) {
         ratingService.setLowerLimit(lowerLimit);
      }
   }

   @PutMapping("/upperLimit/{ul}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void setUpperLimit(@PathVariable("ul") double upperLimit) {
      ratingService.setUpperLimit(upperLimit);
   }
}
