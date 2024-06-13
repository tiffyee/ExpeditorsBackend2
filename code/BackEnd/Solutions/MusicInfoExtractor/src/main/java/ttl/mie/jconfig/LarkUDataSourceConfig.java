package ttl.mie.jconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LarkUDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.larku")
    public DataSourceProperties larkUDataSouceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.larku.configuration")
    public DataSource larkUDataSource() {
        return larkUDataSouceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
}
