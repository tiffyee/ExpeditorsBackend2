package ttl.larku.jconfig.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author whynot
 */
@Configuration
@Order(2)
public class SecurityFilterWeb {


   /**
    * This is the method to override to do authorization.
    */
   @Bean
   public SecurityFilterChain webFilterChain(HttpSecurity http,
                                             AccessExceptionHandlerWeb accessExceptionHandlerWeb) throws Exception {
      // magic to make the h2-console work, since it doesn't send
      // csrf tokens. Note that since we are disabling csrf completely
      // below, we really don't need the csrf part here, but just as an example.
      //The frameoptions bit is to allow h2-console to use frames.
      http.authorizeHttpRequests(auth ->
                  auth.requestMatchers("/h2-console/**").permitAll())
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers
                  .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            );

      http.authorizeHttpRequests(auth ->
                  auth.requestMatchers("/actuator/**"))
            .csrf(csrf -> csrf.ignoringRequestMatchers("/actuator/**"));


      http.securityMatcher("/login/**", "/logout/**", //"/getWithRestClient",
            "/adminPage", "/admin/**", "/discovery/**", "/actuator/**", "/");

      http
            .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/adminPage").hasRole("ADMIN")
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                              .requestMatchers("/").authenticated()
                        .requestMatchers("/admin", "/actuator").authenticated()
                        .requestMatchers(HttpMethod.GET, "/admin/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/discovery/**").hasRole("ADMIN")
                        //PUT needs to be authenticated and is then further fine-tuned in StudentService.
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasAnyRole("USER", "ADMIN")
                        // POST, DELETE, etc. requests to /admin/** need Role of ADMIN
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/admin/**").hasRole("ADMIN")
                        //restrict Actuator endPoints
                        .requestMatchers(EndpointRequest.toAnyEndpoint().excluding("health", "info")).hasRole("ADMIN")
                        .anyRequest().denyAll())
            .formLogin(cust -> cust.permitAll())
            .exceptionHandling(ex -> ex.accessDeniedHandler(accessExceptionHandlerWeb));


//        //ExceptionHandling
//        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authExceptionHandler);

      return http.build();
   }
}