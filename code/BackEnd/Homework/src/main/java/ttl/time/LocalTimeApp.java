package ttl.time;


import java.time.LocalTime;

public class LocalTimeApp {
    public static void main(String[] args) {

//        LocalTime localTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
//        System.out.println(STR."Here we go at: \{localTime}");

        //whileLoopsInJava(20);
        //forLoop(20);

        int result = RemainderService.getRemainder(10, 11);
        System.out.println(STR."result: \{result}");

    }

    public static void whileLoopsInJava(int limit) {
        int count = 0;
        while(count < limit) {
            LocalTime localTime = LocalTime.now(); //.truncatedTo(ChronoUnit.SECONDS);
            System.out.println(STR."Here we go at: \{localTime}");

            count++;    //count = count + 1
        }

        do {
            LocalTime localTime = LocalTime.now(); //.truncatedTo(ChronoUnit.SECONDS);
            System.out.println(STR."Here we go at: \{localTime}");

            count++;    //count = count + 1

        }while (count < limit);
    }

    public static void forLoop(int limit) {
//        int count = 0;
//        while(count < limit) {
//            LocalTime localTime = LocalTime.now(); //.truncatedTo(ChronoUnit.SECONDS);
//            System.out.println(STR."Here we go at: \{localTime}");
//
//            count++;    //count = count + 1
//        }

        for(int count2 = 0; count2 < limit; count2++) {
            LocalTime localTime = LocalTime.now(); //.truncatedTo(ChronoUnit.SECONDS);
            System.out.println(STR."Here we go at: \{localTime}");
        }
    }

}
