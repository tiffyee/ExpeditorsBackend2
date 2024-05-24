package adoption.db;


import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class InitDB {

    public static void main(String[] args) {
        InitDB idb = new InitDB();

        idb.doIt();
    }

    public void doIt() {
        String url = "jdbc:postgresql://localhost:5433/adoptapp";
        String user = "larku";
        String pw = System.getenv("DB_PASSWORD");
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);
        String schemaFile = "sql/postgres/1-adoptapp-postgres_schema.sql";
        String dataFile = "sql/postgres/2-adoptapp-postgres_data.sql";

        JdbcClient jdbcClient = JdbcClient.create(dataSource);

        runSchemaFile(dataSource, schemaFile);
        runSchemaFile(dataSource, dataFile);
    }

    public void runSchemaFile(DataSource dataSource, String schemaFile) {
        try (Connection conn = dataSource.getConnection()) {

            System.err.println("Running schemaFile: " + schemaFile);
            ScriptUtils.executeSqlScript(conn, new ClassPathResource(schemaFile));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
