package expeditors.backend.jconfig;

import expeditors.backend.dao.StudentDAO;
import expeditors.backend.dao.inmemory.InMemoryStudentDAO;
import expeditors.backend.dao.jpa.JPAStudentDAO;
import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan({"expeditors.backend"})
public class LarkUConfig {
   /*
    <bean id="inMemoryStudentDAO" class="expeditors.backend.dao.inmemory.InMemoryStudentDAO"/>
    */

   @Bean
   @Profile("inmem")
   public StudentDAO studentDAO() {
      var dao = new InMemoryStudentDAO();
      List<Student> students = List.of(
            new Student("Arnie", LocalDate.of(1934, 10, 10), "38 0303 83393"),
            Student.builder("Myrtle", LocalDate.of(1987, 10, 10)).phoneNumber("378 00 7585").build(),
            new Student("Sandhay", LocalDate.of(1984, 10, 10), Student.Status.HIBERNATING, "38 0303 83393")
      );
      students.forEach(dao::insert);

      return dao;
   }

   @Bean("studentDAO")
   @Profile("jpa")
   public StudentDAO jpaStudentDAO() {
      var dao = new JPAStudentDAO();
      List<Student> students = List.of(
            new Student("Arnie", LocalDate.of(1934, 10, 10), Student.Status.HIBERNATING, "38 0303 83393"),
            Student.builder("Myrtle", LocalDate.of(1987, 10, 10)).phoneNumber("378 00 7585")
                  .status(Student.Status.FULL_TIME)
                  .build(),
            new Student("Sandhay", LocalDate.of(1984, 10, 10), Student.Status.HIBERNATING, "38 0303 83393")
      );
      students.forEach(dao::insert);

      return dao;
   }

   /*
    <bean id="studentService" class="expeditors.backend.service.StudentService" >
    <property name="studentDAO" ref="inMemoryStudentDAO"/>
    </bean>
    */

   @Bean
   public StudentService studentService(StudentDAO studentDAO) {
      StudentService newService = new StudentService();

      StudentDAO dao = studentDAO;
//      StudentDAO dao = studentDAO();

      newService.setStudentDAO(dao);
      return newService;
   }
}
