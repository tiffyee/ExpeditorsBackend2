package ttl.larku.dao.jdbc;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcDaoTest {

   @Autowired
   private JdbcStudentDAO studentDAO;

   @Test
   public void testFindAll() {
      List<Student> students = studentDAO.findAll();

      System.out.println("students: " + students);
      assertEquals(4, students.size());
   }

   @Test
   public void testFindOneWithGoodId() {
      Student student = studentDAO.findById(1);

      System.out.println("student: " + student);
      assertNotNull(student);
   }

   @Test
   public void testFindOneWithBadId() {
      Student student = studentDAO.findById(1000);

      System.out.println("student: " + student);
      assertNull(student);
   }

   @Test
   @Transactional
   public void testAddNewStudent() {
      Student newStudent = new Student("New Guy", "", LocalDate.parse("1999-08-02"), Student.Status.FULL_TIME);
      Student student = studentDAO.insert(newStudent);

      System.out.println("student: " + student);
      assertTrue(student.getId() > 0);
   }

//   @Test
//   @Transactional
//   public void testDeleteExitingStudent() {
//      Student toDelete = studentDAO.findById(1);
//      assertNotNull(toDelete);
//      boolean result = studentDAO.delete(toDelete);
//
//      assertTrue(result);
//
//      Student shouldNotExist = studentDAO.findById(1);
//      assertNull(shouldNotExist);
//   }

   @Test
   @Transactional
   public void testUpdateExistingStudentShouldSucceed() {
      String newName = "Yahhooo";
      Student toUpdate = studentDAO.findById(1);
      assertNotNull(toUpdate);
      toUpdate.setName(newName);

      boolean result = studentDAO.update(toUpdate);

      assertTrue(result);

      Student updatedStudent = studentDAO.findById(1);
      System.out.println("Updated: " + updatedStudent);
      assertEquals(newName, updatedStudent.getName());
   }

   @Test
   @Transactional
   public void testUpdateNonExistingStudentShouldFail() {
      String newName = "Yahhooo";
      Student toUpdate = studentDAO.findById(1);
      assertNotNull(toUpdate);
      toUpdate.setName(newName);
      toUpdate.setId(1000);

      boolean result = studentDAO.update(toUpdate);

      assertFalse(result);
   }
}
