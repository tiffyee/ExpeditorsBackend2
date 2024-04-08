package week2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCreateList {

    @Test
    public void testCreateList(){
        int size = 10;
        int limit = 100;
        List<Integer> listInt = week2.ListExercise.createList(size,limit);

        //check length
        assertEquals(size, listInt.size());
    }
}
