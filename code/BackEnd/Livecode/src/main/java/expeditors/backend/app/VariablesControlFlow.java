package expeditors.backend.app;

public class VariablesControlFlow
{
	public static void main(String [] args) {
//		String name = "Joey";
//		System.out.println(STR."Hello \{name}");

		types();

//		int result = product(10, 2);
//		System.out.println(STR."result: \{result}");
	}

	// n bits = 2**n-1 - 1;
	public static void types() {

		boolean bool = true; // false

		//Integral types
		byte aByte = 127;          //1 byte
		int anInt =    127;            //4 bytes
		long aLong =    400000000;            //8 bytes


		//Floating point types
		float aFloat = 32.556F;               //4 bytes
		double aDouble = 22.4;                //8 bytes

		//char
		short aShort = 99;      //2 bytes
		char aChar = 'c';                    //2 bytes

		String str = "cabcde";

		System.out.println(STR."aShort: \{aShort}, aChar: \{aChar}, charAsNum: \{(short)aChar}");
	}


	public static int product(int x, int y) {
		int result = x * y;
		return result;
	}

	public static String controlFlow(Integer input) {
		String result;
		if(input < 0) {
			result = "negative";
			System.out.println("negative");
		}else if(input < 100) {
			result = "okay";
			System.out.println("okay");
		}else {
			result = "Too high";
			System.out.println("Too high");
		}

		String r2 = switch(input) {
			case Integer i when i < 0 -> "negative";
			case Integer i when i < 100 -> "okay";
			default -> "Too High";
		};


		return result;
	}

	public static void loops() {
		int limit = 10;

//		do {
//			System.out.println(STR."count: \{count}");
//			count++;
//		}while(count < 10);

		int count = 0;
		while(count < limit) {
			System.out.println(STR."count: \{count}");
			count++;
		}


		for(int i = 0; i < limit; i++) {
			System.out.println(STR."i: \{i}");
		}

		int j = 200;

		for(; j < limit; j++) {
			System.out.println(STR."i: \{j}");

		}
	}

	public static void fun() {
		loopControl(1000000, 2);
	}

	public static boolean loopControl(int limit, int found) {

		boolean result = false;
		for(int i = 0; i < limit; i++) {
			if(limit == found) {
				continue;   //Got to next iteration
			}
			//Do Work with limit
		}

		boolean result2 = false;
		for(int i = 0; i < limit; i++) {
			if(limit == found) {
				result2 = true;
				break;       // break out of loop
			}
		}

		int j = 0;
		while(j > limit) {
			if(limit == found) {
				break;
			}
		}


		System.out.println(STR."result: \{result}");
		//Use result down here

		return result;
	}

	public void foo() {
		//To get remainders

		int i = 10;

		int j = 11;

		int remainder = 10 % 11;       // 10 / 11   ==> 0 and 10/11
	}
}
