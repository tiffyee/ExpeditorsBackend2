package expeditors.backend.domain;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {

   public enum Status {
      FULL_TIME,
      PART_TIME,
      HIBERNATING
   }

   private int id;

   @NotNull
   private String name;
   @NotNull
   private LocalDate dob;

   private List<String> phoneNumbers = new ArrayList<>();
   private Status status = Status.FULL_TIME;   //FULL_TIME, PART_TIME, HIBERNATING

   public Student() {
      int stop = 0;
   }

   public Student(String name, LocalDate dob, String... phoneNumbers) {
      this(0, name, dob, Status.FULL_TIME, List.of(phoneNumbers));
   }

   public Student(String name, LocalDate dob, Status status, String... phoneNumbers) {
      this(0, name, dob, status, List.of(phoneNumbers));
   }

   private Student(int id, String name, LocalDate dob, Status status, List<String> phoneNumbers) {
      this.id = id;
      this.name = name;
      this.dob = dob;
      this.status = status;

      if (phoneNumbers != null) {
         this.phoneNumbers.addAll(phoneNumbers);
      }
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

   public LocalDate getDob() {
      return dob;
   }

   public void setDob(LocalDate dob) {
      this.dob = dob;
   }

   public List<String> getPhoneNumbers() {
      return List.copyOf(phoneNumbers);
   }

   public void setPhoneNumbers(List<String> phoneNumbers) {
      this.phoneNumbers.clear();
      this.phoneNumbers.addAll(phoneNumbers);
   }

   public void addPhoneNumber(String phoneNumber) {
      Objects.requireNonNull(phoneNumber);
      this.phoneNumbers.add(phoneNumber);
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   @Override
   public String toString() {
      return "Student{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", dob=" + dob +
            ", phoneNumbers='" + phoneNumbers + '\'' +
            ", status=" + status +
            '}';
   }

   public static Builder builder(String name, LocalDate dob) {
      return new Builder()
            .name(name)
            .dob(dob);
   }

   public static class Builder {
      private int id = 0;
      private String name;
      private LocalDate dob;
      private List<String> phoneNumbers = new ArrayList<>();

      private Status status = Status.FULL_TIME;   //FULL_TIME, PART_TIME, HIBERNATING

      private Builder() {
      }

      public Builder id(int id) {
         this.id = id;
         return this;
      }

      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder dob(LocalDate dob) {
         this.dob = dob;
         return this;
      }

      public Builder status(String status) {
         return status(Status.valueOf(status));
      }

      public Builder status(Status status) {
         this.status = status;
         return this;
      }

      public Builder phoneNumber(String... phoneNumbers) {
         return phoneNumber(List.of(phoneNumbers));
      }

      public Builder phoneNumber(List<String> phoneNumbers) {
         this.phoneNumbers.addAll(phoneNumbers);
         return this;
      }

      public Student build() {
         if (name == null || dob == null) {
            throw new IllegalArgumentException("Name and dob should not be null");
         }
         return new Student(0, name, dob, status, phoneNumbers);
      }
   }
}
