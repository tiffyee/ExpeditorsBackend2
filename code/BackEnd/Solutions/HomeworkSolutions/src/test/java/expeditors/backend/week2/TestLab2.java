package expeditors.backend.week2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author whynot
 */
public class TestLab2 {

    @Test
    public void testCreateArray() {
        int size = 22;
        int limit = 10000;
        int [] result = Lab2Old.createArray(size, limit);

        //check the length
        assertEquals(size, result.length);

        //make sure no numbers above limit
        //One way would be to find the max value
        int max = Integer.MIN_VALUE;
        for(int i : result) {
            if(i > max) {
                max = i;
            }
        }
        assertTrue(max <= limit);

        //But that goes through the whole array
        //every time.
        //If you don't care about the max value,
        //as we don't for this test, then a potentially
        //faster way would be to break out of the loop
        //when you first encounter a value > limit
        //find a value
        boolean allGood = true;
        for(int i : result) {
            if(i > limit) {
               allGood = false;
               break;
            }
        }

        assertTrue(allGood);
    }
}
