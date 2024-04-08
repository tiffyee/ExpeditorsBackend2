package week1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCountDivisible {
    @Test
    public void testCountDivisible(){
        int a = -500;
        int b = 500;
        int result = CountDivisible.getCount(a,b);
        assertEquals(429,result);
    }
}
