package expeditors.backend.adoptapp.dao.repo;

import expeditors.backend.adoptapp.dao.repository.EmbeddedBigARepo;
import expeditors.backend.adoptapp.domain.embedded.BigAdopterEmbedded;
import expeditors.backend.adoptapp.domain.PetType;
import expeditors.backend.sql.SqlScriptBase;
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
@Transactional
public class TestEmbeddedBigARepo extends SqlScriptBase {

    @Autowired
    private EmbeddedBigARepo adopterDAO;

    @Test
    public void testGetAll() {
        List<BigAdopterEmbedded> adopters = adopterDAO.findAll();
        int oldCount = adopters.size();

        BigAdopterEmbedded adopter = new BigAdopterEmbedded("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                PetType.DOG, "woofie", null);
        adopterDAO.save(adopter);

        adopters = adopterDAO.findAll();
        assertEquals(oldCount + 1, adopters.size());
    }

    @Test
    public void testGetAdopterWithExistingId() {

        BigAdopterEmbedded adopter = new BigAdopterEmbedded("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                PetType.DOG, "woofie", null);
        assertEquals(0, adopter.getId());
        var insertedAdopter = adopterDAO.save(adopter);

        adopter = adopterDAO.findById(insertedAdopter.getId()).orElse(null);
        assertNotNull(adopter);
    }

    @Test
    public void testGetAdopterWithNonExistingId() {

        BigAdopterEmbedded adopter = adopterDAO.findById(1000).orElse(null);
        assertNull(adopter);
    }

    @Test
    public void testInsert() {
        int oldCount = adopterDAO.findAll().size();

        BigAdopterEmbedded adopter = new BigAdopterEmbedded("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                PetType.DOG, "woofie", null);
        assertEquals(0, adopter.getId());
        adopter = adopterDAO.save(adopter);

        int newCount = adopterDAO.findAll().size();
        assertEquals(oldCount + 1, newCount);

        var foundAdopter = adopterDAO.findById(adopter.getId()).orElse(null);
        assertNotNull(foundAdopter);
        assertEquals("woofie", foundAdopter.getPet().getName());
    }

    @Test
    public void testDelete() {
        int testId = 1;
        BigAdopterEmbedded adopter = new BigAdopterEmbedded("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                PetType.DOG, "woofie", null);
        adopter = adopterDAO.save(adopter);

        BigAdopterEmbedded a = adopterDAO.findById(adopter.getId()).orElse(null);
        assertNotNull(a);

        adopterDAO.delete(a);

        a = adopterDAO.findById(adopter.getId()).orElse(null);
        assertNull(a);
    }

    @Test
    public void testUpdateExistingAdopter() {
        int testId = 1;
        BigAdopterEmbedded adopter = new BigAdopterEmbedded("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                PetType.DOG, "woofie", null);
        adopter = adopterDAO.save(adopter);

        BigAdopterEmbedded a = adopterDAO.findById(adopter.getId()).orElse(null);
        assertNotNull(a);

        String newName = "Martha";
        a.setName(newName);

        BigAdopterEmbedded result = adopterDAO.save(a);

        a = adopterDAO.findById(adopter.getId()).orElse(null);
        assertEquals(newName, a.getName());
    }
}
