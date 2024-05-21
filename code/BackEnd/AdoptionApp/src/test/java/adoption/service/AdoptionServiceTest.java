package adoption.service;

import adoption.domain.Adopter;
import adoption.domain.Pet;
import adoption.jconfig.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles({"dev"})
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
        Adopter adopter1 = new Adopter("john doe", "415-123-4567");
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

}
