package org.ttl.ratingservice.controller;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating")
public class CourseRatingServiceController {

   private int lowerLimit = 1;
   private int higherLimit = 10;

   @GetMapping("/{id}")
   public int getRatingForCourse(@PathVariable("id") int id) {
      int rating = ThreadLocalRandom.current().nextInt(lowerLimit, higherLimit);

      return rating;
   }
}
