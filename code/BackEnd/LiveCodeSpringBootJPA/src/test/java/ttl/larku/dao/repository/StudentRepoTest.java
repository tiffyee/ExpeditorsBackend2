package ttl.larku.dao.repository;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.PhoneNumber;
import ttl.larku.domain.Student;
import ttl.larku.sql.SqlScriptBase;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@Sql(scripts = {"/sql/postgres/3-postgres-larku-schema.sql", "/sql/postgres/4-postgres-larku-data.sql"},
//executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
@Rollback(false)
public class StudentRepoTest extends SqlScriptBase {

   @Autowired
   private StudentRepo studentRepo;

   @Autowired
   private PhoneNumberRepo phoneNumberRepo;

   @BeforeEach
   public void beforeEach() throws SQLException {
      runSqlScriptsOnce();
   }

   @Test
   public void testGetStudents() {
      List<Student> students = studentRepo.findAll();

      out.println("students: " + students.size());
      assertEquals(4, students.size());

      //students.forEach(out::println);
   }

   @Test
   public void testGetStudentsWithPhoneNumber() {
      List<Student> students = studentRepo.findStudentWithPhones();

      out.println("students: " + students.size());
      students.forEach(out::println);

      assertEquals(4, students.size());
   }

   @Test
   public void testInsertStudentAndPhoneNumberIndivdually() {
      Student student = new Student("Firdaus", "38 9002 28929", Student.Status.HIBERNATING);

      PhoneNumber pn = student.getPhoneNumbers().get(0);
//      pn = phoneNumberRepo.save(pn);


      student = studentRepo.save(student);

      out.println("student: " + student);
   }

   @Test
   public void testInsertStudentWithPhoneNumberTogether() {
      Student student = new Student("Firdaus", "38 9002 28929", Student.Status.HIBERNATING);

      student = studentRepo.save(student);

      out.println("student: " + student);

      Student newStudent = studentRepo.findById(student.getId()).orElse(null);
      assertNotNull(newStudent);
   }

   @Test
   public void testInsertAgain() {
      Student student = new Student("Firdaus", "38 9002 28929", Student.Status.HIBERNATING);

      student = studentRepo.save(student);

      out.println("student: " + student);
   }

   @Test
   public void testUpdate() {
      Student student = studentRepo.findById(1).orElse(null);
      assertNotNull(student);

      out.println("studentname: " + student.getName());

      student.setName("Phil");
   }
}
