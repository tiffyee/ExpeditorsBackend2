package adoption.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestCreatePetAndAdopter {

    @Test
    public void testValidPetID(){
        Pet pet1 = new Pet(1, Pet.PetType.CAT,"Marble","Tabby");
        assertEquals(1,pet1.getPetId());
    }

//    @Test
//    public void testInvalidPetID(){
//        assertThrows(IllegalArgumentException.class,() -> new Pet(0, Pet.PetType.CAT,"Marble","Tabby"));
//    }

    @Test
    @DisplayName("Adopter Name")
    public void testValidAdopterName(){
        Pet pet1 = new Pet(1, Pet.PetType.CAT,"Marble","Tabby");
        Adopter adopter1 = new Adopter("John Doe","333-333-3333",LocalDate.of(2024, 3, 11),pet1);
        assertEquals("John Doe",adopter1.getName());
    }

    @Test
    public void testNullAdopterName(){
        Pet pet1 = new Pet(1, Pet.PetType.CAT,"Marble","Tabby");
        assertThrows(IllegalArgumentException.class,() -> new Adopter(null,"333-333-3333",LocalDate.of(2024, 3, 11),pet1));
    }

    @Test
    public void testBlankAdopterName(){
        Pet pet1 = new Pet(1, Pet.PetType.CAT,"Marble","Tabby");
        assertThrows(IllegalArgumentException.class,() -> new Adopter("","333-333-3333",LocalDate.of(2024, 3, 11),pet1));
    }

    @Test
    public void testInvalidPhoneNumber(){
        Pet pet1 = new Pet(1, Pet.PetType.CAT,"Marble","Tabby");
        assertThrows(IllegalArgumentException.class,() -> new Adopter("John Doe","(333)333-3333",LocalDate.of(2024, 3, 11),pet1));
    }

}