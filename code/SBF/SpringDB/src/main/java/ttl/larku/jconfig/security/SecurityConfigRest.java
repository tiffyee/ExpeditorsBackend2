package ttl.larku.jconfig.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
public class SecurityConfigRest {

   @Bean
   public SecurityFilterChain restFilterChain(HttpSecurity http,
                                              AccessExceptionHandler accessExceptionHandler,
                                              AuthExceptionHandler authExceptionHandler) throws Exception {
      //Master filter
      http.securityMatcher("/adminrest/**");

      http.authorizeHttpRequests(auth -> {
         auth.requestMatchers(HttpMethod.GET, "/adminrest/**").authenticated();

         auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.POST).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");

//         auth.anyRequest().denyAll();
//         auth.anyRequest().authenticated();
      });

//      http.httpBasic(Customizer.withDefaults());
      http.httpBasic(cust -> cust.authenticationEntryPoint(authExceptionHandler));
      http.exceptionHandling(cust -> cust.accessDeniedHandler(accessExceptionHandler));

      http.csrf(AbstractHttpConfigurer::disable);

      var chain = http.build();

      return chain;
   }

}
