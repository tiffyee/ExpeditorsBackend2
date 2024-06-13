package ttl.larku.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ttl.larku.dao.repository.CourseRepo;
import ttl.larku.domain.Course;

public class CourseRepoService implements CourseService {

    @Autowired
    private CourseRepo courseDAO;

    public Course createCourse(String code, String title) {
        Course course = new Course(code, title);
        course = courseDAO.save(course);

        return course;
    }

    public Course createCourse(Course course) {
        course = courseDAO.save(course);

        return course;
    }

    public boolean deleteCourse(int id) {
        Course course = courseDAO.findById(id).orElse(null);
        if (course != null) {
            courseDAO.delete(course);
            return true;
        }
        return false;
    }

    public boolean updateCourse(Course course) {
        Course oldCourse = courseDAO.findById(course.getId()).orElse(null);
        if(oldCourse != null) {
            courseDAO.save(course);
            return true;
        }
        return false;
    }

    public Course findByCode(String code) {
        Course course = courseDAO.findByCode(code).orElse(null);
        return course;
    }

    public Course getCourse(int id) {
        return courseDAO.findById(id).orElse(null);
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public CourseRepo getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(CourseRepo courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void clear() {
        courseDAO.deleteAll();
        //courseDAO.createStore();
    }
}
