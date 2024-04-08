package ttl.time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRemainderService {

    @Test
    public void testRemainderService() {
        int a = 10;
        int b = 11;

        int result = RemainderService.getRemainder(a, b);
        assertEquals(10, result);
    }
}
