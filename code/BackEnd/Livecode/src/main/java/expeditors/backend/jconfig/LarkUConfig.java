package expeditors.backend.jconfig;

import expeditors.backend.dao.StudentDAO;
import expeditors.backend.dao.inmemory.InMemoryStudentDAO;
import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"expeditors.backend"})
public class LarkUConfig {
   /*
    <bean id="inMemoryStudentDAO" class="expeditors.backend.dao.inmemory.InMemoryStudentDAO"/>
    */

   @Bean
   public StudentDAO studentDAO() {
      var dao = new InMemoryStudentDAO();
      List<Student> students = List.of(
            new Student("Arnie", LocalDate.of(1934, 10, 10), "38 0303 83393"),
            Student.builder("Myrtle", LocalDate.of(1987, 10, 10)).phoneNumber("378 00 7585").build()
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
   public StudentService studentService() {
      StudentService newService = new StudentService();

      StudentDAO dao = studentDAO();

      newService.setStudentDAO(dao);
      return newService;
   }
}
