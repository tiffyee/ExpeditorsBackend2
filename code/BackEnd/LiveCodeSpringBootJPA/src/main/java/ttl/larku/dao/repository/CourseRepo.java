package ttl.larku.dao.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ttl.larku.domain.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {
   @Query(value = "select * from course s", nativeQuery = true)
   List<Course> fun();
}
