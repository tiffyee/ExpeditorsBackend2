package ttl.larku.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttl.larku.domain.CourseVersioned;

@Repository
public interface CourseVersionedRepo extends JpaRepository<CourseVersioned, Integer> {
}
