package adoption.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pet {

    public enum PetType {
        CAT,
        DOG,
        TURTLE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petId;
    private String name;
    private PetType type;
    private String breed;

    private static int totalPetCount;

    public Pet(){}

    public Pet(int petId, PetType type, String name, String breed){
        setPetId(petId);
        setType(type);
        setName(name);
        setBreed(breed);
    }

    public Pet(PetType type, String name, String breed){
        this(1,type,name,breed);
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        if (petId == 0){
            throw new IllegalArgumentException("Invalid Argument: Pet ID can't be zero or null.");
        }
        this.petId = petId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", breed='" + breed + '\'' +
                '}';
    }

    public static PetBuilder builder(PetType type) {
        return new PetBuilder().type(type);
    }

    public static class PetBuilder {
        private int petId;
        private PetType type;
        private String name;
        private String breed;

        public PetBuilder petId(int petId) {
            this.petId = petId;
            return this;
        }
        public PetBuilder type(PetType type) {
            this.type = type;
            return this;
        }
        public PetBuilder name(String name) {
            this.name = name;
            return this;
        }
        public PetBuilder breed(String breed) {
            this.breed = breed;
            return this;
        }

        public Pet build() {
            return new Pet(petId, type, name, breed);
        }
    }
}
