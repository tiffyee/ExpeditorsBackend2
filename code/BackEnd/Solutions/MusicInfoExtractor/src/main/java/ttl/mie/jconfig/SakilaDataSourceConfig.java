package ttl.mie.jconfig;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
@EnableJpaRepositories(basePackages = "ttl.mie.dao.repository.sakila",
        entityManagerFactoryRef = "sakilaEntityManagerFactory",
        transactionManagerRef = "sakilaTransactionManager")
public class SakilaDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.sakila")
    public DataSourceProperties sakilaDataSouceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.sakila.configuration")
    public DataSource sakilaDataSource() {
        return sakilaDataSouceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean sakilaEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(sakilaDataSource())
                .packages("ttl.mie.domain.sakila")
                .build();
    }

    @Bean
    public PlatformTransactionManager sakilaTransactionManager(
            @Qualifier("sakilaEntityManagerFactory") LocalContainerEntityManagerFactoryBean sakilaEntityManagerFactory) {
        return new JpaTransactionManager(sakilaEntityManagerFactory.getObject());
    }

}
