package adoption.app;

import adoption.domain.Adopter;
import adoption.domain.Pet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdoptionApp {
    public static void main(String[] args) {
        Pet pet1 = new Pet(1, Pet.PetType.CAT,"Marble","Tabby");
        Pet pet2 = new Pet(2, Pet.PetType.DOG,"Moose","Bernadoodledr");
        Pet pet3 = new Pet(3,Pet.PetType.DOG,"Jon","Clydesdale");

        List<Adopter> adopters = new ArrayList<>();
        adopters.add(new Adopter("John Doe","333-333-3333", LocalDate.of(2024, 3, 11),pet1));
        adopters.add(new Adopter("Jane Doe","123456-7890", LocalDate.of(2024, 3, 12),pet2));


        for(Adopter adopter: adopters) {
            System.out.println(adopter.toString());
        }
    }
}
