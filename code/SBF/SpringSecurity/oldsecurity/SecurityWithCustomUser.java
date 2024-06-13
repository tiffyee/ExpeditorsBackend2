package ttl.larku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

//This @Conditional is here so that you can exclude this class in tests
//if you need to.
//This is necessary if you are testing in a non web environment (e.g. WebEnvironment.NONE),
//because a lot of what this class needs will not be available there.
//You can set the property on the test.  Look at ClassDAOTest for an example.
@ConditionalOnProperty(name = "ttl.larku.security", havingValue = "true", matchIfMissing = true)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
//public class SecurityWithCustomUser extends WebSecurityConfigurerAdapter {
public class SecurityWithCustomUser {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                web.ignoring().antMatchers("/resources/**", "/static/**");
    }

    //We need these two to plug them into the http object below
    //so we get a chance to deal with 401 and 403 errors.
    @Autowired
    private AccessExceptionHandler accessDeniedHandler;
    @Autowired
    private AuthExceptionHandler authExceptionHandler;


    /**
     * This is the method to override to do authorization.
     */
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // magic to make the h2-console work, since it doesn't send
        // csrf tokens. Note that since we are disabling csrf completely
        // below, we really don't need the csrf part here, but just as an example.
        //The frameoptions bit is to allow h2-console to use frames.
        http.authorizeRequests()
                .antMatchers("/h2-console/**").hasRole("ADMIN")
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/resources/**", "/static/**", "/index.html").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .mvcMatchers(HttpMethod.GET, "/adminrest/student/whoiscalling").hasRole("ADMIN")
                // All GET requests are open
                .mvcMatchers(HttpMethod.GET, "/adminrest/**").permitAll()
                //PUT needs to be authenticated and is then further fine-tuned in StudentService.
                .mvcMatchers(HttpMethod.PUT, "/adminrest/**").hasAnyRole("USER", "ADMIN")
                // POST, DELETE, etc. requests to /adminrest/** need Role of ADMIN
                .mvcMatchers(HttpMethod.POST, "/adminrest/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/adminrest/**").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH, "/adminrest/**").hasRole("ADMIN")
                // management actuator endpoints
                .requestMatchers(EndpointRequest.toAnyEndpoint().excluding("health", "info")).hasRole("ADMIN")
                .anyRequest().denyAll()
                .and()
                .csrf().disable()
                .httpBasic();
//                .and()
//                .authorizeRequests().mvcMatchers("/index.html", "/otherPage.html").authenticated()
////                .anyRequest().authenticated()
//                .and().formLogin();

//				.csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));


        //ExceptionHandling
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authExceptionHandler);

        return http.build();
    }
}
