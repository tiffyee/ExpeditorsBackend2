package adoption.controller;

import adoption.domain.Adopter;
import adoption.service.AdopterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AdopterControllerUnitTest {

    @Mock
    private AdopterService adopterService;

    @Mock
    private UriCreator uriCreator;

    @InjectMocks
    private AdopterController controller;

    List<Adopter> adopters = List.of(
            new Adopter("john doe", "415-123-4567"),
            new Adopter("Jane Doe","123-123-1234")
    );

    @Test
    public void testGetAll(){

        Mockito.when(adopterService.getAllAdopters()).thenReturn(adopters);

        List<Adopter> result = controller.getAllAdopters();

        assertEquals(2,result.size());

        Mockito.verify(adopterService).getAllAdopters();
    }

    @Test
    public void testAddAdopter() throws URISyntaxException {
        String expectedLocHeader = "http://localhost:8080/adopter/0";

        URI expectedURI = new URI(expectedLocHeader);

        Mockito.when(adopterService.addAdopter(adopters.get(0))).thenReturn(adopters.get(0));
        Mockito.when(uriCreator.getURI(adopters.getFirst().getId())).thenReturn(expectedURI);

        ResponseEntity<?> response = controller.addAdopter(adopters.getFirst());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String actualLocHeader = response.getHeaders().getFirst("Location");

        assertEquals(expectedLocHeader, actualLocHeader);

        Mockito.verify(adopterService).addAdopter(adopters.getFirst());
        Mockito.verify(uriCreator).getURI(adopters.getFirst().getId());
    }

    @Test
    public void testGetAdopterWithbadId(){
        Mockito.when(adopterService.findById(1000)).thenReturn(null);

        ResponseEntity<?> response = controller.getAdopter(1000);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Mockito.verify(adopterService).findById(1000);
    }
}
