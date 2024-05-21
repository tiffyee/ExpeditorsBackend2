package expeditors.backend.adoptapp;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.lang.System.out;

@SpringBootApplication
public class AdopterSpringBootApp {
   public static void main(String[] args) {
      SpringApplication.run(AdopterSpringBootApp.class, args);
   }
}

@Component
class InMemoryInitializer implements CommandLineRunner {

   @Autowired
   private AdopterService adopterService;

   @Override
   public void run(String... args) throws Exception {
        /*
        ('H2-Joey', '383 9999 9393', '1960-06-09', 'DOG', 'woofie', 'mixed'),
('H2-Darlene', '4484 9339 77939', '2020-05-09', 'TURTLE', 'swifty', null),
('H2-Miguel', '77 888 93938', '2022-03-09', 'DOG', 'woofwoof', 'Terrier');

         */
      var adopters = List.of(
            new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                  Pet.builder(Pet.PetType.DOG).name("woofie").build()),
            new Adopter("H2-Francine", "383 9339 9999 9393", LocalDate.parse("2020-05-09"),
                  Pet.builder(Pet.PetType.DOG).name("slinky").breed("dalmation").build()),
            new Adopter("H2-Darlene", "44484 9339 77939", LocalDate.parse("2020-05-09"),
                  Pet.builder(Pet.PetType.TURTLE).name("swifty").build()),
            new Adopter("H2-Miguel", "77 888 93938", LocalDate.parse("2022-03-09"),
                  Pet.builder(Pet.PetType.DOG).name("woofwoof").breed("Terrier").build())
      );
      adopters.forEach(adopterService::addAdopter);

      List<Adopter> result = adopterService.getAllAdopters();
      out.println("result: " + result.size());
      result.forEach(out::println);

   }
}
