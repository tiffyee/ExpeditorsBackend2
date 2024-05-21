package ttl.larku.service;

import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.jpahibernate.JPACourseDAO;
import ttl.larku.domain.Course;

import java.util.List;

public class CourseDaoService implements CourseService {

    private JPACourseDAO courseDAO;

    @Override
    public Course createCourse(String code, String title) {
        Course course = new Course(code, title);
        course = courseDAO.insert(course);

        return course;
    }

    @Override
    public Course createCourse(Course course) {
        course = courseDAO.insert(course);

        return course;
    }

    @Override
    public boolean deleteCourse(int id) {
        Course course = courseDAO.findById(id);
        if (course != null) {
            courseDAO.delete(course);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCourse(Course newCourse) {
        Course oldCourse = courseDAO.findById(newCourse.getId());
        if(oldCourse != null) {
            courseDAO.update(newCourse);
            return true;
        }
        return false;
    }

    @Override
    public Course findByCode(String code) {
        Course course = courseDAO.findByCode(code);
        return course;
    }

    @Override
    public Course getCourse(int id) {
        return courseDAO.findById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public BaseDAO<Course> getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(JPACourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public void clear() {
        courseDAO.deleteStore();
        courseDAO.createStore();
    }
}
