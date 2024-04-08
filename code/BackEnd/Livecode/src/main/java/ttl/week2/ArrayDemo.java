package ttl.week2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArrayDemo {

   public static void main(String[] args) {
//      arrayDemo();
      listDemo();
   }

   public static void arrayDemo() {
     int i = 10;

     int [] iarr;

     iarr = new int[10];

     int [] iarr2 = iarr;

     iarr2 = new int[10];

     //iarr[10] = 35;

     doSomething(10);

   }

   public static void listDemo() {
      List<Integer> linit = new ArrayList<>();
      Set<Integer> aSet = new HashSet<>();
      Map<String, Integer> aMap = new HashMap<>();

      linit.add(10);

      int anInt = linit.get(0);

     for(int i = 0; i < 10000; i++) {
        linit.add(i);
     }

      for(int i = 0; i < 100; i++) {
         int val = linit.get(i);
      }

      for(int val : linit) {
         System.out.println("val: " + val);
      }
   }

   public static void doSomething(int size) {
      int [] iarr = new int[size];

      //do some work with it.
   }
}
