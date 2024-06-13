package ttl.mie.misc;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.junit.jupiter.api.Test;

public class TestDecimalFormat {

   @Test
   public void testDecimalFormat() {

      DecimalFormat decimalFormat = new DecimalFormat("0.00");

      var newPrice = new BigDecimal("8.8");

      var str = decimalFormat.format(newPrice);
      System.out.println("str: " + str);
   }
}
