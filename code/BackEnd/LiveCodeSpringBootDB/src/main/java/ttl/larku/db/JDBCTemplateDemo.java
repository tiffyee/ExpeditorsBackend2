package ttl.larku.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ttl.larku.domain.Course;
import ttl.larku.jconfig.LarkUTestDataConfig;

import static java.lang.System.out;

public class JDBCTemplateDemo {

   LarkUTestDataConfig testDataConfig = new LarkUTestDataConfig();

   public static void main(String[] args) {
      JDBCTemplateDemo jtd = new JDBCTemplateDemo();
      String url = "jdbc:postgresql://localhost:5433/larku";
      String user = "larku";
      String pw = "larku";

      DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);

      JdbcTemplate template = new JdbcTemplate(dataSource);
      //jtd.getSimpleWithOneColumn(template);
      jtd.addScheduledClasses(template);


   }

   public void getSimpleWithOneColumn(JdbcTemplate template) {
      String sql = "select code from Course where id = ?";

      String name =
            template.queryForObject(sql, String.class, 1);

      out.println("name: " + name);
   }

   public void getWholeObjectButOnlyCourse(JdbcTemplate template) {
//      String sql = "select * from course where id = ?";
      String sql = "select * from course";

      RowMapper<Course> rowMapper = (resultSet, rowNum) -> {
         int id = resultSet.getInt("course_id");
         String code = resultSet.getString("code");
         String title = resultSet.getString("title");
         float credits = resultSet.getFloat("credits");

         var newObj = new Course(code, title, credits);
         newObj.setId(id);
         return newObj;
      };

      //Course course = template.queryForObject(sql, rowMapper, 1);
      List<Course> course = template.query(sql, rowMapper, 1);

      out.println("course: " + course);
   }

   public void addScheduledClasses(JdbcTemplate template) {
      String insertStudentSql = "insert into scheduledclass (startdate, enddate, course_id) values(?, ?, ?)";

//      List<List<Object>> params = List.of(
//            List.of(LocalDate.parse("2024-10-10"), LocalDate.parse("2025-10-10"), 1),
//            List.of(LocalDate.parse("2025-10-10"), LocalDate.parse("2026-10-10"), 1)
//      );

      Object [] arr = new Object[]{LocalDate.parse("2024-10-10"), LocalDate.parse("2025-10-10"), 1};

      List<Object[]> params = new ArrayList<>();
      params.add(arr);

      int numRows = 0;

      for(Object[] args : params) {
         numRows += template.update(insertStudentSql, args);
      }

      out.println("numRows: " + numRows);
   }
}