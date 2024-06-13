package expeditors.backend.adoptapp.dao.repo;

import expeditors.backend.adoptapp.dao.repository.AdopterRepo;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.OnlyAdopterDTO;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author whynot
 */
@SpringBootTest
public class TestAdopterRepo {

    @Autowired
    private AdopterRepo adopterRepo;

    @Test
    public void testGetAll() {
        List<Adopter> adopters = adopterRepo.findAll();
        int oldCount = adopters.size();

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        adopterRepo.save(adopter);

        adopters = adopterRepo.findAll();
        assertEquals(oldCount + 1, adopters.size());
    }

    @Test
    public void testGetAdopterWithExistingId() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        assertEquals(0, adopter.getId());
        adopterRepo.save(adopter);

        adopter = adopterRepo.findById(1).orElse(null);
        assertNotNull(adopter);
    }

    @Test
    public void testGetAdopterWithNonExistingId() {

        Adopter adopter = adopterRepo.findById(1000).orElse(null);
        assertNull(adopter);
    }

    @Test
    public void testInsert() {
        int oldCount = adopterRepo.findAll().size();

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        assertEquals(0, adopter.getId());
        adopter = adopterRepo.save(adopter);

        int newCount = adopterRepo.findAll().size();
        assertEquals(oldCount + 1, newCount);
    }

    @Test
    public void testDelete() {
        int testId = 1;
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        adopter = adopterRepo.save(adopter);

        Adopter a = adopterRepo.findById(testId).orElse(null);
        assertNotNull(a);

        adopterRepo.delete(a);

        a = adopterRepo.findById(testId).orElse(null);
        assertNull(a);
    }

    @Test
    public void testUpdateExistingAdopter() {
        int testId = 1;
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").build());
        adopter = adopterRepo.save(adopter);

        Adopter a = adopterRepo.findById(testId).orElse(null);
        assertNotNull(a);

        String newName = "Martha";
        a.setName(newName);

        Adopter result = adopterRepo.save(a);

        a = adopterRepo.findById(testId).orElse(null);
        assertEquals(newName, a.getName());
    }

    @Test
    public void testGetOnlyAdtoperInfo() {
        Pageable pageAble = PageRequest.of(0, 10);
        Page<OnlyAdopterDTO> onlyAdopters = adopterRepo.findAllOnlyAdopterByPage(pageAble);

        System.out.println("onlyAdopters: " + onlyAdopters.getContent().size());

        System.out.println(onlyAdopters);

    }
}
