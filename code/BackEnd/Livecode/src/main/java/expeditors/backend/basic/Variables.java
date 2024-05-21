package expeditors.backend.basic;

/**
 * A demo of Types in Java
 */
public class Variables {

    public static void main(String[] args) {
        variableTypes();
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




    public static String boo() {
        return "boo";
    }
}
