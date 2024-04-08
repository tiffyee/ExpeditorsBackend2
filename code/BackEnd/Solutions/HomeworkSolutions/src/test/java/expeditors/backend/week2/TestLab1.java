package expeditors.backend.week2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author whynot
 */
public class TestLab1 {

    /*
    a. Create an array of 10 ints
    b. Initialize each element of the array to the square of it’s index. e.g

    element[0] = 0
    element[1] = 1
    element[2] = 4
    etc.
    c. Write a JUnit test to make sure your code works. e.g. assert that element[6] == 36
    */
    @Test
    public void lab1() {
        int arr[] = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i * i;
        }

        assertEquals(36, arr[6]);
        assertEquals(81, arr[9]);
    }
}
