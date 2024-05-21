package expeditors.backend.adoptapp.service;

import expeditors.backend.adoptapp.dao.AdopterDAO;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author whynot
 */
@ExtendWith(MockitoExtension.class)
public class AdopterServiceUnitTest {

    @Mock
    private AdopterDAO adopterDAO;

    @InjectMocks
    private AdopterService adopterService;

    @BeforeEach
    public void init() {
//        DAOFactory.clear();
//        adopterService = DAOFactory.adopterService();
    }

    List<Adopter> adopters = List.of(
          new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build()),
          new Adopter("H2-Francine", "383 9339 9999 9393", LocalDate.parse("2020-05-09"),
                Pet.builder(Pet.PetType.DOG).name("slinky").breed("dalmation").build()),
          new Adopter("H2-Darlene", "44484 9339 77939", LocalDate.parse("2020-05-09"),
                Pet.builder(Pet.PetType.TURTLE).name("swifty").build()),
          new Adopter("H2-Miguel", "77 888 93938", LocalDate.parse("2022-03-09"),
                Pet.builder(Pet.PetType.DOG).name("woofwoof").breed("Terrier").build())
    );

    @Test
    public void testAddAdopter() {
        Mockito.when(adopterDAO.insert(adopters.get(0))).thenReturn(adopters.get(0));

        Adopter adopter = adopterService.addAdopter(adopters.get(0));

       Mockito.verify(adopterDAO).insert(adopters.get(0));
    }

    @Test
    public void testGetAll() {

        Mockito.when(adopterDAO.findAll()).thenReturn(adopters);

        List<Adopter> adopters = adopterService.getAllAdopters();

        adopters.forEach(System.out::println);

        assertTrue(adopters.size() == 4);

        Mockito.verify(adopterDAO).findAll();
    }

    @Test
    public void testGetOneWithGoodId() {
        int id = 1;
        Mockito.when(adopterDAO.findById(id)).thenReturn(adopters.get(0));
        Adopter a = adopterService.getAdopter(id);
        assertNotNull(a);

        Mockito.verify(adopterDAO).findById(id);
    }

    @Test
    public void testGetOneWithNonExistentId() {
        int id = 1000;
        Mockito.when(adopterDAO.findById(id)).thenReturn(null);
        Adopter adopter = adopterService.getAdopter(id);
        assertNull(adopter);

        Mockito.verify(adopterDAO).findById(id);
    }

    @Test
    public void testDeleteWithExistingAdopter() {
        int id = 1;
        Mockito.when(adopterDAO.delete(id)).thenReturn(true);

        boolean result = adopterService.deleteAdopter(id);
        assertTrue(result);

        Mockito.verify(adopterDAO).delete(id);
    }

    @Test
    public void testDeleteNonExistingAdopter() {

        int id = 1000;
        Mockito.when(adopterDAO.delete(id)).thenReturn(false);
        boolean result = adopterService.deleteAdopter(id);
        assertFalse(result);
        Mockito.verify(adopterDAO).delete(id);
    }

    @Test
    public void testUpdateWithExistingAdopter() {

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());

        Mockito.when(adopterDAO.update(adopter)).thenReturn(true);

        boolean result = adopterService.updateAdopter(adopter);
        assertTrue(result);

        Mockito.verify(adopterDAO).update(adopter);
    }

    @Test
    public void testUpdateNonExistingAdopter() {
        int id = 1000;
        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
              Pet.builder(Pet.PetType.DOG).name("woofie").build());

        Mockito.when(adopterDAO.update(adopter)).thenReturn(false);

        boolean result = adopterService.updateAdopter(adopter);
        assertFalse(result);

        Mockito.verify(adopterDAO).update(adopter);
    }

    @Test
    public void testGetByPetType() {

        Mockito.when(adopterDAO.findAll()).thenReturn(adopters);

        List<Adopter> result =
              adopterService.getAdoptersByPetType(Pet.PetType.TURTLE);
        assertEquals(1, result.size());

        Mockito.verify(adopterDAO).findAll();

    }
}
