package expeditors.backend.dao;

import expeditors.backend.service.StudentService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DAOFactoryTest {

   @Test
   public void testDAOFactoryInstances() {
      final StudentDAO dao = DAOFactory.studentDAO();

      assertNotNull(dao);

      StudentDAO dao2 = DAOFactory.studentDAO();

      assertSame(dao, dao2);
      //
   }

   @Test
   public void testDAOFactoryStudentServiceInstances() {
      final StudentService service1 = DAOFactory.studentService();
      final StudentService service2 = DAOFactory.studentService();

      assertSame(service1, service2);
      assertSame(service1.getStudentDAO(), service2.getStudentDAO());
   }
}
