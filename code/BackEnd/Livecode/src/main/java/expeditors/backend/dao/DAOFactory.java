package expeditors.backend.dao;

import expeditors.backend.dao.inmemory.InMemoryStudentDAO;
import expeditors.backend.dao.jpa.JPAStudentDAO;
import expeditors.backend.service.StudentService;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class DAOFactory {

   private static Map<String, Object> objects = new ConcurrentHashMap<>();
   private static String profile;

   static {
      ResourceBundle bundle = ResourceBundle.getBundle("backend");
      profile = bundle.getString("expeditors.profile");
   }

   public static StudentDAO studentDAO() {
      var result = switch(profile) {
         case "inmem" -> {
            var current = (StudentDAO)objects.get("studentDAO");
            if(current == null) {
               current = new InMemoryStudentDAO();
               objects.put("studentDAO", current);
            }
            yield current;
         }
         case "jpa" -> (StudentDAO)objects.computeIfAbsent("studentDAO", (key) -> {return new JPAStudentDAO();});
         default -> throw new RuntimeException("Unknown profile: " + profile);
      };

      return result;
   }

   public static StudentService studentService() {
      StudentService ss = (StudentService)objects.computeIfAbsent("studentService", key -> {

            StudentService newService = new StudentService();
            StudentDAO dao = studentDAO();
            newService.setStudentDAO(dao);
            return newService;
      });

      return ss;
   }
}
