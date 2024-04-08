package ttl.backend.basic;

/**
 * A demo of Types in Java
 */
public class Variables {

    public static void main(String[] args) {
//        variableTypes();
        //javaControlFlow(10);
        javaControlFlow2(10);
    }

    public static void variableTypes() {

        boolean aBool = true; //false         //1 byte, 8 bits

        //integral types
        byte aByte = 127;                         //1 byte, 8 bits
        int anInt = 127;                   //4 bytes, 32 bits
        long aLong = 383838883;                   //8 bytes, 64 bits

        //floating point types
//        float aFloat = (float)34.56;                    //4 bytes, 32 bits
        float aFloat = 34.56F;                    //4 bytes, 32 bits

        double aDouble = 34.56;                   //8 bytes 64 bits.

        short aShort = 99;                     //2 bytes, 16 bit
        //Character
        char aChar = 'c';                         //2 bytes, 16 bits.

        //System.out.println("aShort: " + aShort + ", aChar: " + aChar + ", Char as num: " + (short)aChar);
        System.out.println(STR."aShort: \{aShort}, aChar: \{aChar}, Char as num: \{(short) aChar}");

        System.out.println(STR."boo: \{boo()}");

    }

    public static void javaControlFlow(int limit) {

        if (limit < 10) {
            System.out.println("small");
        } else if (limit < 100) {
            System.out.println("medium");
        } else {
            System.out.println("Large");
        }

    }

    public static void javaControlFlow2(Integer limit) {

        String length;
        if (limit == 10) {
            length = "small";
        } else if (limit == 100) {
            length = "medium";
        } else {
            length = "large";
        }


        switch (limit) {
            case 10:
                length = "small";
                break;
            case 100:
                length = "medium";
                break;
            default:
                length = "large";
        }

        System.out.println(STR."Length in middle is \{length}");

        String length2 = switch (limit) {
            case Integer i when i < 10 -> "small";
            case Integer i when i < 100 -> "medium";
            default -> "large";
        };
        //Use the length for something
    }



    public static String boo() {
        return "boo";
    }
}
