package week2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreateArray {

    @Test
    public void testCreateArray(){
        int size = 10;
        int limit = 100;
        int[] arrInt = week2.ArrayExercise.createArray(size,limit);

        assertEquals(size, arrInt.length);
    }
}
