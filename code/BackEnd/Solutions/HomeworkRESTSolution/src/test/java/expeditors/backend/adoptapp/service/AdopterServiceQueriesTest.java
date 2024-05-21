package expeditors.backend.adoptapp.service;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
public class AdopterServiceQueriesTest {

    @Autowired
    private AdopterService adopterService;


    @Test
    public void testGetAdoptersBy() {
        var queryStrings = Map.of("name", "Cmd-Francine");
        List<Adopter> result = adopterService.getAdoptersBy(queryStrings);
        System.out.println("result: " + result.size());
        result.forEach(System.out::println);

        assertEquals(1, result.size());

    }

    @Test
    public void testGetAdoptersByPetType() {
        var queryStrings = Map.of("pet.type", "TURTLE");
        List<Adopter> result = adopterService.getAdoptersBy(queryStrings);
        System.out.println("result: " + result.size());
        result.forEach(System.out::println);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetAdoptersByMultipleProperties() {
        var queryStrings = Map.of("pet.type", "TURTLE",
              "name", "Francine");
        List<Adopter> result = adopterService.getAdoptersBy(queryStrings);
        System.out.println("result: " + result.size());
        result.forEach(System.out::println);

        assertEquals(2, result.size());
    }
}