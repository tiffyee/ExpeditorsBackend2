package expeditors.backend.week2;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;

public class Lab2 {

    /**
     * Write a method called createArray that returns an array with
     * the first names of all the students in the class.  If two
     * students have the same name, use the first letter of
     * their other names to differentiate them.
     */

    private static String[] cohort1Names = {
            "Alan Aguillon Juarez",
            "Antonio Nazco",
            "Antony Alfaro",
            "Arjun Panikar",
            "Carla Cairns",
            "Edwin Soto",
            "Jesus Cortez Valdez",
            "Juan De Dios Hernandez",
            "Julio Cesar Rodriguez",
            "Komal Patel",
            "Lokesh Gopi",
            "Lucas Maesaka",
            "Mainor Lobo",
            "Marcus Silva",
            "Raul Gomez",
            "Rohit Aherwadkar",
            "Tetyana Alvarado"
    };

    private static String[] cohort2Names = {
            "Alan Morales Rueda",
            "Andre Uys",
            "Audomaro Gonzalez",
            "Caio Henrique",
            "Chris Valencia",
            "Daniel Lee",
            "Humberto Rojas",
            "Javier Mendoza",
            "Joao Alonso",
            "Luis Barraza Hernandez",
            "Mariana Duarte",
            "Miguel Angel Rodriguez",
            "Rosendo Galindo",
            "Sean Jaw",
            "Tiffany Yee",
            "Vincent Vu",
            "Nathaniel Schieber",
            "Dylan McClain",
            "Grant Stampfli"
    };

    public static String getRandomStudent() {
        String[] arr = cohort1Names;
        int random = ThreadLocalRandom.current().nextInt(arr.length);

        return arr[random];
    }


    private final static int TICK_SLEEP_TIME = 500;

    public static void main(String[] args) {
//        app1();
        appWithSuspense();
    }

    public static void appWithSuspense() {
        out.print("The student is: ");
        for (int t = 0; t < 10; t++) {
            out.print(".");
            sleep(TICK_SLEEP_TIME);
        }
        String name = getRandomStudent();
        out.println(name);
    }

    public static void app1() {
        for (int i = 0; i < 10; i++) {
            String name = getRandomStudent();
            out.print("The student is: " + name);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
