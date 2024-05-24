package adoption;

import adoption.db.InitDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AdoptionSpringBootJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptionSpringBootJpaApplication.class, args);
    }

}
//
//
//@Component
//class MyJPARunner implements CommandLineRunner
//{
//    @Autowired
//    private AdopterJPAService adopterService;
//
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Here we go with Spring Boot");
//
//        Adopter adopter = new Adopter("John Doe", "123-123-1234");
//        Adopter newAdopter = adopterService.add(adopter);
//
//        Adopter adopter2 = new Adopter("Jane Doe", "415-123-1234");
//        adopterService.add(adopter2);
//
//        Adopter adopter3 = new Adopter("Tiffany Yee", "415-222-2222");
//        adopterService.add(adopter3);
//
//
//        List<Adopter> adopters = adopterService.findAll();
//        System.out.println("Adopters:" + adopters.size());
//        adopters.forEach(System.out::println);
//    }
//}


@Component
class DBInitializer implements CommandLineRunner
{
    @Autowired
    private InitDB initDB;

    @Override
    public void run(String... args) throws Exception {
        initDB.doIt();
    }
}
