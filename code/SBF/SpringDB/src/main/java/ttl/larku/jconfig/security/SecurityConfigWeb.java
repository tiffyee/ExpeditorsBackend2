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
public class SecurityConfigWeb {


   @Bean
   public SecurityFilterChain webFilterChain(HttpSecurity http,
                                             AccessExceptionHandlerWeb accessExceptionHandlerWeb) throws Exception {
      http.authorizeHttpRequests(auth -> {
         auth.requestMatchers("/login", "/logoff").permitAll();
         auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();

         auth.requestMatchers(HttpMethod.GET, "/", "/admin/**",
               "/error", "/eek").authenticated();

         auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.POST).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");

         auth.anyRequest().denyAll();
      });

//      http.httpBasic(Customizer.withDefaults());
      http.formLogin(Customizer.withDefaults());

      http.exceptionHandling(cust -> cust.accessDeniedHandler(accessExceptionHandlerWeb));

//      http.csrf(AbstractHttpConfigurer::disable);

      var chain = http.build();

      return chain;
   }

}
