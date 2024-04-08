package expeditors.backend.week1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author whynot
 */
public class TestLab4 {

    @Test
    public void lab4Test() {
        int count = Lab3.withForLoop();
        assertEquals(476, count);

        count = Lab3.withWhileLoop();
        assertEquals(476, count);
    }
}
