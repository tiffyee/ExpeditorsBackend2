package expeditors.backend.adoptapp.domain;

import java.util.ArrayList;
import java.util.List;

public class NameAndPetsDTO {
   private String name;
   private List<Pet> pets;

   public NameAndPetsDTO(Adopter a) {
      this.name = a.getName();
      this.pets = new ArrayList<>();
      this.pets.addAll(a.getPets());
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<Pet> getPets() {
      return pets;
   }

   public void setPets(List<Pet> pets) {
      this.pets = pets;
   }

   @Override
   public String toString() {
      return "NameAndPetsDTO{" +
            "name='" + name + '\'' +
            ", pets=" + pets +
            '}';
   }
}
