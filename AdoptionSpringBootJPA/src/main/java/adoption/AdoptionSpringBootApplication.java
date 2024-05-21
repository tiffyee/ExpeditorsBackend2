package adoption;

import adoption.domain.Adopter;
import adoption.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class AdoptionSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptionSpringBootApplication.class, args);
    }

}

@Component
class MyRunner implements CommandLineRunner
{
    @Autowired
    private AdopterService adopterService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Here we go with Spring Boot");

        Adopter adopter = new Adopter("John Doe", "123-123-1234");
        Adopter newAdopter = adopterService.addAdopter(adopter);

        Adopter adopter2 = new Adopter("Jane Doe", "415-123-1234");
        adopterService.addAdopter(adopter2);

        Adopter adopter3 = new Adopter("Tiffany Yee", "415-222-2222");
        adopterService.addAdopter(adopter3);


        List<Adopter> adopters = adopterService.getAllAdopters();
        System.out.println("Adopters:" + adopters.size());
        adopters.forEach(System.out::println);
    }
}