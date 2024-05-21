package expeditors.backend.adoptapp.domain.embedded;

import expeditors.backend.adoptapp.domain.PetType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class PetEmbeddable {

    @Enumerated(EnumType.STRING)
    private PetType type;
    private String name;
    private String breed;

    private PetEmbeddable(PetType type, String name, String breed) {
        this.type = type;
        this.name = name;
        this.breed = breed;
    }

    public PetEmbeddable(PetType type) {
        this(type, null, null);
    }

    public PetEmbeddable() {
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
    public static EmbeddablePetBuilder builder(PetType type) {
        return new EmbeddablePetBuilder().type(type);
    }

    //The actual Builder
    public static class EmbeddablePetBuilder {
        private PetType type;
        private String name;
        private String breed;

        public EmbeddablePetBuilder type(PetType type) {
            this.type = type;
            return this;
        }
        public EmbeddablePetBuilder name(String name) {
            this.name = name;
            return this;
        }
        public EmbeddablePetBuilder breed(String breed) {
            this.breed = breed;
            return this;
        }

        public PetEmbeddable build() {
            if(this.type == null) {
                throw new RuntimeException("A type is REQUIRED");
            }
            return new PetEmbeddable(type, name, breed);
        }
    }
}
