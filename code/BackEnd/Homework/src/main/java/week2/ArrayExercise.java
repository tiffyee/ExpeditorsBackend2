package week2;

import java.util.concurrent.ThreadLocalRandom;

public class ArrayExercise {
    public static void main(String[] args) {
        int[] arr = createArray(10,10);
    }

    public static int[] createArraySquares (){
        int[] arraySquares = new int[10];

        for (int i = 1; i <= 10; i++){
            arraySquares[i-1] = i * i;
        }

        return arraySquares;
    }

    public static int[] createArray (int size, int limit){
        int[] arrInt = new int[size];

        for (int i = 0; i < arrInt.length; i++){
            arrInt[i] = ThreadLocalRandom.current().nextInt(limit + 1);
        }
        return arrInt;
    }
}
