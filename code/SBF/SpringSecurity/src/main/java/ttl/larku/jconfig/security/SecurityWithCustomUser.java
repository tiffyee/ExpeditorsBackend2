package ttl.larku.jconfig.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityWithCustomUser {

    /**
     * We are using the approved way to get a PasswordEncode.
     * But _note that this requires us to add a '{bcrypt}'
     * section in front of the passwords in userdbData-h2.sql
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder;
    }


    /**
     * As another level of customization, we could just provide an
     * UserDetailsManager primed up with data. This will get set in the default
     * DaoAuthenticationProvider that Spring Boot will provide if we don't supply
     * our own.
     * <p>
     * We can either provide an InMemoryUserDetailsManager, or use the
     * JdbcUserDetailsManager.
     * <p>
     * If we use the JdbcUserDetailsManager then we have to provide a DataSource. We
     * are creating one in DataSourceConfig.java.
     * <p>
     * In fact, we are creating two, because if we create *any* DataSource, then we
     * are responsible for all of them, including the main one we are going to use
     * for our application. Spring backs off when it sees that we have created our
     * own DataSource.
     *
     * @return
     */

//	@Bean
//	public UserDetailsService inMemoryUserDetailsService() {
//		MyUserDetailsService muds = new MyUserDetailsService();
//		return muds;
//	}
    @Primary
    @Bean
    public UserDetailsService userDetailsService(@Qualifier("UserDS") DataSource userDataSource,
                                                 DataSource mainDataSource) {
        JdbcTemplate userTemplate = new JdbcTemplate(userDataSource);
        JdbcTemplate mainTemplate = new JdbcTemplate(mainDataSource);
        MyUserDetailsDBService muds = new MyUserDetailsDBService(userTemplate, mainTemplate);
        return muds;
    }
}
