package expeditors.backend.adoptapp.week2and3.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDomainObjects {

    @Test
    public void testCreateAdopterAndPet() {
        Pet fido = new Pet(Pet.PetType.DOG, "fido", "mongrel");
        Adopter charlie = new Adopter("Charlie", "383 939 03303", LocalDate.of(2000, 02, 07),
                fido);

        assertEquals("mongrel", charlie.getPet().getBreed());
    }

    @Test
    public void testPetBuilder() {
        Adopter charlie = new Adopter("Charlie", "383 939 03303", LocalDate.of(2000, 02, 07),
                Pet.builder(Pet.PetType.DOG)
                        .name("fido")
                        .breed("mongrel")
                        .build()
        );

        assertEquals("mongrel", charlie.getPet().getBreed());
    }

    @Test
    public void testSoloBuilder() {
        Pet pet = Pet.builder(Pet.PetType.DOG)
              .name("fido")
              .breed("mongrel")
              .build();
        System.out.println("pet: " + pet);

        assertEquals("fido", pet.getName());

//        Pet p2 = new Pet(Pet.PetType.DOG, "dalmation", "fido");
    }

    @Test
    public void testPetWithSetters() {
        Pet p = new Pet();
        p.setType(Pet.PetType.TURTLE);

        //Use it i
    }
}
