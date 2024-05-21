package expeditors.backend.adoptapp.domain;

import expeditors.backend.adoptapp.domain.embedded.BigAdopterEmbedded;
import expeditors.backend.adoptapp.domain.embedded.PetEmbeddable;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBigAdopterEmbedded {

    @Test
    public void testCreateBigAdopterEmbedded() {
        var petEmbeddable = PetEmbeddable.builder(PetType.TURTLE).name("Pickles").build();
        var bae = new BigAdopterEmbedded("Jacky", "3890 585 92392", LocalDate.of(1999, 10, 8),
                PetType.TURTLE, "Pickles", "");

        var pet = bae.getPet();
        assertEquals("Pickles", pet.getName());
    }
}
