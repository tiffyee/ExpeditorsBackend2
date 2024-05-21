package ttl.larku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringDBApp {
    public static void main(String[] args) {
        SpringApplication springApp = new SpringApplication(SpringDBApp.class);

        springApp.run(args);
    }
}




