package expeditors.backend.app;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStudent {

   public void main(String[] args) {

      String[] cohort1Names = {
            "Alan Aguillon Juarez",
            "Antonio Nazco",
            "Antony Alfaro",
            "Arjun Panikar",
            "Carla Cairns",
            "Edwin Soto",
            "Jesus Cortez Valdez",
            "Juan De Dios Hernandez",
            "Julio Cesar Rodriguez",
            "Komal Patel",
            "Lokesh Gopi",
            "Lucas Maesaka",
            "Mainor Lobo",
            "Marcus Silva",
            "Raul Gomez",
            "Rohit Aherwadkar",
            "Tetyana Alvarado",
      };

      System.out.print("And the Winner is ");
      for(int i = 0; i < 4; i++) {
         getRandomStudent(cohort1Names);
         System.out.print(".");
         sleep(500);
      }

      String randomStudent = getRandomStudent(cohort1Names);
      System.out.println(STR." \{randomStudent}!!");
   }

   private void sleep(int millis) {
      try {
         Thread.sleep(millis);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }

   public String getRandomStudent(String [] arr) {
      int rNum = ThreadLocalRandom.current().nextInt(arr.length);
      String s = arr[rNum];
      return s;
   }
}


