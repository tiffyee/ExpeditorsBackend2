package expeditors.backend.adoptapp.week6.service;

import expeditors.backend.adoptapp.week6.dao.DAOFactory;
import expeditors.backend.adoptapp.week6.domain.Adopter;
import expeditors.backend.adoptapp.week6.domain.Pet;
import expeditors.backend.adoptapp.week6.jconfig.AdoptAppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author whynot
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AdoptAppConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles({"dev"})
public class TestAdopterService {

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
        assertEquals(0, adopters.size());

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        adopterService.addAdopter(adopter);


        adopters = adopterService.getAllAdopters();
        assertEquals(1, adopters.size());

        adopters.forEach(System.out::println);
    }

    @Test
    public void testGetOneWithGoodId() {

        List<Adopter> adopters = adopterService.getAllAdopters();
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
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

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        Adopter newAdopter = adopterService.addAdopter(adopter);

        boolean result = adopterService.deleteAdopter(newAdopter.getId());
        assertTrue(result);

        newAdopter = adopterService.getAdopter(1);
        assertNull(newAdopter);
    }

    @Test
    public void testDeleteNonExistingAdopter() {

        boolean result = adopterService.deleteAdopter(1000);
        assertFalse(result);
    }

    @Test
    public void testUpdateWithExistingAdopter() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        Adopter newAdopter = adopterService.addAdopter(adopter);

        assertTrue(newAdopter.getName().contains("Joey"));

        newAdopter.setName("Martha");

        boolean result = adopterService.updateAdopter(newAdopter);
        assertTrue(result);

        newAdopter = adopterService.getAdopter(1);
        assertEquals("Martha", newAdopter.getName());
    }

    @Test
    public void testUpdateNonExistingAdopter() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        Adopter newAdopter = adopterService.addAdopter(adopter);

        newAdopter.setId(1000);

        boolean result = adopterService.updateAdopter(newAdopter);
        assertFalse(result);
    }

    @Test
    public void testGetByPetType() {
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        Adopter adopter2 = new Adopter("Franny", "282 9393 8834", LocalDate.of(1990, 6, 9),
                Pet.builder(Pet.PetType.TURTLE).name("swifty").build());

        adopterService.addAdopter(adopter);
        adopterService.addAdopter(adopter2);
        List<Adopter> result = adopterService.getAdoptersByPetType(Pet.PetType.TURTLE);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains("Franny"));

        result = adopterService.getAdoptersByPetType(Pet.PetType.CAT);

        assertEquals(0, result.size());
    }
}
