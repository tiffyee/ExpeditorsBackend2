package expeditors.backend.adoptapp.service;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author whynot
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdopterServiceTest {

    @Autowired
    private AdopterService adopterService;

    @BeforeEach
    public void init() {
//        DAOFactory.clear();
//        adopterService = DAOFactory.adopterService();
    }

    @Test
    public void testGetAll() {

        List<Adopter> adopters = adopterService.getAllAdopters();
        int oldCount = adopters.size();

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        adopterService.addAdopter(adopter);


        adopters = adopterService.getAllAdopters();
        assertEquals(oldCount + 1, adopters.size());

        adopters.forEach(System.out::println);
    }

    @Test
    public void testGetOneWithGoodId() {

        List<Adopter> adopters = adopterService.getAllAdopters();
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        adopterService.addAdopter(adopter);

        Adopter a = adopterService.getAdopter(1);
        assertEquals(1, a.getId());
    }

    @Test
    public void testGetOneWithNonExistentId() {
        Adopter adopter = adopterService.getAdopter(1000);
        assertNull(adopter);
    }

    @Test
    public void testDeleteWithExistingAdopter() {

        int oldCount = adopterService.getAllAdopters().size();

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        Adopter newAdopter = adopterService.addAdopter(adopter);

        int midCount = adopterService.getAllAdopters().size();

        boolean result = adopterService.deleteAdopter(newAdopter.getId());
        assertTrue(result);

        int newCount = adopterService.getAllAdopters().size();
        assertEquals(midCount, newCount + 1);
    }

    @Test
    public void testDeleteNonExistingAdopter() {

        boolean result = adopterService.deleteAdopter(1000);
        assertFalse(result);
    }

    @Test
    public void testUpdateWithExistingAdopter() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        Adopter newAdopter = adopterService.addAdopter(adopter);

        assertTrue(newAdopter.getName().contains("Joey"));

        newAdopter.setName("Martha");

        boolean result = adopterService.updateAdopter(newAdopter);
        assertTrue(result);

        newAdopter = adopterService.getAdopter(newAdopter.getId());
        assertEquals("Martha", newAdopter.getName());
    }

    @Test
    public void testUpdateNonExistingAdopter() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        Adopter newAdopter = adopterService.addAdopter(adopter);

        newAdopter.setId(1000);

        boolean result = adopterService.updateAdopter(newAdopter);
        assertFalse(result);
    }

    @Test
    public void testGetByPetType() {
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        Adopter adopter2 = new Adopter("Franny", "282 9393 8834", LocalDate.of(1990, 6, 9),
                Pet.builder(PetType.TURTLE).name("swifty").build());

        adopterService.addAdopter(adopter);
        adopterService.addAdopter(adopter2);
        List<Adopter> result = adopterService.getAdoptersByPetType(PetType.TURTLE);

        assertEquals(2, result.size());

        result = adopterService.getAdoptersByPetType(PetType.CAT);

        assertEquals(0, result.size());
    }

    @Test
    public void testAddPetToExistingAdopter() {
        Adopter adopter = new Adopter("New Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());

        var insertedAdopter = adopterService.addAdopter(adopter);

        var foundAdopter = adopterService.getAdopter(insertedAdopter.getId());
        assertNotNull(foundAdopter);

        var newPet = Pet.builder(PetType.CAT).name("curly").build();
        foundAdopter.setPet(newPet);
    }

}
