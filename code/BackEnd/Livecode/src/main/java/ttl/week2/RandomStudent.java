package ttl.week2;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStudent {

   public static void main(String[] args) {
      int[] iarr = new int[10];
      int[] iarr2 = {0, 25, 54, 220};

      String[] names = {
            "Alan Morales Rueda",
            "Andre Uys",
            "Audomaro Gonzalez",
            "Caio Henrique",
            "Chris Valencia",
            "Daniel Lee",
            "Humberto Rojas",
            "Javier Mendoza",
            "Joao Alonso",
            "Luis Barraza Hernandez",
            "Mariana Duarte",
            "Miguel Angel Rodriguez",
            "Rosendo Galindo",
            "Sean Jaw",
            "Tiffany Yee",
            "Vincent Vu",
            "Nathaniel Schieber",
            "Dylan McClain",
            "Grant Stampfli",
      };

      System.out.print("And the Winner is ");
      for(int i = 0; i < 3; i++) {
         getRandomStudent(names);
         System.out.print(".");
         sleep(500);
      }

      String result = getRandomStudent(names);
      System.out.println("result: " + result);
   }

   private static void sleep(int millis) {
      try {
         Thread.sleep(millis);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }
   public static String getRandomStudent(String[] array) {
      int num = ThreadLocalRandom.current().nextInt(array.length);

      String result = array[num];
      return result;
   }
}
