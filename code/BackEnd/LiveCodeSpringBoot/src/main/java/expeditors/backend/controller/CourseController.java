package expeditors.backend.controller;

import expeditors.backend.domain.Course;
import expeditors.backend.service.CourseServiceWithRating;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

   @Autowired
   private CourseServiceWithRating courseService;

   @GetMapping
   public List<Course> getAllCourses() {
      List<Course> courses = courseService.getAllCourses();

      return courses;

   }
}
