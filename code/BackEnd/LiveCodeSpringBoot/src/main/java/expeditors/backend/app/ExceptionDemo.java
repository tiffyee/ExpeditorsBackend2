package expeditors.backend.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionDemo {

   private Logger logger = LoggerFactory.getLogger(this.getClass().getPackageName());

   public void main(String[] args) {
         fun(9);
   }

   public void fun(int i) {
      try {
         bar(i);
      }catch(BadIValueException ex) {

      }
   }

   public void bar(int i) {
      if (i < 10) {
         throw new BadIValueException("Bad i");
      }

      String str = null;
      System.out.println(str.length());
   }
}

class BadIValueException extends RuntimeException {
   public BadIValueException(String msg) {
      super(msg);
   }
}
