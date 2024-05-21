package expeditors.backend.app;

import expeditors.backend.domain.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ObjectCollections {

   public static void main(String[] args) {
//      List<Integer> lint = new ArrayList<>();
//      for(int i = 0; i < Integer.MAX_VALUE; i++) {
//         lint.add(i);
//      }
      int [] iarr = new int[10];

      Student [] sarr = new Student[2];

      sarr[0] = new Student("Johe", LocalDate.now(), "3768 944 92");
      sarr[1] = new Student("Johe", LocalDate.now(), "987 646 9229");

      List<Student> lStudent = new ArrayList<>();

      for(int i = 0; i < sarr.length; i++) {
         lStudent.add(sarr[i]);
      }



   }
}
