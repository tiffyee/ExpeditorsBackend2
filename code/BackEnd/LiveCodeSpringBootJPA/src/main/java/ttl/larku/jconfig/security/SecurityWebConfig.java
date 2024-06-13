package ttl.larku.jconfig.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(2)
public class SecurityWebConfig {

   @Bean
   public SecurityFilterChain webFilterChain(HttpSecurity http,
                                             AccessExceptionHandlerWeb accessDeniedHandler) throws Exception {

      http.authorizeHttpRequests(auth -> {
         auth.requestMatchers("/login", "/logout").permitAll();
         auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
         auth.requestMatchers(HttpMethod.GET , "/" , "/admin/**").authenticated();
         auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.POST).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");
//
         auth.anyRequest().denyAll();
      });

//      http.exceptionHandling(exh -> exh.accessDeniedHandler(accessDeniedHandler));

      http.formLogin(Customizer.withDefaults());

      return http.build();
   }

}
