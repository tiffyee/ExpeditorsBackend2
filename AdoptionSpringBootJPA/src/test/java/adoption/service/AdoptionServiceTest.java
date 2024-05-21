package adoption.service;

import adoption.domain.Adopter;
import adoption.domain.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {AppConfig.class})
//@ActiveProfiles({"dev"})
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdoptionServiceTest {

    @Autowired
    protected AdopterService as;

    @Autowired
    public void setAdopterServiceTest(AdopterService adopterService){
        this.as = adopterService;
    }

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void beforeEach(){
       // as = DAOFactory.adopterService();
    }

    @Test
    public void testInsertAdopter(){
        Pet pet = new Pet(1, Pet.PetType.CAT,"Fluffy","Tabby");
        Adopter adopter1 = new Adopter("john doe", "415-123-4567");
        Adopter adopter2 = new Adopter("Jane Doe","123-123-1234");

        Adopter insertedAdopter1 = as.addAdopter(adopter1);
        Adopter insertedAdopter2 = as.addAdopter(adopter2);
        System.out.println("Adopter1: "  + insertedAdopter1);
        System.out.println("Adopter2: "  + insertedAdopter2);
        assertNotNull(insertedAdopter1);
        assertEquals(1,insertedAdopter1.getId());
        assertEquals(2,insertedAdopter2.getId());
    }

    @Test
    public void testDeleteExistingAdopter(){
        Pet pet = new Pet(1, Pet.PetType.CAT,"Fluffy","Tabby");
        Adopter adopter1 = new Adopter("john doe", "415-123-4567", LocalDate.parse("2024-03-14"),Pet.builder(Pet.PetType.CAT).breed("Tabby").build());
        Adopter adopter2 = new Adopter("Jane Doe","123-123-1234");

        Adopter insertedAdopter1 = as.addAdopter(adopter1);
        Adopter insertedAdopter2 = as.addAdopter(adopter2);
        assertNotNull(insertedAdopter1);

        boolean result = as.deleteAdopter(insertedAdopter1.getId());
        assertTrue(result);
    }

    @Test
    public void testDeleteNonExistingAdopter(){
        boolean result = as.deleteAdopter(9999);
        assertFalse(result);
    }

    @Test
    void testFindAll(){
        Adopter adopter1 = new Adopter("john doe", "415-123-4567");
        Adopter adopter2 = new Adopter("Jane Doe","123-123-1234");

        Adopter insertedAdopter1 = as.addAdopter(adopter1);
        Adopter insertedAdopter2 = as.addAdopter(adopter2);

        List<Adopter> adopters = as.getAllAdopters();
        assertEquals(2,adopters.size());
        System.out.println("AdopterSize:" + adopters.size());
    }

}
