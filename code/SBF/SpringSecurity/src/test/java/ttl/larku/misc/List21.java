package ttl.larku.misc;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;

public class List21 {

    @Test
    public void testListForReveresed() {
        List<Integer> orig = new ArrayList(List.of(1, 2, 3));

        var reversed = orig.reversed();
        reversed.set(reversed.size() - 1, 25);
        out.println("reversed: " + reversed);

        out.println("orig: " + orig);
        orig.add(200);

        out.println("reversed after add: " + reversed);
    }
}
