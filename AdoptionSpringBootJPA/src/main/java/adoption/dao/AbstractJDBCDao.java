package adoption.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractJDBCDao {

    protected Connection getConnection () throws SQLException {
        String url = "jdbc:postgresql://localhost:5433/adoptapp";
        String username = "larku";
        String password = "larku";
        Connection con = DriverManager.getConnection (url,
                username, password);

        return con;
    }
}
