package expeditors.backend.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentBuilderTest {

   @Test
   @DisplayName("Builder correctly initializes default values")
   public void testBuilderInitializesDefaultValues() {
      String name = "Charlie";
      LocalDate dob = LocalDate.of(1987, 10, 5);
      String phoneNumber1 = "393 93300 88458";
      String phoneNumber2 =  "+22 383 922 0902";
      Student student = Student.builder(name, dob)
            .phoneNumber(phoneNumber1, phoneNumber2)
            .build();


      assertEquals(name, student.getName());
      assertEquals(dob, student.getDob());
      assertEquals(Student.Status.FULL_TIME, student.getStatus());
      assertEquals(2, student.getPhoneNumbers().size());
      assertEquals(phoneNumber1, student.getPhoneNumbers().get(0));
   }
}
