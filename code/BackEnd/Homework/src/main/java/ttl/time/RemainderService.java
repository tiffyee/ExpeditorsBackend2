package ttl.time;

public class RemainderService {
    public static int getRemainder(int numerator, int denominator) {
        // 11 / 10   ==> 1 1/10
        // 10/11   ==> 0 10/11

        int rem = numerator % denominator;
        return rem;
    }
}
