package adoption.service;

import adoption.domain.Adopter;
import adoption.domain.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest
//public class AdopterJPAServiceTest {
//
//
//    @Autowired
//    AdopterJPAService as;
//
//    @Autowired
//    public void setAdopterServiceTest(AdopterJPAService adopterJPAService){
//        this.as = adopterJPAService;
//    }
//
//    @Autowired
//    private ApplicationContext context;
//
//
//
//    @Test
//    public void testInsertAdopter(){
//        Pet pet = new Pet(1, Pet.PetType.CAT,"Fluffy","Tabby");
//        Adopter adopter1 = new Adopter("john doe", "415-123-4567");
//        Adopter adopter2 = new Adopter("Jane Doe","123-123-1234");
//
//        Adopter insertedAdopter1 = as.add(adopter1);
//        Adopter insertedAdopter2 = as.add(adopter2);
//        System.out.println("Adopter1: "  + insertedAdopter1);
//        System.out.println("Adopter2: "  + insertedAdopter2);
//        assertNotNull(insertedAdopter1);
//        assertEquals(1,insertedAdopter1.getId());
//        assertEquals(2,insertedAdopter2.getId());
//    }
//
//
//
//}
