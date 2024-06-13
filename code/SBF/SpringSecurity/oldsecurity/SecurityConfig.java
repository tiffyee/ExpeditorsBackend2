package ttl.larku;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Configuring the one AuthenticationManager. Usually more appropriate
	// to customize at lower levels, like a custom AuthenticationProvider, or
	// a custom UserDetailsService.
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("user")
//                .password(passwordEncoder().encode("passwordtoo"))
//                .roles("ADMIN", "USER");
//    }


	/**
	 * Our own AuthenticationProvider. Well, actually not wholly our own, because we
	 * are reusing the DaoAuthenticationProvider, and giving it our custom
	 * MyUserDetailsService
	 *
	 * @return
	 */
	// @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider
//                = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(new MyUserDetailsService());
////        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
	

	/**
	 * As another level of customization, we could just provide an
	 * UserDetailsManager primed up with data. This will get set in the default
	 * DaoAuthenticationProvider that Spring Boot will provide if we don't supply
	 * our own.
	 * 
	 * We can either provide an InMemoryUserDetailsManager, or use the
	 * JdbcUserDetailsManager.
	 * 
	 * If we use the JdbcUserDetailsManager then we have to provide a DataSource. We
	 * are creating one in DataSourceConfig.java.
	 * 
	 * In fact, we are creating two, because if we create *any* DataSource, then we
	 * are responsible for all of them, including the main one we are going to use
	 * for our application. Spring backs off when it sees that we have created our
	 * own DataSource.
	 *
	 * @return
	 */
	@Bean
	public UserDetailsService userDetailsService(@Qualifier("UserDS") DataSource userDataSource) {
//        UserDetailsManager uds = new InMemoryUserDetailsManager();
		UserDetailsManager uds = new JdbcUserDetailsManager(userDataSource);

		//password is password.
		UserDetails u1 = User.builder().username("bobby")
				.password("$2a$10$u5leH7rxVHh43TSz40w8z.0m.G4vifluKxERnMBCTLQ8KlqXCgEGe").roles("ADMIN", "USER")
				.build();
		uds.createUser(u1);
		UserDetails u2 = User.withUserDetails(u1).username("alice").roles("USER").build();
		uds.createUser(u2);

		return uds;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**");
	}

	/**
	 * This is the method to override to do authorization.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// magic to make the h2-console work, since it does'nt send
		// csrf tokens. Note that since we are disabling csrf completely
		// below, we really don't need the csrf part here, but just as an example.
//		http.authorizeRequests()
//			.antMatchers("/h2-console/**") .hasRole("ADMIN")
//			.and()
//			.csrf().ignoringAntMatchers("/h2-console/**")
//			.and()
//			.headers().frameOptions().sameOrigin();

		http.authorizeRequests()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.mvcMatchers(HttpMethod.GET, "/adminrest/student/whoiscalling").hasRole("ADMIN")
				// All GET requests are open
				.mvcMatchers(HttpMethod.GET, "/adminrest/**").permitAll()
				// POST requests to /adminrest/** need Role of ADMIN
				.mvcMatchers(HttpMethod.POST, "/adminrest/**").hasRole("ADMIN")
				// Use this to authenticate all requests
//                .anyRequest()
//                .fullyAuthenticated()
				// management actuator endpoints
				.requestMatchers(EndpointRequest.toAnyEndpoint().excluding("health", "info")).hasRole("ADMIN")
				.and()
				.httpBasic()
				.and()
				.csrf().disable();

//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .and()
//                .logout()
//                .logoutRequestMatcher(
//                        new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login")
	}
}
