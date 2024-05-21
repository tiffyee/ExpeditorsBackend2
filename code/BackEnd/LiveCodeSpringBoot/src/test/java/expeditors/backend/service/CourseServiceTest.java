package expeditors.backend.service;

import expeditors.backend.domain.Course;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testCreateCourse() {
        Course course = courseService.createCourse("Math-101", "Intro to Math");

        Course result = courseService.getCourse(course.getId());

        assertTrue(result.getCode().contains("Math-101"));
        assertEquals(3, courseService.getAllCourses().size());
    }

    @Test
    public void testDeleteCourse() {
        Course course1 = courseService.createCourse("Math-101", "Intro to Math");
        Course course2 = courseService.createCourse("Phys-101", "Intro to Physics");

        assertEquals(4, courseService.getAllCourses().size());

        boolean result = courseService.deleteCourse(course1.getId());
        assertTrue(result);

        assertEquals(3, courseService.getAllCourses().size());
    }

    @Test
    public void testDeleteNonExistentCourse() {
        Course course1 = courseService.createCourse("Math-101", "Intro to Math");
        Course course2 = courseService.createCourse("Phys-101", "Intro to Physics");

        assertEquals(4, courseService.getAllCourses().size());

        //Non existent Id
        boolean result = courseService.deleteCourse(9999);
        assertFalse(result);

        assertEquals(4, courseService.getAllCourses().size());
    }

    @Test
    public void testUpdateCourse() {
        Course course1 = courseService.createCourse("Math-101", "Intro to Math");

        assertEquals(3, courseService.getAllCourses().size());

        course1.setCode("Math-202");
        boolean result = courseService.updateCourse(course1);
        assertTrue(result);

        List<Course> courses = courseService.getAllCourses();

        assertEquals(3, courses.size());
    }

    @Test
    public void testUpdateNonExistentCourse() {
        Course course1 = courseService.createCourse("Math-101", "Intro to Math");
        assertEquals(3, courseService.getAllCourses().size());

        course1.setCode("Math-202");
        course1.setId(9999);
        boolean result = courseService.updateCourse(course1);
        assertFalse(result);

        List<Course> courses = courseService.getAllCourses();

        assertEquals(3, courses.size());
    }
}
