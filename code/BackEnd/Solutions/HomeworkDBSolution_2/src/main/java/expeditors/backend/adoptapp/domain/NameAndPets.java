package expeditors.backend.adoptapp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record NameAndPets(String name, List<Pet> pets) {

   public NameAndPets(Adopter a) {
      this(a.getName(), List.copyOf(a.getPets()));
   }
}
