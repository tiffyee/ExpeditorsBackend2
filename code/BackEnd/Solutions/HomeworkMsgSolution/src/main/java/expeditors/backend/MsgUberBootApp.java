package expeditors.backend;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import expeditors.backend.adoptapp.service.AdopterRepoService;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class MsgUberBootApp {
    public static void main(String[] args) {
        SpringApplication.run(MsgUberBootApp.class, args);
    }
}

@Component
@Profile("makemanyadopters")
class MakeManyAdopters implements CommandLineRunner {

    @Autowired
    private AdopterRepoService adopterRepoService;

    @Override
    public void run(String... args) throws Exception {
        int petLimit = 4;
        getFunky();
        PetType[] petTypes = PetType.values();
        for (int i = 0; i < 100; i++) {
            int numPets = ThreadLocalRandom.current().nextInt(petLimit);
            //Create a random number of pets.
            var pets = IntStream.range(0, numPets)
                  .mapToObj(index ->
                              Pet.builder(petTypes[ThreadLocalRandom.current().nextInt(petTypes.length)])
                                    .name(randomLetters(ThreadLocalRandom.current().nextInt(3, 8)))
                                    .adoptionDate(
                                          LocalDate.of(ThreadLocalRandom.current().nextInt(2000, 2024),
                                                ThreadLocalRandom.current().nextInt(1, 13), ThreadLocalRandom.current().nextInt(1, 29))
                                    )
                                    .build()
                        ).collect(Collectors.toList());
            Adopter adopter = new Adopter(
                  //Adopter name
                  randomLetters(ThreadLocalRandom.current().nextInt(2, 8)) + " "
                  + randomLetters((ThreadLocalRandom.current().nextInt(2, 10))),
                  //Adopter phoneNumber
                  randomDigits(3) + " "
                        + randomDigits(2) + " "
                        + randomDigits(5),
                  //Pets
                  pets
            );

            adopterRepoService.addAdopter(adopter);
        }
    }


    String digits = "012346789";
    int digitsLength = digits.length();
    public String randomDigits(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(digits.charAt(ThreadLocalRandom.current().nextInt(digitsLength)));
        }
        return sb.toString();
    }

    String letters = getFunky();
    int lettersLength = letters.length();
    public String randomLetters(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(letters.charAt(ThreadLocalRandom.current().nextInt(2, lettersLength)));
        }
        return sb.toString();
    }

    public String getFunky() {
        String basic = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(basic);
        for(int i = 0x00C0; i < 0x00DF; i++) {
            sb.append((char)i);
        }

        String result = sb.toString();
        return result;
    }

}