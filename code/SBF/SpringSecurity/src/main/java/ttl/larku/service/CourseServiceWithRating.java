package ttl.larku.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;
import ttl.larku.rating.RatingProvider;


@Service
@Primary
public class CourseServiceWithRating implements CourseService {


    @Value("${CLIENT_PASSWORD}")
    private String pw;

    private static String adminUser = "bobby";

    private BaseDAO<Course> courseDAO;
    private final RatingProvider ratingProvider;

//    private RestClient restClient;

    public CourseServiceWithRating(BaseDAO<Course> courseDAO,
                                   RatingProvider ratingProvider) {
        this.courseDAO = courseDAO;
        this.ratingProvider = ratingProvider;
//        var baseUrl = "http://localhost:10001/rating";
//        this.restClient = RestClient.builder()
//              .baseUrl(baseUrl)
//              .defaultHeader("Accept", "application/json")
//              .defaultHeader("Content-Type", "application/json")
//              .build();
    }

    public Course createCourse(String code, String title) {
        Course course = new Course(code, title);
        course = createCourse(course);

        return course;
    }

    public Course createCourse(Course course) {
        course = courseDAO.create(course);

        return course;
    }

    public boolean deleteCourse(int id) {
        Course course = courseDAO.get(id);
        if (course != null) {
            courseDAO.delete(course);
            return true;
        }
        return false;
    }

    public boolean updateCourse(Course newCourse) {
        Course oldCourse = courseDAO.get(newCourse.getId());
        if(oldCourse != null) {
            courseDAO.update(newCourse);
            return true;
        }
        return false;
    }

    @Override
    public Course findByCode(String code) {
        List<Course> courses = courseDAO.findBy(c -> c.getCode().contains(code));
        if(!courses.isEmpty()) { //take the first one
            var course = courses.get(0);
            course.setRating(getRatingFromService(course));
        }
        return !courses.isEmpty() ? courses.get(0) : null;
    }


    public Course getCourse(int id) {
        var course = courseDAO.get(id);
        if(course != null) {
            course.setRating(getRatingFromService(course));
        }

        return course;
    }

    public List<Course> getAllCourses() {
        var courses = courseDAO.getAll();
        courses.forEach(c -> c.setRating(getRatingFromService(c)));

        return courses;
    }

    public BaseDAO<Course> getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(BaseDAO<Course> courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void clear() {
        courseDAO.deleteStore();
        courseDAO.createStore();
    }

    private BigDecimal getRatingFromService(Course course) {
        var result = ratingProvider.getRating(course.getId(), adminUser, pw);
        return result;
    }
}
