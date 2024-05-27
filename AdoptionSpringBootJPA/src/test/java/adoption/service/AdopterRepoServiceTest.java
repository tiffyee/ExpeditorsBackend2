package adoption.service;

import adoption.domain.Adopter;
import adoption.domain.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdopterRepoServiceTest {


    @Autowired
    AdopterRepoService as;

    @Autowired
    public void setAdopterServiceTest(AdopterRepoService adopterRepoService){
        this.as = adopterRepoService;
    }

//    @Autowired
//    private ApplicationContext context;
//


    @Test
    public void testInsertAdopter(){
        Pet pet1 = new Pet(1, Pet.PetType.CAT,"Fluffy","Tabby");
        Adopter adopter1 = new Adopter("john doe", "415-123-4567");
        Adopter adopter2 = new Adopter("Jane Doe","123-123-1234");

        Adopter insertedAdopter1 = as.add(adopter1);
        Adopter insertedAdopter2 = as.add(adopter2);
        System.out.println("Adopter1: "  + insertedAdopter1);
        System.out.println("Adopter2: "  + insertedAdopter2);
        assertNotNull(insertedAdopter1);
        assertEquals(1,insertedAdopter1.getId());
        assertEquals(2,insertedAdopter2.getId());
    }



}
