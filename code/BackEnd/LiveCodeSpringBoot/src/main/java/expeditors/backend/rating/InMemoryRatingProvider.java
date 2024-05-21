package expeditors.backend.rating;

import expeditors.backend.domain.Course;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("localrating")
public class InMemoryRatingProvider implements RatingProvider {

   @Override
   public void addRatingToCourse(Course course) {
      int rating = 10;
      course.setRating(rating);
   }
}
