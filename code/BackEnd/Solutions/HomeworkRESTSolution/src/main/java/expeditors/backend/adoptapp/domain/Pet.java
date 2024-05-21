package expeditors.backend.adoptapp.domain;

import jakarta.validation.constraints.NotNull;

public class Pet {

    private int petId;
    private String name;
    @NotNull
    private PetType type;
    private String breed;

    private Pet(int petId, PetType type, String name, String breed) {
        this.petId = petId;
        this.type = type;
        this.name = name;
        this.breed = breed;
    }

    public Pet(PetType type, String name, String breed) {
        this.type = type;
        this.name = name;
        this.breed = breed;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public Pet(PetType type) {
        this(type, null, null);
    }

    public Pet() {
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
                "name='" + name + '\'' +
                ", type=" + type +
                ", breed='" + breed + '\'' +
                '}';
    }

    //A Builder for Pets
    //Some static Factory methods to make the builder easier to
    //create.
    public static PetBuilder builder(PetType type) {
        return new PetBuilder().type(type);
    }

    //The actual Builder
    public static class PetBuilder {
        private int petId;
        private PetType type;
        private String name;
        private String breed;

        public PetBuilder petId(int id) {
            this.petId = id;
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
//            if(this.type == null) {
//                throw new RuntimeException("A type is REQUIRED");
//            }
            return new Pet(type, name, breed);
        }
    }
}
