package ttl.larku.jconfig.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfigAllElse {
   /**
    * We are using the approved way to get a PasswordEncode.
    * But _note that this requires us to add a '{bcrypt}'
    * section in front of the passwords in userdbData-h2.sql
    * @return
    */
   @Bean
   public PasswordEncoder passwordEncoder() {
      PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();
      return passwordEncoder;
   }

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
}
