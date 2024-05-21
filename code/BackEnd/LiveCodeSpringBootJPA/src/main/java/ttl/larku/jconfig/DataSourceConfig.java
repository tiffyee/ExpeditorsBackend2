package ttl.larku.jconfig;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
   @Bean
   public DataSource dataSource() {
      String url = "jdbc:postgresql://localhost:5433/larku";
      String user = "larku";
      String pw = System.getenv("DB_PASSWORD");
      DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);
      return dataSource;
   }
}
