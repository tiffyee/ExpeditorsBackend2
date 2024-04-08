package ttl.larku.shape.app;

import java.util.ArrayList;
import java.util.List;
import ttl.larku.shape.domain.Circle;
import ttl.larku.shape.domain.MyFunkyShape;
import ttl.larku.shape.domain.Rectangle;
import ttl.larku.shape.domain.Shape;

public class InheritanceShapeApp {

   public static void main(String[] args) {
      Circle circle = new Circle(10, 10, 10, "Red", true, 1);

      Shape shape = circle;

      Object obj = circle;

      System.out.println(STR."lineThickness: \{circle.getLineThickness()}");

      Rectangle rectangle = new Rectangle(10, 10, 35, 35);


      System.out.println("rect color: " + rectangle.getColor());

      List<Shape> shapes = new ArrayList<>();
      shapes.add(circle);
      shapes.add(rectangle);

      MyFunkyShape mfs = new MyFunkyShape();
      shapes.add(mfs);

      for(Shape s : shapes) {

//         if(s instanceof Circle c){
//            System.out.println(c.getRadius());
//         }
//
//         s.draw();
         System.out.println("s: " + s.getLineThicknessSquared());
      }

   }

}
