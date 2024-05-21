package adoption.service;

import adoption.dao.inmem.InMemoryAdopterDAO;
import adoption.domain.Adopter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class AdoptionServiceUnitTest {

    private String name1 = "Tiffany";
    private String phoneNumber1 = "123-456-7890";
    private LocalDate adoptionDate1 = LocalDate.of(2024,3,15);
    private String name2 = "John";
    private String phoneNumber2 = "333-333-3333";
    private LocalDate adoptionDate2 = LocalDate.of(2022,10,29);

    @Mock
    private InMemoryAdopterDAO adopterDAO;

    @InjectMocks
    private AdopterService adopterService;

    @Test
    public void testCreateAdopter() {
        Adopter a = new Adopter(name1, phoneNumber1, null, null);

        Mockito.when(adopterDAO.insert(a)).thenReturn(a);

        Adopter newAdopter = adopterService.addAdopter(a);
        assertEquals(a.getName(), "Tiffany");
        Mockito.verify(adopterDAO).insert(a);

    }

}
