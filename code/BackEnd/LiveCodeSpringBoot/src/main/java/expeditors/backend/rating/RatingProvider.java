package expeditors.backend.rating;

import expeditors.backend.domain.Course;

public interface RatingProvider {
   void addRatingToCourse(Course course);
}
