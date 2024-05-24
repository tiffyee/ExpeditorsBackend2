package adoption.dao.repository;

import adoption.domain.Adopter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdopterRepoTest {

    @Autowired
    private AdopterRepo adopterRepo;

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

    }

    @Test
    public void findAdopterNameAndPetName(){
        List<Object[]> results = adopterRepo.findAdopterNameAndPetName();
        results.forEach(System.out::println);
    }


}
