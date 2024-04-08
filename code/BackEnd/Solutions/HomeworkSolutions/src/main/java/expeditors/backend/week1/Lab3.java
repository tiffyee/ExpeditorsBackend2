package expeditors.backend.week1;

/**
 * @author whynot
 */
public class Lab3 {
    /*
    Change your program to print out a count
    of the numbers between -500 to +500 that are divisible
    by either 3 or 7.
     */
    public static void main(String[] args) {
        withForLoop();
//        withWhileLoop();
    }

    public static int withForLoop() {
        int count = 0;
        for (int i = -500; i <= 500; i++) {
            if (i % 3 == 0 && i % 7 == 0) {
                count++;
            }
//            if (i % 7 == 0) {
//                count++;
//            }
        }

        System.out.println("Count of Numbers between -500 and +500 which are divisible by 3 and/or 7: " + count);
        return count;
    }

    public static int withWhileLoop() {
        int count = 0;
        int i = -500;
        while (i <= 500) {
            if (i % 3 == 0) {
                count++;
            }
            if (i % 7 == 0) {
                count++;
            }

            //IMPORTANT to make sure you remember to change
            // the condition in a while loop
            i++;
        }
        System.out.println("Count of Numbers between -500 and +500 which are divisible by 3 and/or 7: " + count);
        return count;
    }
}
