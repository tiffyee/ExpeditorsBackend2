package expeditors.backend;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import expeditors.backend.adoptapp.service.AdopterService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

@SpringBootApplication
public class AdopterUberRestApp
{
    public static void main(String[] args) {
        SpringApplication.run(AdopterUberRestApp.class, args);
    }
}

@Component
class InMemoryInitializer implements CommandLineRunner {

    @Autowired
    private AdopterService adopterService;

    @Override
    public void run(String... args) throws Exception {
        var adopters = List.of(
              new Adopter("Cmd-Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                    Pet.builder(PetType.DOG).name("woofie").build()),
              new Adopter("Cmd-Francine", "383 9339 9999 9393", LocalDate.parse("2020-05-09"),
                    Pet.builder(PetType.DOG).name("slinky").breed("dalmation").build()),
              new Adopter("Cmd-Darlene", "44484 9339 77939", LocalDate.parse("2020-05-09"),
                    Pet.builder(PetType.TURTLE).name("swifty").build()),
              new Adopter("Cmd-Miguel", "77 888 93938", LocalDate.parse("2022-03-09"),
                    Pet.builder(PetType.DOG).name("woofwoof").breed("Terrier").build())
        );
        adopters.forEach(adopterService::addAdopter);

        List<Adopter> result = adopterService.getAllAdopters();
        out.println("result: " + result.size());
        result.forEach(out::println);

    }
}
