package org.ttl.ratingservice.jconfig.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
   public SecurityFilterChain courseRatingChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests(auth -> {
         auth.requestMatchers(HttpMethod.GET, "/rating/**", "/admin/**").authenticated();
         auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");

         auth.anyRequest().denyAll();
      });

      http.httpBasic(Customizer.withDefaults());

      http.csrf(AbstractHttpConfigurer::disable);

      return http.build();
   }
}
