package expeditors.backend.adoptapp.dao.repo;

import expeditors.backend.adoptapp.dao.repository.AdopterRepo;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author whynot
 */
@SpringBootTest
public class AdopterRepoTest {

    @Autowired
    private AdopterRepo adopterDAO;

    @Test
    public void testGetAll() {
        List<Adopter> adopters = adopterDAO.findAll();
        int oldCount = adopters.size();

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        adopterDAO.save(adopter);

        adopters = adopterDAO.findAll();
        assertEquals(oldCount + 1, adopters.size());
    }

    @Test
    public void testGetAdopterWithExistingId() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        assertEquals(0, adopter.getId());
        adopterDAO.save(adopter);

        adopter = adopterDAO.findById(1).orElse(null);
        assertNotNull(adopter);
    }

    @Test
    public void testGetAdopterWithNonExistingId() {

        Adopter adopter = adopterDAO.findById(1000).orElse(null);
        assertNull(adopter);
    }

    @Test
    public void testInsert() {
        int oldCount = adopterDAO.findAll().size();

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        assertEquals(0, adopter.getId());
        adopter = adopterDAO.save(adopter);

        int newCount = adopterDAO.findAll().size();
        assertEquals(oldCount + 1, newCount);

        var foundAdopter = adopterDAO.findByIdWithPets(adopter.getId());

        assertEquals(1, foundAdopter.getPets().size());
    }

    @Test
    public void testDelete() {
        int testId = 1;
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        adopter = adopterDAO.save(adopter);

        Adopter a = adopterDAO.findById(testId).orElse(null);
        assertNotNull(a);

        adopterDAO.delete(a);

        a = adopterDAO.findById(testId).orElse(null);
        assertNull(a);
    }

    @Test
    public void testUpdateExistingAdopter() {
        int testId = 1;
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        adopter = adopterDAO.save(adopter);

        Adopter a = adopterDAO.findById(testId).orElse(null);
        assertNotNull(a);

        String newName = "Martha";
        a.setName(newName);

        Adopter result = adopterDAO.save(a);

        a = adopterDAO.findById(testId).orElse(null);
        assertEquals(newName, a.getName());
    }

    @Test
//    @Transactional
    public void testAddPetToExistingAdopter() {
        int id = 3;

        Adopter adopter = adopterDAO.findByIdWithPets(id);
        assertNotNull(adopter);

        var goodDog = Pet.builder(PetType.DOG).name("moki").breed("Spaniel").build();
        int oldCount = adopter.getPets().size();

        adopter.addPet(goodDog);
        adopter = adopterDAO.save(adopter);

        var foundAdopter = adopterDAO.findByIdWithPets(id);

        int newCount = foundAdopter.getPets().size();

        assertEquals(oldCount + 1, newCount);
    }

    @Test
    public void testRemoveAPetFromAnExistingAdopter() {
        int id = 1;

        Adopter adopter = adopterDAO.findByIdWithPets(id);
        int oldCount = adopter.getPets().size();
        var pets = adopter.getPets();
        if(!pets.isEmpty()) {
            pets.remove(pets.iterator().next());
        }
        adopterDAO.save(adopter);
        var foundAdopter = adopterDAO.findByIdWithPets(id);
        int newCount = foundAdopter.getPets().size();

        assertEquals(oldCount - 1, newCount);
    }
}
