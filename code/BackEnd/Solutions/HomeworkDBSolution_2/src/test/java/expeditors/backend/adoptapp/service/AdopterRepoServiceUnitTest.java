package expeditors.backend.adoptapp.service;


import expeditors.backend.adoptapp.dao.AdopterDAO;
import expeditors.backend.adoptapp.dao.repository.AdopterRepo;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AdopterRepoServiceUnitTest {

   @Mock
   private AdopterRepo dao;

   @InjectMocks
   private AdopterRepoService adopterRepoService;

   @Test
   public void testAddPetToExistingAdopter() {

      int testId = 1;

      Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
            Pet.builder(PetType.DOG).name("woofie").build());

      Pet newPet = Pet.builder(PetType.TURTLE).name("Shiny").build();

      Mockito.when(dao.findByIdWithPets(testId)).thenReturn(adopter);
      boolean result = adopterRepoService.addPetToAdopter(testId, newPet);
      assertTrue(result);

      Mockito.verify(dao).findByIdWithPets(testId);
   }

   @Test
   public void testAddPetToNonExistingAdopter() {

      int testId = 1;

      Pet newPet = Pet.builder(PetType.TURTLE).name("Shiny").build();

      Mockito.when(dao.findByIdWithPets(testId)).thenReturn(null);
      boolean result = adopterRepoService.addPetToAdopter(testId, newPet);
      assertFalse(result);

      Mockito.verify(dao).findByIdWithPets(testId);
   }

   @Test
   public void testRemovePetFromExistingAdopter() {

      int testId = 1;

      Pet newPet = Pet.builder(PetType.TURTLE).name("Shiny").build();
      newPet.setPetId(testId);

      Adopter adopter = new Adopter("Joey", "383 9999 9393",
            LocalDate.of(1960, 6, 9),
            newPet);

      Mockito.when(dao.findByIdWithPets(testId)).thenReturn(adopter);

      boolean result = adopterRepoService.removePetFromAdopter(testId, testId);
      assertTrue(result);

      Mockito.verify(dao).findByIdWithPets(testId);
   }

   @Test
   public void testRemovePetFromNonExistingAdopter() {

      int testId = 1;

      Mockito.when(dao.findByIdWithPets(testId)).thenReturn(null);

      boolean result = adopterRepoService.removePetFromAdopter(testId, testId);
      assertFalse(result);

      Mockito.verify(dao).findByIdWithPets(testId);
   }

   @Test
   public void testRemoveNonExistingPetFromExistingAdopter() {

      int testId = 1;

      Pet newPet = Pet.builder(PetType.TURTLE).name("Shiny").build();
      newPet.setPetId(testId);

      Adopter adopter = new Adopter("Joey", "383 9999 9393",
            LocalDate.of(1960, 6, 9),
            newPet);

      Mockito.when(dao.findByIdWithPets(testId)).thenReturn(adopter);

      boolean result = adopterRepoService.removePetFromAdopter(testId,
            9999);
      assertFalse(result);

      Mockito.verify(dao).findByIdWithPets(testId);
   }
}
