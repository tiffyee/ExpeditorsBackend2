package expeditors.backend;

import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class LiveCodeSpringBootApplication {

   public static void main(String[] args) {
      SpringApplication.run(LiveCodeSpringBootApplication.class, args);
   }
}

@Component
class MyRunner implements CommandLineRunner
{
   @Autowired
   private StudentService studentService;

   @Override
   public void run(String... args) throws Exception {
      System.out.println("Here we go with Spring Boot");

      List<Student> students = studentService.getStudents();
      System.out.println("students: " + students.size());
      System.out.println(students);
   }
}
