package ttl.expiditors.slides;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author whynot
 */
public class SlideExamples {

    @Test
    public void localDates() {
        //today:
        LocalDate now = LocalDate.now();
        //dob of 04/17/1956:
        LocalDate dob = LocalDate.of(1924, 04, 17);
        //Years between dob and today:
        long age = dob.until(LocalDate.now(), ChronoUnit.YEARS);

        assertEquals(99, age);
    }

    @Test
    public void ohBoyOhBoy() {
        int someVal = 0;
        var obob = STR."some val is \{someVal}";

        System.out.println(STR."obob: \{obob}");
    }
}


