package week1;

public class CountDivisible {
    public static int getCount(int start, int end) {
        int count = 0;
        for (int i = start; i <= end; i++) {
            int result_3 = getRemainder(i, 3);
            int result_7 = getRemainder(i, 7);
            //Increment count if number i is divisible by 3 or 7
            if (result_3 == 0 || result_7 == 0) {
                count++;
            }
        }
        System.out.println(STR."Count of numbers between \{start} and \{end} that are divisible by 3 or 7 is: \{count}");
        return count;
    }
    public static int getRemainder(int numerator, int denominator){
        // 11 / 10 ===> 1 1/10

        int rem = numerator % denominator;
        return rem;
    }
}
