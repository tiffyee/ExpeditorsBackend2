package adoption.dao.jdbc;

import adoption.dao.BaseDAO;
import adoption.domain.Adopter;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Profile("JDBCAdopterDAO")
public class JDBCAdopterDAO implements BaseDAO<Adopter> {

    private JdbcClient jdbcClient;

    public JDBCAdopterDAO(DataSource dataSource) {
        jdbcClient = JdbcClient.create(dataSource);
    }

    String insertSql = "insert into adopter (name, phonenumber) values (?, ?, ?)";
    @Override
    public Adopter insert(Adopter newObject) {
        var keyHolder = new GeneratedKeyHolder();
        int rows = jdbcClient.sql(insertSql)
                .params(newObject.getName())
                .params(newObject.getPhoneNumber())
                .update(keyHolder);
        int newId = (int)keyHolder.getKeys().get("id");
        newObject.setId(newId);
        return newObject;
    }

    private String deleteSql = "delete from adopter where id = ?";
    @Override
    public boolean delete(int id) {
        int rows = jdbcClient.sql(deleteSql)
                .params(id)
                .update();
        return rows == 1;
    }

    private String updateSql = "update adopter set name = ?, phonenumber = ? where id = ?";
    @Override
    public boolean update(Adopter updateObject) {
        int rows = jdbcClient.sql(updateSql)
                .params(updateObject.getName())
                .params(updateObject.getPhoneNumber())
                .params(updateObject.getId())
                .update();

        return false;
    }

    private String findByIdSql = "select * from adopter where id = ?";
    @Override
    public Adopter findById(int id) {
        var result = jdbcClient.sql(findByIdSql)
                .params(id)
                .query(Adopter.class)
                .optional().orElse(null);
        return result;
    }

    private String findAllSql = "select * from adopter";
    @Override
    public List<Adopter> findAll() {
        var result = jdbcClient.sql(findAllSql)
                .query(Adopter.class)
                .list();
        return result;
    }
}
