package ttl.larku.service;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Course;
import ttl.larku.sql.SqlScriptBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Tag("integration")
public class CourseServiceWithRatingTest extends SqlScriptBase {

    @Autowired
    private CourseServiceWithRating courseService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testRatingIsReturnedWithCourse() {
        List<Course> courses = courseService.getAllCourses();

        courses.forEach(course -> assertTrue(new BigDecimal("0.0").compareTo(course.getRating()) <= 0));
    }
}
