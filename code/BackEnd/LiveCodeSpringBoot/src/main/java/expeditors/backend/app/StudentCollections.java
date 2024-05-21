package expeditors.backend.app;

import expeditors.backend.domain.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

record Point(int x, int y){}

public class StudentCollections {

   public static void main(String[] args) {

      int [] iarr = new int[10];

      Student [] sarr = new Student[2];

      sarr[0] = new Student("Wilma", LocalDate.of(2000, 10, 10), "w@xyz.com");
      sarr[1] = new Student("Seema", LocalDate.of(2000, 10, 10), "sma@zyx.com");

      int len = sarr[0].getName().length();


      List<Student> list = new ArrayList<>();
      for(int i = 0; i < sarr.length; i++) {
         list.add(sarr[i]);
      }
   }
}
