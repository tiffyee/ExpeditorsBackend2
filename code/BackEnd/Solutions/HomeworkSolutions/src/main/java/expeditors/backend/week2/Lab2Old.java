package expeditors.backend.week2;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author whynot
 */
public class Lab2Old {
    /*
        2. Arrays and Methods
    a. Write a method called createArray that takes two arguments,
    called size and limit. It should create and return an int array of
    length size, initialized with random integers between zero and limit.
    Remember to make your method static for now.
    b. Use ThreadLocalRandom.current().nextInt(limit) to create a random number. e.g.
    int rand = ThreadLocalRandom.current().nextInt(10) will create a
    random number between 0 and 10 exclusive.
    c. Check out the API documentation for other ways to create random numbers.
    d. Create an appropriate JUnit test to make sure your code works.
     */

    public static int [] createArray(int size, int limit) {
        int [] result = new int[size];
        for(int i = 0; i < result.length; i++) {
            result[i] = ThreadLocalRandom.current().nextInt(limit + 1); //+ 1 to include limit
        }

        return result;
    }
}
