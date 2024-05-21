package ttl.larku.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ttl.larku.domain.Course;
import ttl.larku.domain.PhoneNumber;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.jconfig.LarkUTestDataConfig;

import static java.lang.System.out;

public class JDBCDemo {

   LarkUTestDataConfig testDataConfig = new LarkUTestDataConfig();

   public static void main(String[] args) {
      JDBCDemo jdbcDemo = new JDBCDemo();

      String url = "jdbc:postgresql://localhost:5433/larku";
      String user = "larku";
      String pw = "larku";

      try (Connection connection = DriverManager.getConnection(url, user, pw)) {
//         jdbcDemo.addDataToDB(connection);
//         jdbcDemo.addStudent(connection);
//         jdbcDemo.dumpAllStudents(connection);


//         jdbcDemo.addCourses(connection);
//         jdbcDemo.dumpCourses(connection);

         //Example of Many to One Unidirectional
//         jdbcDemo.addScheduledClasses(connection);
//         jdbcDemo.dumpScheduledClasses(connection);
         jdbcDemo.dumpScheduledClassesWithJoin(connection);

         //Exmaple of One to Many Unidirectional
//         jdbcDemo.addPhoneNumbers(connection);
//         jdbcDemo.dumpAllStudentsWithPhoneNumbers(connection);

      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public void addDataToDB(Connection connection) {
         addStudents(connection);
         addCourses(connection);
         addScheduledClasses(connection);
         addPhoneNumbers(connection);
         registerStudentsForClasses(connection);
   }

   public void addOneStudent(Connection connection) {
      String inserSql = "insert into student (name, phonenumber, dob, status)" +
            " values(?, ?, ?, ?)";
      try (PreparedStatement statement = connection.prepareStatement(inserSql,
            Statement.RETURN_GENERATED_KEYS)) {
         statement.setString(1, "Carly");
         statement.setString(2, "383 022 02202");
         statement.setObject(3, LocalDate.parse("1980-10-10"));
         statement.setString(4, "FULL_TIME");

         int rowsAffected = statement.executeUpdate();
         int newId = -1;
         try (var keys = statement.getGeneratedKeys();) {
            keys.next();
            newId = keys.getInt(1);
         }

         out.println("rowsAffected: " + rowsAffected + ", newId: " + newId);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public void addStudents(Connection connection) {
      String inserSql = "insert into student (name, dob, status)" +
            " values(?, ?, ?)";
      List<Student> students = List.of(
            testDataConfig.student1(),
            testDataConfig.student2(),
            testDataConfig.student3(),
            testDataConfig.student4()
      );
      int rowsAffected = 0;
      var newKeys = new ArrayList<Integer>();

      try (PreparedStatement statement = connection.prepareStatement(inserSql,
            Statement.RETURN_GENERATED_KEYS)) {

         for(Student student : students) {
            statement.setString(1, student.getName());
            statement.setObject(2, student.getDob());
            statement.setString(3, student.getStatus().toString());

            rowsAffected += statement.executeUpdate();

            try (var keys = statement.getGeneratedKeys();) {
               keys.next();
               newKeys.add(keys.getInt(1));
            }
         }

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

      out.println("rowsAffected: " + rowsAffected + ", newKeys: " + newKeys);
   }

   public void dumpAllStudents(Connection connection) {
      String sql = "select * from student";
      try (PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet resultSet = statement.executeQuery()
      ) {

         List<Student> result = new ArrayList<>();
         while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String phoneNumber = resultSet.getString("phoneNumber");
            LocalDate dob = resultSet.getObject("dob", LocalDate.class);
            Student.Status status = Student.Status.valueOf(resultSet.getString("status"));

            var newStudent = new Student(name, phoneNumber, dob, status);
            newStudent.setId(id);
            result.add(newStudent);
         }

         out.println("num students: " + result.size());
         result.forEach(out::println);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public void addCourses(Connection connection) {
      String inserSql = "insert into course (code, title, credits)" +
            " values(?, ?, ?)";
      int rowsAffected = 0;
      var newKeys = new ArrayList<Integer>();

      var courses = List.of(
            testDataConfig.course1(),
            testDataConfig.course2(),
            testDataConfig.course3()
      );

//      var courses = List.of(
//            new Course("BKTW-101", "Intro To Basket Weaving", 3.5F),
//            new Course("Bio-101", "Intro to Biology", 3.5F),
//            new Course("Bktw-101", "Intro to Basket Weaving", 3.5F)
//      );

      for (Course course : courses) {
         try (PreparedStatement statement = connection.prepareStatement(inserSql,
               Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, course.getCode());
            statement.setString(2, course.getTitle());
            statement.setFloat(3, course.getCredits());

            rowsAffected += statement.executeUpdate();
            try (var keys = statement.getGeneratedKeys();) {
               keys.next();
               var newId = keys.getInt(1);
               newKeys.add(newId);
            }

         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }
      out.println("rowsAffected: " + rowsAffected + ", newIds: " + newKeys);
   }

   public void dumpCourses(Connection connection) {
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

   public void addScheduledClasses(Connection connection) {
      String inserSql = "insert into scheduledclass (startdate, enddate, course_id)" +
            " values(?, ?, ?)";
      int rowsAffected = 0;
      var newKeys = new ArrayList<Integer>();


      List<List<Object>> input = List.of(
            List.of("2024-02-26", "2024-06-07", 1),
            List.of("2025-02-26", "2025-06-07", 1),
            List.of("2024-05-26", "2024-09-07", 2)
      );

      for (List<Object> params : input) {
         try (PreparedStatement statement = connection.prepareStatement(inserSql,
               Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, LocalDate.parse((String) params.get(0)));
            statement.setObject(2, LocalDate.parse((String) params.get(1)));
            statement.setInt(3, (Integer) params.get(2));

            rowsAffected += statement.executeUpdate();
            try (var keys = statement.getGeneratedKeys();) {
               keys.next();
               var newId = keys.getInt(1);
               newKeys.add(newId);
            }

         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }

      out.println("rowsAffected: " + rowsAffected + ", newIds: " + newKeys);

   }

   public void dumpScheduledClasses(Connection connection) {
      String classSql = "select * from scheduledclass";
      String courseSql = "select * from course where id = ?";

      out.println("Running query: " + classSql);
      try (PreparedStatement statement = connection.prepareStatement(classSql);
           ResultSet resultSet = statement.executeQuery()
      ) {

         List<ScheduledClass> result = new ArrayList<>();
         while (resultSet.next()) {
            int id = resultSet.getInt("id");
            LocalDate startDate = resultSet.getObject("startDate", LocalDate.class);
            LocalDate endDate = resultSet.getObject("endDate", LocalDate.class);
            int course_id_fk = resultSet.getInt("course_id");

            try (PreparedStatement courseStatement = connection.prepareStatement(courseSql);
            ) {
               courseStatement.setInt(1, course_id_fk);
               out.println("Running query: " + courseSql + " with " + course_id_fk);
               try (ResultSet courseResultSet = courseStatement.executeQuery();) {
                  courseResultSet.next();
                  int course_id = courseResultSet.getInt("id");
                  String code = courseResultSet.getString("code");
                  String title = courseResultSet.getString("title");
                  float credits = courseResultSet.getFloat("credits");

                  var newCourse = new Course(code, title, credits);
                  newCourse.setId(course_id);

                  var newObj = new ScheduledClass(newCourse, startDate, endDate);
                  newObj.setId(id);
                  result.add(newObj);
               }
            }
         }

         out.println("num obj: " + result.size());
         result.forEach(out::println);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public void dumpScheduledClassesWithJoin(Connection connection) {
      String classSql = "select * from scheduledclass sc join course c on sc.course_id = c.id";

      out.println("Running query: " + classSql);
      try (PreparedStatement statement = connection.prepareStatement(classSql);
           ResultSet resultSet = statement.executeQuery()
      ) {

         List<ScheduledClass> result = new ArrayList<>();
         while (resultSet.next()) {
            int id = resultSet.getInt("id");
            LocalDate startDate = resultSet.getObject("startDate", LocalDate.class);
            LocalDate endDate = resultSet.getObject("endDate", LocalDate.class);
            int course_id = resultSet.getInt("id");
            String code = resultSet.getString("code");
            String title = resultSet.getString("title");
            float credits = resultSet.getFloat("credits");

            var newCourse = new Course(code, title, credits);
            newCourse.setId(course_id);

            var newObj = new ScheduledClass(newCourse, startDate, endDate);
            newObj.setId(id);
            result.add(newObj);
         }

         out.println("num obj: " + result.size());
         result.forEach(out::println);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public void addPhoneNumbers(Connection connection) {
      String inserSql = "insert into phonenumber (type, number, student_id)" +
            " values(?, ?, ?)";
      int rowsAffected = 0;
      var newKeys = new ArrayList<Integer>();

      List<List<Object>> input = List.of(
            List.of("SATELLITE", "383 022 02202020", 1),
            List.of("MOBILE", "849 777 89229", 1),
            List.of("WORK", "55554 63 7337", 2),
            List.of("MOBILE", "89 060 04404", 3)
      );

      for (List<Object> params : input) {
         try (PreparedStatement statement = connection.prepareStatement(inserSql,
               Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, (String) params.get(0));
            statement.setString(2, (String) params.get(1));
            statement.setInt(3, (Integer) params.get(2));

            rowsAffected += statement.executeUpdate();
            try (var keys = statement.getGeneratedKeys();) {
               keys.next();
               var newId = keys.getInt(1);
               newKeys.add(newId);
            }

         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }

      out.println("rowsAffected: " + rowsAffected + ", newIds: " + newKeys);
   }

   public void dumpAllStudentsWithPhoneNumbers(Connection connection) {
      String sql = "select * from student s left join phonenumber p on s.id = p.student_id order by s.id";
      out.println("Running sql: " + sql);
      try (PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet resultSet = statement.executeQuery()
      ) {

         List<Student> result = new ArrayList<>();
         int lastStudentId = -1;
         Student currStudent = null;
         while (resultSet.next()) {
            int id = resultSet.getInt("id");
            if (id != lastStudentId) { //New Student

               lastStudentId = id;
               String name = resultSet.getString("name");
               LocalDate dob = resultSet.getObject("dob", LocalDate.class);
               Student.Status status = Student.Status.valueOf(resultSet.getString("status"));

               currStudent = new Student(name, null, dob, status);
               currStudent.setId(id);
               result.add(currStudent);
            }
            var tStr = (resultSet.getString("type"));
            if (currStudent != null && tStr != null) {
               PhoneNumber.Type pt = PhoneNumber.Type.valueOf(tStr);
               String number = resultSet.getString("number");
               PhoneNumber pn = new PhoneNumber(pt, number);
               currStudent.addPhoneNumber(pn);
            }
         }


         out.println("num students: " + result.size());
         result.forEach(out::println);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public void registerStudentsForClasses(Connection connection) {

      String inserSql = "insert into student_scheduledclass (student_id, classes_id) values(?, ?)";
      int rowsAffected = 0;

      //Student_id, class_id
      List<List<Object>> input = List.of(
            List.of(1, 1),
            List.of(1, 2),
            List.of(3, 3)
      );

      for (List<Object> params : input) {
         try (PreparedStatement statement = connection.prepareStatement(inserSql)) {

            statement.setInt(1, (Integer) params.get(0));
            statement.setInt(2, (Integer) params.get(1));

            rowsAffected += statement.executeUpdate();

         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      }

      out.println("rowsAffected: " + rowsAffected);
   }
}
