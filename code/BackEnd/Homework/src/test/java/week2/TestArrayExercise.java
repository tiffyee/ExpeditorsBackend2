package week2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestArrayExercise {

    @Test
    public void testArrayExercise(){
        int[] arraySquares = week2.ArrayExercise.createArraySquares();

        assertEquals(36,arraySquares[5]);

    }

}
