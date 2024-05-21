package ttl.larku.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ttl.larku.domain.Course;
import ttl.larku.domain.PhoneNumber;
import ttl.larku.domain.Student;
import ttl.larku.jconfig.LarkUTestDataConfig;

import static java.lang.System.out;

public class JDBCClientDemo {

   LarkUTestDataConfig testDataConfig = new LarkUTestDataConfig();

   public static void main(String[] args) {
      JDBCClientDemo jtd = new JDBCClientDemo();
      String url = "jdbc:postgresql://localhost:5433/larku";
      String user = "larku";
      String pw = "larku";
      DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);

      JdbcClient jdbcClient = JdbcClient.create(dataSource);

      jtd.getSimpleWithOneColumn(jdbcClient);
//      jtd.getAllCourses(jdbcClient);
//      jtd.addStudentsWithPhoneNumbers(jdbcClient);
//      jtd.getWholeObjectWithRelationShipWithResultSetExtractor(jdbcClient);
   }

   public void getSimpleWithOneColumn(JdbcClient jdbcClient) {
//      String sql = "select code from Course where id = ?";
      String sql = "select code from Course where id = :id";

      String name = jdbcClient.sql(sql)
            .param("id", 1000)
            .query(String.class)
            .single();

      out.println("name: " + name);
   }

   public void getWholeObject(JdbcClient jdbdClient) {
      String sql = "select * from Course where id = ?";

      Course course = jdbdClient.sql(sql)
            .param(1)
            .query(Course.class)
            .single();

      out.println("course: " + course);
   }

   public void getAllCourses(JdbcClient jdbdClient) {
      String sql = "select * from Course";

      List<Course> courses = jdbdClient.sql(sql)
            .query(Course.class)
            .list();

      courses.forEach(out::println);
   }

   public void addStudentsWithPhoneNumbers(JdbcClient jdbcClient) {
      String insertStudentSql = "insert into student (name, dob, status) values(?, ?, ?)";

      String insertPhoneNumbersSql = "insert into phonenumber (type, number, student_id) values(?, ?, ?)";
      List<Integer> newStudentKeys = new ArrayList<>();

      List<Student> students = List.of(
            testDataConfig.student1(),
            testDataConfig.student2(),
            testDataConfig.student3(),
            testDataConfig.student4()
      );

      List<Object[]> studentParams = students.stream()
            .map(s -> new Object[]{s.getName() + "-JDBC", s.getDob(), s.getStatus().toString()})
            .toList();


      List<Object[]> phoneParams = students.stream()
            .flatMap(s -> s.getPhoneNumbers().stream()
                  .map(pn -> new Object[]{pn.getType().toString(), pn.getNumber(), s.getId()}))
            .toList();

      out.println("Running sql: " + insertStudentSql);
      var keyHolder = new GeneratedKeyHolder();
      for (Object[] params : studentParams) {
         jdbcClient.sql(insertStudentSql)
               .params(params)
               .update(keyHolder);

//         out.println("Keys for new student: " + keyHolder.getKeys());
         newStudentKeys.add((int)keyHolder.getKeys().get("id"));
      }


      out.println("Running sql: " + insertPhoneNumbersSql);
      for (Object[] params : phoneParams) {
         jdbcClient.sql(insertPhoneNumbersSql)
               .params(params)
               .update(keyHolder);
      }

      out.println("students added : " + newStudentKeys.size());
      newStudentKeys.forEach(out::println);
   }

   public void getWholeObjectWithRelationShipWithResultSetExtractor(JdbcClient jdbcClient) {
      String sql = "select * from student s left join phonenumber p on s.id = p.student_id order by s.id";

      List<Student> result = new ArrayList<>();
      ResultSetExtractor<List<Student>> resultSetExtractor = (resultSet) -> {
         int lastStudentId = -1;
         Student currStudent = null;

         while (resultSet.next()) {
            int id = resultSet.getInt("id");
            if (id != lastStudentId) {
               lastStudentId = id;

               String name = resultSet.getString("name");
               LocalDate dob = resultSet.getObject("dob", LocalDate.class);


               Student.Status status = Student.Status.valueOf(resultSet.getString("status"));

               currStudent = new Student(name, "", dob, status);
               currStudent.setId(id);
               result.add(currStudent);
            }
            String type = resultSet.getString("type");
            if (type != null) {
               String number = resultSet.getString("number");


               PhoneNumber pn = new PhoneNumber(PhoneNumber.Type.valueOf(type), number);
               currStudent.addPhoneNumber(pn);
            }
         }
         return result;
      };

      List<Student> students = jdbcClient.sql(sql)
            .query(resultSetExtractor);


      students.forEach(out::println);
   }
}
