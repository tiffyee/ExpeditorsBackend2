package ttl.larku.shape.app;

import java.util.ArrayList;
import java.util.List;
import ttl.larku.shape.domain.AbstractShape;
import ttl.larku.shape.domain.Circle;
import ttl.larku.shape.domain.Rectangle;

public class StaticDemo {

   public static void main(String[] args) {
      int countBeforeStart = AbstractShape.getCount();
      System.out.println("Count at start: " + AbstractShape.getCount());

      Circle circle = new Circle(10, 10, 10, "Red", true, 1);
      Rectangle rectangle = new Rectangle(10, 10, 35, 35);
      Rectangle rectangle2 = new Rectangle(10, 10, 35, 35);

      List<AbstractShape> shapes = new ArrayList<>();
      shapes.add(circle);
      shapes.add(rectangle);
      shapes.add(rectangle2);

      System.out.println("Count at end: " + AbstractShape.getCount());
      for (AbstractShape s : shapes) {
         System.out.println("s: " + s);
      }

   }

   public void listFactories() {
      List<String> ls = List.of("one", "two", "three");

      ls.add("10");
   }

}
