package expeditors.backend.service;

import expeditors.backend.domain.Student;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {LarkUConfig.class})
//@ActiveProfiles("inmem")

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentServiceTest {

   @Autowired
   private StudentService studentService;

   @Autowired
   private ApplicationContext context;

   @BeforeEach
   public void beforeEach() {
//     studentService = new StudentService();
   }

   @Test
   public void testStudentServiceInsert() {
      Student student = new Student("Franky", LocalDate.of(2000, 10, 10));

      Student insertedStudent = studentService.createStudent(student);

      System.out.println("student: " + insertedStudent.toString());
      assertNotNull(insertedStudent);
      assertEquals(4, student.getId());
   }

   @Test
   public void testDeleteExistingStudent() {
      Student student = new Student("Franky", LocalDate.of(2000, 10, 10));


      Student insertedStudent = studentService.createStudent(student);

      System.out.println("student: " + insertedStudent);
      assertNotNull(insertedStudent);

      boolean deleted = studentService.deleteStudent(insertedStudent.getId());
      assertTrue(deleted);
   }

   @Test
   public void testDeleteNonExistingStudent() {
      boolean result = studentService.deleteStudent(9999);
      assertFalse(result);
   }

   @Test
   public void testGetStudentByName() {
      List<Student> students = List.of(
            new Student("Bulbul", LocalDate.of(1934, 10, 10), "38 0303 83393"),
            Student.builder("Chandni", LocalDate.of(1987, 10, 10)).phoneNumber("378 00 7585").build()
      );
      students.forEach(studentService::createStudent);

      List<Student> result = studentService.getStudentsByName("Bulbul");
      assertEquals(1, result.size());
      assertTrue(result.get(0).getName().contains("Bulbul"));
   }

   @Test
   public void addPhoneNumberToExistingStudent() {
      List<Student> students = List.of(
            new Student("Bulbul", LocalDate.of(1934, 10, 10), "38 0303 83393"),
            Student.builder("Chandni", LocalDate.of(1987, 10, 10)).phoneNumber("378 00 7585").build()
      );
      students.forEach(studentService::createStudent);

      Student student = studentService.getStudent(1);
      student.addPhoneNumber("bleb@xyz.org");
      boolean result = studentService.updateStudent(student);
      assertTrue(result);

      Student changedStudent = studentService.getStudent(student.getId());
      assertEquals(2, changedStudent.getPhoneNumbers().size());
   }
}
