package ttl.larku.jconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.jpahibernate.JPAClassDAO;
import ttl.larku.dao.jpahibernate.JPACourseDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.ClassService;
import ttl.larku.service.CourseService;
import ttl.larku.service.RegistrationService;
import ttl.larku.service.StudentService;

@Configuration
@PropertySource({"classpath:/backend.properties"})
@ComponentScan("ttl.larku")
public class LarkUConfig {

    private LarkUTestDataConfig testDataProducer = new LarkUTestDataConfig();
    @Bean
    @Profile("development")
    public BaseDAO<Student> studentDAO() {
        return inMemoryStudentDAO();
    }

    @Bean(name = "studentDAO")
    @Profile("production")
    public BaseDAO<Student> studentDAOJpa() {
        return jpaStudentDAO();
    }

    @Bean
    @Profile("development")
    public BaseDAO<Course> courseDAO() {
        return inMemoryCourseDAO();
    }

    @Bean(name = "courseDAO")
    @Profile("production")
    public BaseDAO<Course> courseDAOJPA() {
        return jpaCourseDAO();
    }

    @Bean
    @Profile("development")
    public BaseDAO<ScheduledClass> classDAO() {
        return inMemoryClassDAO();
    }

    @Bean(name = "classDAO")
    @Profile("production")
    public BaseDAO<ScheduledClass> classDAOJPA() {
        return jpaClassDAO();
    }

    @Bean
    public CourseService courseService() {
        CourseService cc = new CourseService();
        cc.setCourseDAO(courseDAO());

        return cc;
    }

    @Bean
    public ClassService classService() {
        ClassService cs = new ClassService();
        cs.setClassDAO(classDAO());
        cs.setCourseService(courseService());
        return cs;
    }


    //Injection with an argument.  This approach works when running from
    //the IDE and the command line.  As opposed to @Autowired at the field
    //level, as above.
    @Bean
    public RegistrationService registrationService(StudentService studentService) {
        RegistrationService rs = new RegistrationService();
        rs.setStudentService(studentService);
        rs.setCourseService(courseService());
        rs.setClassService(classService());

        return rs;
    }

    public BaseDAO<Student> inMemoryStudentDAO() {
        BaseDAO<Student> bs = testDataProducer.studentDAOWithInitData();
        return bs;
    }

    public BaseDAO<Student> jpaStudentDAO() {
        BaseDAO<Student> bs = testDataProducer.studentJPADAOWithInitData();
        return bs;
    }

    public BaseDAO<Course> inMemoryCourseDAO() {
        BaseDAO<Course> dao = testDataProducer.courseDAOWithInitData();
        return dao;
    }

    public BaseDAO<Course> jpaCourseDAO() {
        return new JPACourseDAO();
    }

    public BaseDAO<ScheduledClass> inMemoryClassDAO() {
        return testDataProducer.classDAOWithInitData();
//        return new InMemoryClassDAO();
    }

    public BaseDAO<ScheduledClass> jpaClassDAO() {
        return new JPAClassDAO();
    }
}
