package expeditors.backend.service;

import expeditors.backend.domain.Course;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseRatingServiceTest {

    @Autowired
    private CourseServiceWithRating courseService;


    @Test
    public void testGetCourseWithRating() {
        Course course = courseService.createCourse("Math-101", "Intro to Math");

        Course result = courseService.getCourse(course.getId());

        out.println("result: " + result);
        assertTrue(result.getCode().contains("Math-101"));

        assertTrue(result.getRating() > 0);
    }

    @Test
    public void testGetCourseWithRatingForMultipleCourses() {
        List<Course> result = courseService.getAllCourses();

        out.println("result: " + result);
        result.forEach(c -> {
            out.println(c);
            assertTrue(c.getRating() > 0);
        });

    }

}
