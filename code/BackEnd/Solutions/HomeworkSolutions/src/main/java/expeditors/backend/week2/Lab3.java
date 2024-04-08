package expeditors.backend.week2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author whynot
 */
public class Lab3 {

    public static List<Integer> createList(int size, int limit) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            result.add(ThreadLocalRandom.current().nextInt(limit + 1)); //+ 1 to include limit
        }

        return result;
    }
}
