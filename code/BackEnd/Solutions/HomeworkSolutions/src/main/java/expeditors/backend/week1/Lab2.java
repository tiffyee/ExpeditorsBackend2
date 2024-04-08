package expeditors.backend.week1;

/**
 * @author whynot
 */
public class Lab2 {

    /*
    Write a program that goes in a loop from -500 to +500
    and prints out all numbers that are divisible by either 3 or 7.
    Do this with a for loop and a while loop.
     */
    public static void main(String[] args) {
        withForLoop();
//        withWhileLoop();
    }

    public static void withForLoop() {
        for (int i = -500; i <= 500; i++) {
            if (i % 3 == 0 && i % 7 == 0) {
                System.out.println(i);
            }
//            if (i % 7 == 0) {
//                System.out.println(i);
//            }
        }
    }

    public static void withWhileLoop() {
        int i = -500;
        while (i <= 500) {
            if (i % 3 == 0) {
                System.out.println(i);
            }
            if (i % 7 == 0) {
                System.out.println(i);
            }

            //IMPORTANT to make sure you remember to change
            // the condition in a while loop
            i++;
        }
    }
}
