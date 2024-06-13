package org.ttl.ratingservice.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ttl.ratingservice.service.RatingService;

@RestController
@RequestMapping("/rating")
public class CourseRatingServiceController {

   @Autowired
   private RatingService ratingService;

   @GetMapping("/{id}")
   public BigDecimal getRatingForCourse(@PathVariable("id") int id) {
      var rd = ThreadLocalRandom.current().nextDouble(ratingService.getLowerLimit(), ratingService.getUpperLimit());
      var rating = new BigDecimal(String.valueOf(rd)).setScale(1, RoundingMode.CEILING);

      return rating;
   }
}
