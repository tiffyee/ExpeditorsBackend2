package ttl.larku.dao.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.larku.domain.Student;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StudentRepoTest {

   @Autowired
   private StudentRepo studentRepo;

   @Test
   public void testGetAll() {
      List<Student> students = studentRepo.findAll();

      out.println("num results: " + students.size());
//      students.forEach(out::println);

      assertEquals(4, students.size());
   }
}
