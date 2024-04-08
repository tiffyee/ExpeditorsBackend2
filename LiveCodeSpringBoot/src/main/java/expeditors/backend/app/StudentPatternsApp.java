package expeditors.backend.app;

import expeditors.backend.dao.DAOFactory;
import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
import java.time.LocalDate;
import java.util.List;

public class StudentPatternsApp {

   public static void main(String[] args) {
      StudentPatternsApp app = new StudentPatternsApp();
      app.go();
   }

   public void go() {
     postAStudent();
     getAllStudents();
   }

   //StudentService ss = new StudentService();
   StudentService ss = DAOFactory.studentService();
   public void postAStudent() {
      Student student = new Student("Bulbul", LocalDate.of(1934, 10, 10), "a@b.com");

      Student newStudent = ss.createStudent(student);

      List<Student> students = ss.getStudents();
      System.out.println("student: " + students.size());
      students.forEach(System.out::println);
   }

   public void getAllStudents() {
//      StudentService ss = new StudentService();


      List<Student> students = ss.getStudents();
      System.out.println("student: " + students.size());
      students.forEach(System.out::println);
   }
}
