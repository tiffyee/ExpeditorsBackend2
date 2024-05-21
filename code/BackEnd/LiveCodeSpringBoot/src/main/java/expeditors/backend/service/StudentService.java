package expeditors.backend.service;

import expeditors.backend.dao.StudentDAO;
import expeditors.backend.domain.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/*
StudentService should allow users to perform basic create, update, delete operations on Students to a store.
2. StudentService should allow users to retrieve a Student by ID
3. StudentService should allow users to retrieve all Students.
 */
public class StudentService {

   List<String> lstr = new ArrayList<>();

   private StudentDAO studentDAO;

   private int numCalls;
   private AtomicInteger betterCounter = new AtomicInteger(1);

   public Student createStudent(Student student) {
      //Save the student to a Store == what kind of store?
      Student insertedStudent = studentDAO.insert(student);

      return insertedStudent;
      //Return a result
   }

   public boolean deleteStudent(int id) {
      var result =  studentDAO.delete(id);
      //Lots of code here

      return result;
   }

   public boolean updateStudent(Student student) {
      return studentDAO.update(student);
   }

   public Student getStudent(int id) {
      return studentDAO.findById(id);
   }

   public List<Student> getStudents() {
      return studentDAO.findAll();
   }

   public List<Student> getStudentsByName(String name) {
      return studentDAO.findByName(name);
   }

   //   public JPAStudentDAO getStudentDAO() {
//   public InMemoryStudentDAO getStudentDAO() {
   public StudentDAO getStudentDAO() {
      return studentDAO;
   }

   public void setStudentDAO(StudentDAO studentDAO) {
      this.studentDAO = studentDAO;
   }

   public List<Student> getByQueryParams(Map<String, String> queryParams) {
      Predicate<Student> finalPred = null;

      for(var entry : queryParams.entrySet()) {
         var key = entry.getKey();
         var value = entry.getValue();

         if(key.equals("name")) {
            Predicate<Student> tmp = (s) -> s.getName().equals(value);

            finalPred = finalPred == null ? tmp : finalPred.or(tmp);
         }else if(key.equals("status")) {
           var statusEnum = Student.Status.valueOf(value);
           Predicate<Student> tmp = (s) -> s.getStatus().equals(statusEnum);

            finalPred = finalPred == null ? tmp : finalPred.or(tmp);
         }else if(key.equals("dob")) {
            var dobDate = LocalDate.parse(value);
            Predicate<Student> tmp = (s) -> s.getDob().equals(dobDate);

            finalPred = finalPred == null ? tmp : finalPred.or(tmp);
         }
      }

      finalPred = finalPred != null ? finalPred : (s) -> true;

      List<Student> result = getStudents().stream()
            .filter(finalPred)
            .toList();

      return result;
   }
}