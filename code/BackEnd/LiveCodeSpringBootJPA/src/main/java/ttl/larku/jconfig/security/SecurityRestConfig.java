package ttl.larku.jconfig.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
public class SecurityRestConfig {

   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails bobby = User.withUsername("bobby")
            .password("{bcrypt}$2a$10$I0GWORygqYR120wi0TV.Lua//D93EFUQX3mpEu4t5bOCQUUalzWqC")
            .roles("ADMIN", "USER")
            .build();

      UserDetails manoj = User.withUsername("manoj")
            .password("{bcrypt}$2a$10$E0lqfyqxkgbZSk1ZGP.miexXFs1I0Le6qEPBoBMM/etmKBbgMQsX2")
            .roles("USER")
            .build();

      var userDetailsService = new InMemoryUserDetailsManager(bobby, manoj);

      return userDetailsService;
   }

   @Bean
   public SecurityFilterChain restFilterChain(HttpSecurity http,
                                              AuthExceptionHandler authExceptionHandler,
                                              AccessExceptionHandler accessDeniedHandler) throws Exception {
      http.securityMatcher("/adminrest/**");

      http.authorizeHttpRequests(auth -> {
         auth.requestMatchers(HttpMethod.GET , "/adminrest/**").authenticated();
         auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.POST).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");
//
//         auth.anyRequest().denyAll();
      });

      http.exceptionHandling(exh -> exh.accessDeniedHandler(accessDeniedHandler));
      http.httpBasic(customizer -> customizer.authenticationEntryPoint(authExceptionHandler));

      http.csrf(AbstractHttpConfigurer::disable);

      return http.build();
   }

}
