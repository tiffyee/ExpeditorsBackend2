package expeditors.backend.adoptapp.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class Adopter {

    private int id;

    @NotNull
    private String name;
    private String phoneNumber;

    @NotNull
    private LocalDate adoptionDate;

    //If you also want to validate the Pet when an Adopter is validated.
    @Valid
    @NotNull
    Pet pet;

    public Adopter(String name, String phoneNumber, LocalDate adoptionDate, Pet pet) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.adoptionDate = adoptionDate;
        this.pet = pet;
    }

    public Adopter(String name, String phoneNumber) {
        this(name, phoneNumber, null, null);
    }

    public Adopter() {
        int stop = 10;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
        this.adoptionDate = LocalDate.now();
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
