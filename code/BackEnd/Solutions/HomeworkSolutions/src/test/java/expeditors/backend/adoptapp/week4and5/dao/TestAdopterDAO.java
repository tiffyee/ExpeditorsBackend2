package expeditors.backend.adoptapp.week4and5.dao;

import expeditors.backend.adoptapp.week4and5.dao.inmemory.InMemoryAdopterDAO;
import expeditors.backend.adoptapp.week4and5.domain.Adopter;
import expeditors.backend.adoptapp.week4and5.domain.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author whynot
 */
public class TestAdopterDAO {

    private InMemoryAdopterDAO adopterDAO;

    @BeforeEach
    public void init() {
        adopterDAO = new InMemoryAdopterDAO();
    }

    @Test
    public void testGetAll() {
        List<Adopter> adopters = adopterDAO.findAll();
        assertEquals(0, adopters.size());

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        adopterDAO.insert(adopter);

        adopters = adopterDAO.findAll();
        assertEquals(1, adopters.size());
    }

    @Test
    public void testGetAdopterWithExistingId() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        assertEquals(0, adopter.getId());
        adopterDAO.insert(adopter);

        adopter = adopterDAO.findById(1);
        assertNotNull(adopter);
    }

    @Test
    public void testGetAdopterWithNonExistingId() {

        Adopter adopter = adopterDAO.findById(1000);
        assertNull(adopter);
    }

    @Test
    public void testInsert() {
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        assertEquals(0, adopter.getId());
        adopter = adopterDAO.insert(adopter);

        assertEquals(1, adopter.getId());
    }

    @Test
    public void testDelete() {
        int testId = 1;
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        adopter = adopterDAO.insert(adopter);

        Adopter a = adopterDAO.findById(testId);
        assertNotNull(a);

        boolean result = adopterDAO.delete(testId);
        assertTrue(result);

        a = adopterDAO.findById(testId);
        assertNull(a);
    }

    @Test
    public void testDeleteNonExistingAdopter() {
        int testId = 1000;
        Adopter a = adopterDAO.findById(testId);
        assertNull(a);

        boolean result = adopterDAO.delete(testId);
        assertFalse(result);
    }

    @Test
    public void testUpdateExistingAdopter() {
        int testId = 1;
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        adopter = adopterDAO.insert(adopter);

        Adopter a = adopterDAO.findById(testId);
        assertNotNull(a);

        String newName = "Martha";
        a.setName(newName);

        boolean result = adopterDAO.update(a);
        assertTrue(result);

        a = adopterDAO.findById(testId);
        assertEquals(newName, a.getName());
    }

    @Test
    public void testUpdateNonExistingAdopter() {
        int testId = 1;
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        adopter = adopterDAO.insert(adopter);

        Adopter a = adopterDAO.findById(testId);
        assertNotNull(a);

        String newName = "Martha";
        a.setName(newName);
        a.setId(1000);

        boolean result = adopterDAO.update(a);
        assertFalse(result);
    }

}
