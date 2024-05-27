package adoption.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String phoneNumber;
    private LocalDate adoptionDate;
    //Pet pet;
//    @OneToMany( mappedBy = "adopter")
//    @JoinTable(name = "adopter_pet")
//    @JoinColumn(name = "adopter_id_fk")
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Pet> pets = new ArrayList<>();


    public Adopter(){}
    public Adopter(String name, String phoneNumber, LocalDate adoptionDate, Pet pet){
        setName(name);
        setPhoneNumber(phoneNumber);
        setAdoptionDate(adoptionDate);
        if (pet != null) {
            addPet(pet);
        }
    }

    public Adopter(String name, String phoneNumber) {
        this(name, phoneNumber,null,null);
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
        return pets.isEmpty() ? null : pets.getFirst();
    }

    public void setPet(Pet pet){
        if (pet != null){
            if (pets.isEmpty()){
                addPet(pet);
            }else {
                pets.set(0,pet);
            }
        }
    }
    public void addPet(Pet pet){
        this.pets.add(pet);
    }

    public List<Pet> getPets(){
        return List.copyOf(pets);
    }

    @Override
    public String toString() {
        return "Adopter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", adoptionDate=" + adoptionDate +
                ", pets =" + pets +
                '}';
    }


}
