package ttl.larku.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Student {

   public enum Status {
      FULL_TIME,
      PART_TIME,
      HIBERNATING
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @NotNull
   private String name;

   //    @Size(min = 10, message = "Phonenumber must be at least 10 digits")
   @NotNull
   @Valid
//   @Transient
//   @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)

   //Using a JoinColum will generate some unnecessary SQL
   //The name is the name of the foreign key column in the PhoneNumber
   // table.  You would this approach if you really really really don't
   // want to have a Student reference in PhoneNumber.
   @JoinColumn(name = "student_id")
   //@JoinTable will generate even more unnecessary SQL.
//   @JoinTable
//         (
//               name="student_phones",
//               joinColumns={ @JoinColumn(name="student_id", referencedColumnName="id") },
//               inverseJoinColumns={ @JoinColumn(name="phone_id", referencedColumnName="id", unique=true) }
//         )

   //A List is generally a bad idea with Hibernate if you use a
   // join table .  To see why, use the @JoinTable above and a List to hold the
   // phonenumbers. add a bunch of phoneNumbers, and then try and delete one.
   // Watch the ensuing fun in the SQL.  Lots of deletes and inserts.
//   private Set<PhoneNumber> phoneNumbers = new HashSet<>();
   private List<PhoneNumber> phoneNumbers = new ArrayList<>();

   @JsonDeserialize(using = LocalDateDeserializer.class)
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate dob;

   @Enumerated(EnumType.STRING)
   private Status status = Status.FULL_TIME;

   @Transient
   private List<ScheduledClass> classes = new ArrayList<>();

   private static int nextId = 0;

   public Student() {
//        this("Unknown");
      this(null);
   }

   public Student(String name) {
      this(name, null, null, Status.FULL_TIME, new ArrayList<ScheduledClass>());
   }

   public Student(String name, String phoneNumber, Status status) {
      this(name, phoneNumber, null, status, new ArrayList<ScheduledClass>());
   }

   public Student(String name, String phoneNumber, LocalDate dob, Status status) {
      this(name, phoneNumber, dob, status, new ArrayList<ScheduledClass>());
   }

   public Student(String name, String phoneNumber, LocalDate dob, Status status, List<ScheduledClass> classes) {
      super();
      this.name = name;
      this.status = status;
      if (phoneNumber != null && !phoneNumber.isBlank()) {
         this.phoneNumbers.add(new PhoneNumber(phoneNumber));
      }
      this.dob = dob;
      setClasses(classes);
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

   @JsonIgnore
   public Status[] getStatusList() {
      return Status.values();
   }

   public String getPhoneNumber() {
      //return !phoneNumbers.isEmpty() ? phoneNumbers.get(0).getNumber() : null;
      return !phoneNumbers.isEmpty() ? phoneNumbers.stream().findFirst().orElse(null).getNumber() : null;
   }

   public List<PhoneNumber> getPhoneNumbers() {
      return List.copyOf(phoneNumbers);
   }

   public void setPhoneNumber(String phoneNumber) {
      if (phoneNumbers.isEmpty()) {
         addPhoneNumber(new PhoneNumber(phoneNumber));
      } else {
         this.phoneNumbers.set(0, new PhoneNumber(phoneNumber));
//         this.phoneNumbers.add(new PhoneNumber(phoneNumber));
      }
   }

   public void addPhoneNumber(String phoneNumber) {
      addPhoneNumber(new PhoneNumber(phoneNumber));
   }

   public void addPhoneNumber(PhoneNumber phoneNumber) {
      this.phoneNumbers.add(phoneNumber);
   }

   public void removePhoneNumber(PhoneNumber phoneNumber) {
      this.phoneNumbers.remove(phoneNumber);
   }

   public LocalDate getDob() {
      return dob;
   }


   public void setDob(LocalDate dob) {
      this.dob = dob;
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }


   public List<ScheduledClass> getClasses() {
      return List.copyOf(classes);
   }

   public void setClasses(List<ScheduledClass> classes) {
      if (classes != null) {
         this.classes.clear();
         this.classes.addAll(classes);
      }
   }


   public void addClass(ScheduledClass sClass) {
      classes.add(sClass);
   }

   public void dropClass(ScheduledClass sClass) {
      classes.remove(sClass);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Student student = (Student) o;
      return id == student.id && name.equals(student.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, name);
   }

   @Override
   public String toString() {
      return "Student{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", phoneNumber='" + phoneNumbers + '\'' +
            ", dob=" + dob +
            ", status=" + status +
            ", classes=" + classes +
            '}';
   }
}
