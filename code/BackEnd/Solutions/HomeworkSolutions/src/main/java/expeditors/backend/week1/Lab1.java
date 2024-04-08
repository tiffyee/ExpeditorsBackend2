package expeditors.backend.week1;

import java.awt.*;
import java.math.BigInteger;

/**
 * @author whynot
 */
public class Lab1 {

    public static void main(String[] args) {
        /*
         */

        //a. A vehicle identification number in the range 1000000 - 999999
        //Can't use a short here because it is too small.
        //And a long would be 4 wasted bytes.
        int vin = 1000000;
        /*********************************************************************/
        //b. A vehicle make /model (i.e. Ford Explorer)
        String make = "Honda Accord";

        /*********************************************************************/
        //c. a vehicle color
        //Could use a String.
        String color = "RED";

        //In some circumstances, could also use a special class, e.g. the
        //java.awt.Color class
        Color red = Color.RED;
        /*********************************************************************/
        //d. whether the vehicle has a towing package
        boolean hasTowingPackage = true;  //or false
        /*********************************************************************/
        //e. an odometer reading
        float numMiles = 22000.4F;

        /*********************************************************************/
        //f. a price
        //could use a double here
        double price = 33.5;
        //But generally you do NOT want to use a double for
        //anything where you need to be precise.  Money is
        //definitely one of those things.  Instead, you would
        //use the library class BigInteger which is meant for
        //arbitrary precision arithmetic.
        BigInteger betterPrice = new BigInteger("33.5");
        /*********************************************************************/

        //g. a quality rating (A, B, or C)
        //Again, we could use a String
        String badQualityRating = "A"; //or "B", or "C"

        //But we will later look at Enums, which are a much
        //better way of declaring a variable which can take
        //one of a fixed number of values

        Quality muchBetterQuality = Quality.A;
    }

    enum Quality {
        A,
        B,
        C
    }
}
