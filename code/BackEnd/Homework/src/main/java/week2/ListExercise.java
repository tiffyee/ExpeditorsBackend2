package week2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListExercise {

    public static List<Integer> createList(int size, int limit){
        List<Integer> listInt = new ArrayList<>();
        for (int i = 0; i < size; i++){
            listInt.add(ThreadLocalRandom.current().nextInt(limit + 1));
        }
        return listInt;
    }
}
