package expeditors.backend.app;

import expeditors.backend.dao.DAOFactory;
import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
import java.time.LocalDate;
import java.util.List;

public class StudentApp {

   StudentService service = DAOFactory.studentService();
   public static void main(String[] args) {
      StudentApp sa = new StudentApp();
      sa.go();
   }

   public void go() {
      postAStudent();
      getAllStudents();
   }

   public void postAStudent() {
      Student student = new Student("Franky", LocalDate.of(2000, 10, 10), "a@b.com");

      Student insertedStudent = service.createStudent(student);

      List<Student> students = service.getStudents();
      System.out.println("student: " + students.size());

      students.forEach(System.out::println);
   }

   public void getAllStudents() {
//      StudentService service = new StudentService();

      List<Student> students = service.getStudents();
      System.out.println("Get students: " + students.size());

      students.forEach(System.out::println);

   }
}

