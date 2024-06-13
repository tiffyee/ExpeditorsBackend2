package ttl.larku.dao.repository;

import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Course;

/**
 * A custom repository that builds on the base interface
 * Only the methods here and in the CustomRepoBase will be a
 * part of the CustomRepo.
 *
 * <p>
 * Look at ttl.larku.dao.CustomRepoTest for usage examples
 *
 *
 * @author whynot
 */
@Repository
@Transactional
public interface CustomRepo extends CustomRepoBase<Course, Integer> {

    public List<Course> findByCode(@Param("code") String code);
}
