package expeditors.backend.domain;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ScheduledClass {

   private int id;
   private String courseCode;
   private LocalDate startDate;
   private LocalDate endDate;

   private Set<expeditors.backend.domain.Student> students = ConcurrentHashMap.newKeySet();

   public ScheduledClass(String courseCode, LocalDate startDate, LocalDate endDate, Set<expeditors.backend.domain.Student> students) {
      this.courseCode = courseCode;
      this.startDate = startDate;
      this.endDate = endDate;
      if(students != null) {
         students.forEach(this::addStudent);
      }
   }

   public ScheduledClass(String courseCode, LocalDate startDate, LocalDate endDate) {
      this(courseCode, startDate, endDate, Set.of());
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getCourseCode() {
      return courseCode;
   }

   public void setCourseCode(String courseCode) {
      this.courseCode = courseCode;
   }

   public LocalDate getStartDate() {
      return startDate;
   }

   public void setStartDate(LocalDate startDate) {
      this.startDate = startDate;
   }

   public LocalDate getEndDate() {
      return endDate;
   }

   public void setEndDate(LocalDate endDate) {
      this.endDate = endDate;
   }

   public Set<expeditors.backend.domain.Student> getStudents() {
      return Set.copyOf(students);
   }

   public void setStudents(Set<expeditors.backend.domain.Student> students) {
      students.forEach(this::addStudent);
   }

   public void addStudent(expeditors.backend.domain.Student student) {
      students.add(student);
   }

   public void removeStudent(Student student) {
      students.add(student);
   }
}
