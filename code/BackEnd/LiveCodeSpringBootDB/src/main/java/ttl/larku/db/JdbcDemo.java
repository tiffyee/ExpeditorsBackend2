package ttl.larku.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ttl.larku.domain.Course;

import static java.lang.System.out;

public class JdbcDemo {

   public static void main(String[] args) {
      String url = "jdbc:postgresql://localhost:5433/larku";
      String user = "larku";
      String pw = "larku";

      try(Connection connection = DriverManager.getConnection(url, user, pw)) {
//         addCourses(connection);
         dumpCourses(connection);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public static void addCourses(Connection connection) {
      String sql = "insert into course (code, title, credits) values (?, ?, ?)";

      List<Integer> newKeys = new ArrayList<>();
      int rowsAffected = 0;

      try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

         ps.setString(1, "MATH-101");
         ps.setString(2, "Intro to Math");
         ps.setFloat(3, 3.5F);

         rowsAffected += ps.executeUpdate();

         try (ResultSet keys = ps.getGeneratedKeys();) {
            keys.next();
            var newId = keys.getInt(1);
            newKeys.add(newId);
         }

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

      System.out.println("rowsAffected: " + rowsAffected + ", newKeys: " + newKeys);
   }

   public static void dumpCourses(Connection connection) {
      String sql = "select * from Course";
      try (PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet resultSet = statement.executeQuery()
      ) {

         List<Course> result = new ArrayList<>();
         while (resultSet.next()) {
            int id = resultSet.getInt("course_id");
            String code = resultSet.getString("code");
            String title = resultSet.getString("title");
            float credits = resultSet.getFloat("credits");

            var newObj = new Course(code, title, credits);
            newObj.setId(id);
            result.add(newObj);
         }

         out.println("num obj: " + result.size());
         result.forEach(out::println);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }
}
