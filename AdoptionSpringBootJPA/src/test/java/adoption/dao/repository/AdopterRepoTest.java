package adoption.dao.repository;

import adoption.domain.Adopter;
import adoption.domain.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AdopterRepoTest {

    @Autowired
    private AdopterRepo adopterRepo;

    @Autowired PetRepo petRepo;

    @Test
    public void testGetAllAdopters(){
        List<Adopter> adopters = adopterRepo.findAll();
        System.out.println("adopters: " + adopters.size());
        adopters.forEach(System.out::println);
    }

    @Test
    public void testGetAllAdoptersWithPets(){
        List<Adopter> adopters = adopterRepo.findAllWithPets();
        System.out.println("adopters: " + adopters.size());
        adopters.forEach(System.out::println);
    }

    @Test
    public void testInsertAdopter(){

        Adopter adopter = new Adopter("Tom", "111-222-3333");
        adopter = adopterRepo.save(adopter);

        Adopter foundAdopter = adopterRepo.findByIdWithPets(adopter.getId());
//        Adopter foundAdopter = adopterRepo.findById(adopter.getId()).orElse((null));
        System.out.println("found adopter: "+ foundAdopter);
        assertNotNull(foundAdopter);
    }

    @Test
    public void testInsertAdopterWithPet(){
        Pet pet = new Pet(Pet.PetType.TURTLE, "Frankie", "Red-Eared Slider");
//        petRepo.save(pet);

//        Optional<Pet> opt = petRepo.findById(5);
//        if (opt.isPresent()){
//            Pet pet = opt.get();
            Adopter adopter = new Adopter("Tom", "111-222-3333", LocalDate.parse("2020-04-28"), pet);
            adopter = adopterRepo.save(adopter);
//        }
        Adopter foundAdopter = adopterRepo.findByIdWithPets(adopter.getId());
        System.out.println("found adopter: "+ foundAdopter);
        assertNotNull(foundAdopter);
    }

    @Test
    public void findAdopterNameAndPetName(){
        List<Object[]> results = adopterRepo.findAdopterNameAndPetName();
        results.forEach(System.out::println);
    }


}
