package expeditors.backend.rating;

import expeditors.backend.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("network")
public class TestableNetworkRatingProvider implements RatingProvider {

   private int ratingLowerLimit = 1;
   private int ratingUpperLimit = 10;

   @Autowired
   private RestClientHolder restClientHolder;


   @Override
   public void addRatingToCourse(Course course) {
      int rating = restClientHolder.getRating(course.getId());

      course.setRating(rating);
   }
}
