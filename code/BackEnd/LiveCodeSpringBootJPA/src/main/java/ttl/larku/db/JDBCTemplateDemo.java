package ttl.larku.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ttl.larku.domain.PhoneNumber;
import ttl.larku.domain.Student;
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
      NamedParameterJdbcTemplate namedParameterJdbcTemplate = new
            NamedParameterJdbcTemplate(dataSource);

//      jtd.getSimpleWithOneColumn(template);
//      jtd.getWholeObjectButOnlyStudent(template);
//      jtd.getWholeObjectWithRelationShip(template);
      //jtd.getWholeObjectWithRelationShipWithResultSetExtractor(template);

      jtd.addStudentsWithPhoneNumbers(template);
   }

   public void getSimpleWithOneColumn(JdbcTemplate template) {
      String sql = "select name from Student where id = ?";

      String name =
            template.queryForObject(sql, String.class, 1);

      out.println("name: " + name);
   }

   public void getWholeObjectButOnlyStudent(JdbcTemplate template) {
      String sql = "select * from Student where id = ?";

      RowMapper<Student> rowMapper = (resultSet, rowNum) -> {
         int id = resultSet.getInt("id");
         String name = resultSet.getString("name");
         LocalDate dob = resultSet.getObject("dob", LocalDate.class);

         Student.Status status = Student.Status.valueOf(resultSet.getString("status"));

         Student student = new Student(name, "", dob, status);
         return student;
      };

      Student student =
            template.queryForObject(sql, rowMapper, 1);

      out.println("student: " + student);
   }

   public void getWholeObjectWithRelationShip(JdbcTemplate template) {
      String sql = "select * from student s left join phonenumber p on s.id = p.student_id order by s.id";

      RowMapper<Student> rowMapper = (resultSet, rowNum) -> {
         int id = resultSet.getInt("id");
         String name = resultSet.getString("name");
         LocalDate dob = resultSet.getObject("dob", LocalDate.class);


         Student.Status status = Student.Status.valueOf(resultSet.getString("status"));

         Student student = new Student(name, "", dob, status);
         String type = resultSet.getString("type");
         if (type != null) {
            String number = resultSet.getString("number");


            PhoneNumber pn = new PhoneNumber(PhoneNumber.Type.valueOf(type), number);
            student.addPhoneNumber(pn);
         }
         return student;
      };

      List<Student> students = template.query(sql, rowMapper);

      students.forEach(out::println);
   }

   public void getWholeObjectWithRelationShipWithResultSetExtractor(JdbcTemplate template) {
      String sql = "select * from student s left join phonenumber p on s.id = p.student_id order by s.id";

      List<Student> result = new ArrayList<>();
      ResultSetExtractor<List<Student>> rowMapper = (resultSet) -> {
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

      List<Student> students = template.query(sql, rowMapper);

      students.forEach(out::println);
   }

   public void addStudentsWithPhoneNumbers(JdbcTemplate template) {
      String insertStudentSql = "insert into student (name, dob, status) values(?, ?, ?)";

      String insertPhoneNumbersSql = "insert into phonenumber (type, number, student_id) values(?, ?, ?)";

      List<Student> students = List.of(
                  testDataConfig.student1(),
                  testDataConfig.student2(),
                  testDataConfig.student3(),
                  testDataConfig.student4()
            );

      List<Object[]> studentParams = students.stream()
            .map(s -> new Object[]{s.getName(), s.getDob(), s.getStatus().toString()})
            .toList();

      List<Object[]> phoneParams = students.stream()
            .flatMap(s -> s.getPhoneNumbers().stream()
                  .map(pn -> new Object[]{pn.getType().toString(), pn.getNumber(), s.getId()}))
            .collect(Collectors.toList());

      phoneParams.add(new Object[]{"WORK", "38 00 045765", 1});

      out.println("Running sql: " + insertStudentSql);
      int [] newStudents = template.batchUpdate(insertStudentSql, studentParams);

      out.println("Running sql: " + insertPhoneNumbersSql);
      int [] newPhones = template.batchUpdate(insertPhoneNumbersSql, phoneParams);

      out.println("students added : " + newStudents.length);
      out.println("phones added: " + newStudents.length);
   }
}
