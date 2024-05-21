package expeditors.backend.basic;

public class JavaFlowControl {


   public static void main(String[] args) {
      //javaControlFlow(10);
      javaControlFlow2(10);
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
}
