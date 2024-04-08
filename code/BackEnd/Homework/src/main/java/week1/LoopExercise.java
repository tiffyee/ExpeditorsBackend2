package week1;

public class LoopExercise {

    public static void main(String[] args) {
        //for3And7();
        //while3And7();
        count3And7();
    }

    public static void for3And7 () {
        for (int i = -500; i <= 500; i++) {
            int result_3 = getRemainder(i, 3);
            int result_7 = getRemainder(i, 7);
            //Print number if it is divisible by 3 or 7
            if (result_3 == 0 || result_7 == 0) {
                System.out.println(STR."\{i} is divisible by 3 or 7.");
            }
        }
    }

    public static void while3And7 (){
        int i = -500;
        while (i <= 500) {
            int result_3 = getRemainder(i, 3);
            int result_7 = getRemainder(i, 7);
            //Print number if it is divisible by 3 or 7
            if (result_3 == 0 || result_7 == 0)
                System.out.println(STR."\{i} is divisible by 3 or 7.");
            i++;
        }
    }

    public static void count3And7 () {
        int count = 0;
        for (int i = -500; i <= 500; i++) {
            int result_3 = getRemainder(i, 3);
            int result_7 = getRemainder(i, 7);
            //Increment count if number i is divisible by 3 or 7
            if (result_3 == 0 || result_7 == 0) {
                count++;
            }
        }
        System.out.println(STR."Count of numbers between -500 and 500 that are divisible by 3 or 7 is: \{count}");
    }

    public static int getRemainder(int numerator, int denominator){
        // 11 / 10 ===> 1 1/10

        int rem = numerator % denominator;
        return rem;
    }
}
