package ttl.larku;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Service
public class MyUserDetailsService implements UserDetailsService {

    private static List<User> users = new ArrayList<>();

    //Passwords are BCrypted 'password'
    public MyUserDetailsService() {
        users.add(MyUserDetails.of(4, "alice", "$2a$10$2KtlBG9s4.QckPpFaM13b.3jeC6D/gYcWxFOcCVkpk7k/9Iz03THy", "ROLE_USER"));
        users.add(MyUserDetails.of(5, "bobby", "$2a$10$2KtlBG9s4.QckPpFaM13b.3jeC6D/gYcWxFOcCVkpk7k/9Iz03THy", "ROLE_ADMIN"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = users.stream().filter(u -> u.getUsername().equals(username))
                .findFirst().orElseThrow(() -> new UsernameNotFoundException(username));

        return user;
    }
}
