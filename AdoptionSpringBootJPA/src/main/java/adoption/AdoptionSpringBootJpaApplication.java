package adoption;

import adoption.domain.Adopter;
import adoption.service.AdopterRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class AdoptionSpringBootJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptionSpringBootJpaApplication.class, args);
    }

}


@Component
class MyJPARunner implements CommandLineRunner
{
    @Autowired
    private AdopterRepoService adopterService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Here we go with Spring Boot");

//        Pet pet1 = new Pet(1,Pet.PetType.CAT, "Bobo", "Tabby");
//        Pet pet2 = new Pet(2,Pet.PetType.TURTLE, "Frankie", "Red-Eared Slider");
//        Pet pet3 = new Pet(3,Pet.PetType.DOG, "Moose", "Bernadoodle");
//        Pet pet4 = new Pet(4,Pet.PetType.DOG, "Jude", "Golden Retriever");
//
//
////        Pet pet1 = Pet.builder(Pet.PetType.CAT).petId(1).name("Bobo").breed("Tabby").build();
////        Pet pet2 = Pet.builder(Pet.PetType.TURTLE).petId(2).name("Frankie").breed("Red-Eared Slider").build();
////        Pet pet3 = Pet.builder(Pet.PetType.DOG).petId(3).name("Moose").breed("Bernadoodle").build();
////        Pet pet4 = Pet.builder(Pet.PetType.DOG).petId(4).name("Jude").breed("Golden Retriever").build();
//
//        Adopter adopter1 = new Adopter("john doe", "415-123-4567");
//        Adopter adopter2 = new Adopter("Jane Doe","123-123-1234");
//        Adopter adopter3 = new Adopter("Tiffany Yee","555-555-5555");
//
////        Adopter adopter1 = new Adopter("john doe", "415-123-4567", LocalDate.parse("2024-03-14"), Pet.builder(Pet.PetType.CAT).petId(1).name("Bobo").breed("Tabby").build());
////        Adopter adopter2 = new Adopter("Jane Doe","123-123-1234",LocalDate.parse("2016-07-15"),Pet.builder(Pet.PetType.TURTLE).petId(2).name("Frankie").breed("Red-Eared Slider").build());
////        Adopter adopter3 = new Adopter("Tiffany Yee","555-555-5555",LocalDate.parse("2022-03-21"),Pet.builder(Pet.PetType.DOG).petId(3).name("Moose").breed("Bernadoodle").build());
//
//        Adopter insertedAdopter1 = adopterService.add(adopter1);
//        Adopter insertedAdopter2 = adopterService.add(adopter2);
//        Adopter insertedAdopter3 = adopterService.add(adopter3);
//
//        insertedAdopter1.setAdoptionDate(LocalDate.parse("2024-03-14"));
//        insertedAdopter1.addPet(pet1);
//        insertedAdopter2.setAdoptionDate(LocalDate.parse("2024-03-14"));
//        insertedAdopter2.addPet(pet2);
//        insertedAdopter3.setAdoptionDate(LocalDate.parse("2022-03-21"));
//        insertedAdopter3.addPet(pet3);
//        insertedAdopter3.addPet(pet4);


        List<Adopter> adopters = adopterService.findAll();
        System.out.println("Adopters:" + adopters.size());
        adopters.forEach(System.out::println);
    }
}


//@Component
//class DBInitializer implements CommandLineRunner
//{
//    @Autowired
//    private InitDB initDB;
//
//    @Override
//    public void run(String... args) throws Exception {
//        initDB.doIt();
//    }
//}
