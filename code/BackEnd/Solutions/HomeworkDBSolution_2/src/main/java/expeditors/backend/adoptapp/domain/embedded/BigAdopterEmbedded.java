package expeditors.backend.adoptapp.domain.embedded;

import expeditors.backend.adoptapp.domain.PetType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BIG_ADOPTER_EMBEDDED")
public class BigAdopterEmbedded {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "BIG_ADOPTER_PET", joinColumns = @JoinColumn(name = "ADOPTER_ID"))
    @AttributeOverrides({
            @AttributeOverride( name = "type", column = @Column(name = "PET_TYPE")),
            @AttributeOverride( name = "name", column = @Column(name = "PET_NAME")),
            @AttributeOverride( name = "breed", column = @Column(name = "PET_BREED")),
            @AttributeOverride( name = "adoptionDate", column = @Column(name = "ADOPT_DATE"))
    })
    private Set<PetEmbeddable> pets = new HashSet<>();

    public BigAdopterEmbedded(String name, String phoneNumber,
                              LocalDate adoptionDate, PetType petType, String petName, String petBreed) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        var petEmbeddable = PetEmbeddable.builder(petType).name(petName).breed(petBreed).build();
        this.pets.add(petEmbeddable);
    }

    public BigAdopterEmbedded() {
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


    public Set<PetEmbeddable> getPets() {
        return pets;
    }

    @Override
    public String toString() {
        return "BigAdopter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pet =" + pets +
                '}';
    }
}
