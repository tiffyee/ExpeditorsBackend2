package ttl.larku.domain;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//public class PhoneNumber {
//
//   @NotNull
//   @Size(min = 10, message = "Phonenumber must be at least 10 digits")
//   private String number;
//
//   private Type type;
//
//   public PhoneNumber(Type type,
//                      String number) {
//      this.type = type;
//      this.number = number;
//   }
//
//   public PhoneNumber(String number) {
//      this(Type.MOBILE, number);
//   }
//
//   public void setNumber(String number) {
//      this.number = number;
//   }
//
//   public String getNumber() {
//      return number;
//   }
//
//   public void setType(Type type) {
//      this.type = type;
//   }
//
//   public Type getType() {
//      return type;
//   }
//
//   public enum Type {
//      HOME,
//      WORK,
//      MOBILE,
//      SATELLITE
//   }
//}

public record PhoneNumberDTO(Type type,
                          @NotNull
                          @Size(min = 10, message = "Phonenumber must be at least 10 digits")
                          String number) {
   public PhoneNumberDTO(String number) {
      this(Type.MOBILE, number);
   }

   public String getNumber() {
      return number;
   }

   public enum Type {
      HOME,
      WORK,
      MOBILE,
      SATELLITE
   }
}
