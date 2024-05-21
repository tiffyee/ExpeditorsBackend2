package ttl.larku.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class PhoneNumber{
   public enum Type {
      HOME,
      WORK,
      MOBILE,
      SATELLITE
   }

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @NotNull
   @Size(min = 10, message = "Phonenumber must be at least 10 digits")
   private String number;

   @Enumerated(EnumType.STRING)
   private Type type;

//   @ManyToOne(fetch = FetchType.LAZY)
//   private Student student;

   public PhoneNumber(Type type,
                      String number) {
      this.type = type;
      this.number = number;
   }

   public PhoneNumber(String number) {
      this(Type.MOBILE, number);
   }

   public PhoneNumber() {}

   public void setNumber(String number) {
      this.number = number;
   }

   public String getNumber() {
      return number;
   }

   public void setType(Type type) {
      this.type = type;
   }

   public Type getType() {
      return type;
   }

//   public Student getStudent() {
//      return student;
//   }
//
//   public void setStudent(Student student) {
//      this.student = student;
//   }

   @Override
   public String toString() {
      return "PhoneNumber{" +
            "id=" + id +
            ", number='" + number + '\'' +
            ", type=" + type +
            '}';
   }
}

