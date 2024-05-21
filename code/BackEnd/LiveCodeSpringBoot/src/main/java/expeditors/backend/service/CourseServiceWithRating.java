package expeditors.backend.service;

import expeditors.backend.dao.BaseDAO;
import expeditors.backend.domain.Course;
import expeditors.backend.rating.RatingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseServiceWithRating {

    @Autowired
    private BaseDAO<Course> courseDAO;

    @Autowired
    private RatingProvider ratingProvider;

    public CourseServiceWithRating() {
        int stop = 0;
    }


    public Course createCourse(String code, String title) {
        Course course = new Course(code, title);
        course = courseDAO.insert(course);

        return course;
    }

    public Course createCourse(Course course) {
        course = courseDAO.insert(course);

        return course;
    }

    public boolean deleteCourse(int id) {
        Course course = courseDAO.findById(id);
        if (course != null) {
            courseDAO.delete(course);
            return true;
        }
        return false;
    }

    public boolean updateCourse(Course course) {
        return courseDAO.update(course);
    }

    public Course getCourseByCode(String code) {
        List<Course> courses = courseDAO.findAll();
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    public Course getCourse(int id) {
        var course = courseDAO.findById(id);
        ratingProvider.addRatingToCourse(course);

        return course;
    }

    public List<Course> getAllCourses() {
        List<Course> allCourses = courseDAO.findAll();

//        List<Course> withRating = allCourses.stream()
//              .peek(c -> {
//                  ratingProvider.addRatingToCourse(c);
//              }).toList();

        allCourses.forEach(ratingProvider::addRatingToCourse);

        return allCourses;
    }

    public BaseDAO<Course> getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(BaseDAO<Course> courseDAO) {
        this.courseDAO = courseDAO;
    }

}
