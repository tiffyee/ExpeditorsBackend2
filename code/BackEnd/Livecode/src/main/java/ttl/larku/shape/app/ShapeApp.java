package ttl.larku.shape.app;

import java.util.ArrayList;
import java.util.List;
import ttl.larku.shape.domain.Circle;
import ttl.larku.shape.domain.Rectangle;

public class ShapeApp {

   public static void main(String[] args) {

      List<Circle> circles = new ArrayList<>();
      Circle circle = new Circle(10, 10, 10, "Red", true, 1);
      Circle circle2 = new Circle(20, 30, 3);

      circles.add(circle);
      circles.add(circle2);

      for(Circle c : circles) {
         System.out.println(STR."area: \{c.getArea()}");
         c.draw();
      }

      List<Rectangle> rects = new ArrayList<>();
      Rectangle r1 = new Rectangle(10, 10, 10, 10);
      Rectangle r2 = new Rectangle(100, 10, 5, 10);

      rects.add(r1);
      rects.add(r2);

      for(Rectangle c : rects) {
         System.out.println(STR."area: \{c.getArea()}");
         c.draw();
      }
   }

}
