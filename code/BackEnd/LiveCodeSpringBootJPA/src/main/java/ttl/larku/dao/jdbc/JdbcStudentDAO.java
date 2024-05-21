package ttl.larku.dao.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;

@Repository
public class JdbcStudentDAO implements BaseDAO<Student> {

    private JdbcClient jdbcClient;

    public JdbcStudentDAO(DataSource dataSource) {
        jdbcClient = JdbcClient.create(dataSource);
    }

    private String updateSql = "update student set name = ?, dob = ?, status = ? where id = ?";
    public boolean update(Student updateObject) {
        int rows = jdbcClient.sql(updateSql)
              .param(updateObject.getName())
              .param(updateObject.getDob())
              .param(updateObject.getStatus().toString())
              .param(updateObject.getId())
              .update();

        return rows == 1;
    }

    private String deleteSql = "delete from student where id = ?";
    public boolean delete(Student student) {
        int rows = jdbcClient.sql(deleteSql)
              .param(student.getId())
              .update();
        return rows == 1;
    }

    private String insertSql = "insert into student (name, dob, status) values (?, ?, ?)";
    public Student insert(Student newObject) {
        //Create a new Id
        var keyHolder = new GeneratedKeyHolder();
        int rows = jdbcClient.sql(insertSql)
              .param(newObject.getName())
              .param(newObject.getDob())
              .param(newObject.getStatus().toString())
              .update(keyHolder);
        int newId = (int)keyHolder.getKeys().get("id");
        newObject.setId(newId);

        return newObject;
    }

    private String findByIdSql = "select * from student where id = ?";
    public Student findById(int id) {
        var result = jdbcClient.sql(findByIdSql)
              .param(id)
              .query(Student.class)
              .optional().orElse(null);
        return result;
    }

    private String findAllSql = "select * from student";
    public List<Student> findAll() {
        var result = jdbcClient.sql(findAllSql)
              .query(Student.class)
              .list();
        return result;
    }

    public void deleteStore() {
    }

    public void createStore() {
    }
}
