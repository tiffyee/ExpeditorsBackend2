package expeditors.backend.app;

import expeditors.backend.domain.Course;
import expeditors.backend.domain.Student;
import expeditors.backend.jconfig.LarkUConfig;
import expeditors.backend.service.CourseService;
import expeditors.backend.service.StudentService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDemo {

   public static void main(String[] args) {
      SpringDemo springDemo = new SpringDemo();
      //springDemo.goStudent();
      springDemo.goCourse();
   }

   public void goStudent() {
      //ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

      ApplicationContext context = new AnnotationConfigApplicationContext(LarkUConfig.class);

      StudentService ss = context.getBean("studentService", StudentService.class);
      StudentService ss2 = context.getBean("studentService", StudentService.class);

      List<Student> students = ss.getStudents();
      System.out.println("students: " + students.size());
      System.out.println(students);
   }

   public void goCourse() {
      //ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

      ApplicationContext context = new AnnotationConfigApplicationContext(LarkUConfig.class);

      CourseService ss = context.getBean("courseService", CourseService.class);

      List<Course> courses = ss.getAllCourses();
      System.out.println("course: " + courses.size());
      System.out.println(courses);
   }
}
