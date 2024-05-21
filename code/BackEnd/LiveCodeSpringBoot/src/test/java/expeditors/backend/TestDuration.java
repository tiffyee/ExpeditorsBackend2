package expeditors.backend;

import java.time.Duration;
import org.junit.jupiter.api.Test;

public class TestDuration {

   @Test
   public void testDuration() {
      Duration fiveMinAnd16Seconds = Duration.ofMinutes(5).plusSeconds(16);

      System.out.println(fiveMinAnd16Seconds);

      Duration otherWay = Duration.parse("PT5M16S");

      System.out.println(otherWay);
   }
}
