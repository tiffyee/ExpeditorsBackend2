package ttl.mie.jconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "ttl.mie.dao.repository.track",
        entityManagerFactoryRef = "trackEntityManagerFactory",
        transactionManagerRef = "trackTransactionManager")
public class TrackDataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties trackDataSouceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("dataSource")
    @ConfigurationProperties("spring.datasource.configuration")
    public HikariDataSource trackDataSource() {
        return trackDataSouceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "trackEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean trackEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(trackDataSource())
                .packages("ttl.mie.domain.track")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager trackTransactionManager(
            @Qualifier("trackEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean trackEntityManagerFactory) {
        return new JpaTransactionManager(trackEntityManagerFactory.getObject());
    }
}
