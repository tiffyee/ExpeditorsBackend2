package org.ttl.ratingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/courserating.properties")
public class CourseRatingServiceApplication {

   public static void main(String[] args) {
      SpringApplication.run(CourseRatingServiceApplication.class, args);
   }

}
