package ttl.larku.jconfig.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * We have split up our security configuration into two parts.
 * This one deals with the REST API, i.e. "/adminrest/**".
 *
 * @author whynot
 */
@Configuration
@Order(1)
public class SecurityFilterRest {

   //We need these two to plug them into the http object below
   //so we get a chance to deal with 401 and 403 errors.
   @Autowired
   private AccessExceptionHandler accessDeniedHandler;
   @Autowired
   private AuthExceptionHandler authExceptionHandler;


   @Bean
   public SecurityFilterChain restFilterChain(HttpSecurity http) throws Exception {
      //The first antMatcher limits the scope of this filter chain.
      //to /adminrest/**
      http.securityMatcher("/adminrest/**", "/discoveryrest/**", "/actuatorrest/**")
            .authorizeRequests(auth ->
                  auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/adminrest/student/whoiscalling").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/discoveryrest/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/actuatorrest/**").hasRole("ADMIN")
                        // All GET requests are open
                        .requestMatchers(HttpMethod.GET, "/adminrest/**").permitAll()
                        //PUT needs to be authenticated and is then further fine-tuned in StudentService.
                        .requestMatchers(HttpMethod.PUT, "/adminrest/**").hasAnyRole("USER", "ADMIN")
                        // POST, DELETE, etc. requests to /adminrest/** need Role of ADMIN
                        .requestMatchers(HttpMethod.POST, "/adminrest/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/adminrest/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/adminrest/**").hasRole("ADMIN")
                        .requestMatchers(EndpointRequest.toAnyEndpoint().excluding("health", "info")).hasRole("ADMIN")
                        //Say NO to everything else.
                        .requestMatchers("/adminrest/**").denyAll()
            )
            //Don't need this for REST.  Else we have to always supply a CSRF Token.
            .csrf(csrf -> csrf.disable())
            //We need to add the authenticationEntryPoint here to channel
            //Authorization Exceptions to our Exception Handler.
            .httpBasic(customizer -> customizer.authenticationEntryPoint(authExceptionHandler))
            .exceptionHandling(exh -> exh.accessDeniedHandler(accessDeniedHandler));


      return http.build();
   }

   //@Bean
   public AuthenticationEntryPoint restAuthenticationEntryPoint() {
      BasicAuthenticationEntryPoint restEntryPoint =
            new BasicAuthenticationEntryPoint();
      restEntryPoint.setRealmName("Rest realm");
      return restEntryPoint;
   }

   public SecurityFilterChain oldFilterChain(HttpSecurity http) throws Exception {
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


      http.authorizeRequests()
            .requestMatchers("/resources/**", "/static/**", "/index.html").permitAll()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers(HttpMethod.GET, "/adminrest/student/whoiscalling").hasRole("ADMIN")
            // All GET requests are open
            .requestMatchers(HttpMethod.GET, "/adminrest/**").permitAll()
            //PUT needs to be authenticated and is then further fine-tuned in StudentService.
            .requestMatchers(HttpMethod.PUT, "/adminrest/**").hasAnyRole("USER", "ADMIN")
            // POST, DELETE, etc. requests to /adminrest/** need Role of ADMIN
            .requestMatchers(HttpMethod.POST, "/adminrest/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/adminrest/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/adminrest/**").hasRole("ADMIN")
            // management actuator endpoints
            .anyRequest().denyAll()
            .and()
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults());


      //ExceptionHandling
      http.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authExceptionHandler));

      return http.build();
   }
}
