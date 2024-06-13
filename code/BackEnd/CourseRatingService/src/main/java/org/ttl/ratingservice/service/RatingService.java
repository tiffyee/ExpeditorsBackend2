package org.ttl.ratingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

   @Value("${ttl.courseRating.lowerLimit}")
   private double lowerLimit;

   @Value("${ttl.courseRating.upperLimit}")
   private double upperLimit;

   public double getLowerLimit() {
      return lowerLimit;
   }

   public void setLowerLimit(double lowerLimit) {
      int value = 10;

      this.lowerLimit = lowerLimit;
   }

   public double getUpperLimit() {
      return upperLimit;
   }

   public void setUpperLimit(double upperLimit) {
      this.upperLimit = upperLimit;
   }
}
