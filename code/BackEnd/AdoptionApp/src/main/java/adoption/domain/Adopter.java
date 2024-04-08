package adoption.domain;

import java.time.LocalDate;

import static java.lang.StringTemplate.STR;

public class Adopter {
    private int id;
    private String name;
    private String phoneNumber;
    private LocalDate adoptionDate;
    Pet pet;
    public static int totalAdopterCount;

    public Adopter(int id, String name, String phoneNumber, LocalDate adoptionDate, Pet pet){
        setId(id);
        setName(name);
        setPhoneNumber(phoneNumber);
        setAdoptionDate(adoptionDate);
        this.pet = pet;
    }

    public Adopter(int id,String name) {
        this(id, name, null, null, null);
    }

    public Adopter(int id, String name, String phoneNumber) {
        this(id, name, phoneNumber,null,null);
    }

    public Adopter(String name, String phoneNumber){
        this(1,name,phoneNumber,null,null);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id == 0){
            throw new IllegalArgumentException("Invalid Argument: Adopter ID can't be zero or null.");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Invalid Argument: Adopter Name can't be null");
        }else if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Argument: Adopter Name can't be empty");
        }
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null){
            throw new IllegalArgumentException("Invalid Argument: Phone Number can't be null");
        } else if (phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Argument: Phone Number can't be empty");
        } else if (!phoneNumber.matches("([0-9]+(-[0-9]+)+)")){
            throw new IllegalArgumentException(STR."Invalid format: Phone Number entered was: \{phoneNumber}. Ex: xxx-xxx-xxxx");
        }
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    public Pet getPet(){
        return pet;
    }

    public void setPet(Pet pet){
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Adopter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", adoptionDate=" + adoptionDate +
                ", pet=" + pet +
                '}';
    }


}
