package expeditors.backend.app;

import java.io.FileInputStream;
import java.io.IOException;

public class TryWithResources {


   public void main(String[] args) {
      //doit();
      tryWithResources();
   }


   public void doit() {

      FileInputStream fis = null;
      try {
         //Acquire some resource
         //Open a file
         fis = new FileInputStream("pom.xml");
         int value = fis.read();

         //Use the resource
         System.out.println("value: " + value);

         String s = null;
         System.out.println(s.length());

//         fis.close();
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         //Release the resource
         try {
            fis.close();
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }
   }

   public void tryWithResources() {
      try (FileInputStream fis = new FileInputStream("pom.xml");
           MyClass mc = new MyClass()) {
         int value = fis.read();

         //Use the resource
         System.out.println("value: " + value);

         mc.sayIt();

         String s = null;
         System.out.println(s.length());

      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   class MyClass implements AutoCloseable {

      public void sayIt() {
         System.out.println("said it");
      }

      @Override
      public void close() {
         System.out.println("Myclass.close called");
      }
   }
}

