package ttl.larku.jconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.jpahibernate.JPAClassDAO;
import ttl.larku.dao.jpahibernate.JPACourseDAO;
import ttl.larku.dao.jpahibernate.JPAStudentDAO;
import ttl.larku.dao.repository.StudentRepo;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

@Configuration
@Profile("production")
public class LarkURealDataConfig {

    @Bean
    public BaseDAO<Student> studentDAO(JPAStudentDAO studentRepoDAO) {
        //return new JPAStudentDAO();
        return studentRepoDAO;
    }

    @Bean
    public BaseDAO<Course> courseDAO() {
        return new JPACourseDAO();
    }

    @Bean
    public BaseDAO<ScheduledClass> classDAO() {
        return new JPAClassDAO();
    }
}