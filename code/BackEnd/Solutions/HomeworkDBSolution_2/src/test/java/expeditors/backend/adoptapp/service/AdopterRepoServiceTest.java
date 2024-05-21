package expeditors.backend.adoptapp.service;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author whynot
 */
@SpringBootTest
@Transactional
public class AdopterRepoServiceTest {

    @Autowired
    private AdopterRepoService adopterService;

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

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        Adopter newAdopter = adopterService.addAdopter(adopter);

        int oldCount = adopterService.getAllAdopters().size();

        boolean result = adopterService.deleteAdopter(newAdopter.getId());
        assertTrue(result);

        int newCount = adopterService.getAllAdopters().size();
        assertEquals(oldCount - 1, newCount);
    }

    @Test
    public void testDeleteWithNonExistingAdopter() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
              Pet.builder(PetType.DOG).name("woofie").build());
        Adopter newAdopter = adopterService.addAdopter(adopter);

        int oldCount = adopterService.getAllAdopters().size();

        boolean result = adopterService.deleteAdopter(1000);
        assertFalse(result);

        int newCount = adopterService.getAllAdopters().size();
        assertEquals(oldCount, newCount);
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
    public void testUpdateWithNonExistingAdopter() {

        Adopter newAdopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
              Pet.builder(PetType.DOG).name("woofie").build());

        newAdopter.setName("Martha");
        newAdopter.setId(1000);

        boolean result = adopterService.updateAdopter(newAdopter);
        assertFalse(result);
    }

    @Test
    public void testGetByPetType() {
        List<Adopter> result = adopterService.getAdoptersByPetType(PetType.TURTLE);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains("Darlene"));

        result = adopterService.getAdoptersByPetType(PetType.CAT);

        assertEquals(0, result.size());
    }

    @Test
    public void testAddPetToExistingAdopterByHand() {
        int testId = 1;
        Adopter existing = adopterService.getAdopter(testId);
        assertEquals(2, existing.getPets().size());

        var newPet = Pet.builder(PetType.CAT).name("Hissy").breed("nasty").build();
        existing.addPet(newPet);

        existing = adopterService.getAdopter(testId);
        assertEquals(3, existing.getPets().size());
    }

    @Test
    public void testAddPetToExistingAdopterByService() {
        int testId = 1;
        var newPet = Pet.builder(PetType.CAT).name("Hissy").breed("nasty").build();

        boolean result = adopterService.addPetToAdopter(testId, newPet);
        assertTrue(result);

        var adopter = adopterService.getAdopter(testId);
        assertEquals(3, adopter.getPets().size());
    }

    @Test
    public void testAddPetToNonExistingAdopterByService() {
        int testId = 1000;
        var newPet = Pet.builder(PetType.CAT).name("Hissy").breed("nasty").build();

        boolean result = adopterService.addPetToAdopter(testId, newPet);
        assertFalse(result);
    }

    @Test
    public void testRemoveExistingPetFromExistingAdopterByService() {
        int testId = 1;

        Adopter existingAdopter = adopterService.getAdopter(testId);
        assertNotNull(existingAdopter);

        var pet = existingAdopter.getPets().stream().findFirst().orElse(null);
        assertNotNull(pet);

        boolean result = adopterService.removePetFromAdopter(testId, pet.getPetId());
        assertTrue(result);

        var adopter = adopterService.getAdopter(testId);
        assertEquals(1, adopter.getPets().size());
    }

    @Test
    public void testRemovePetFromNonExistingAdopterByService() {
        int testId = 1000;

        boolean result = adopterService.removePetFromAdopter(testId, 10);
        assertFalse(result);

    }

    @Test
    public void testRemoveNonExistingPetFromExistingAdopterByService() {
        int testId = 1;
        int badPetId = 1000;

        Adopter existingAdopter = adopterService.getAdopter(testId);
        assertNotNull(existingAdopter);

        boolean result = adopterService.removePetFromAdopter(testId, badPetId);
        assertFalse(result);

        var adopter = adopterService.getAdopter(testId);
        assertEquals(2, adopter.getPets().size());
    }
}
