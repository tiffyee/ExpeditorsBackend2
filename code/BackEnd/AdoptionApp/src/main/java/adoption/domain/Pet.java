package adoption.domain;

public class Pet {

    public enum PetType {
        CAT,
        DOG,
        TURTLE
    }
    private int petId;
    private String name;
    private PetType type;
    private String breed;
    private static int totalPetCount;

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


}
