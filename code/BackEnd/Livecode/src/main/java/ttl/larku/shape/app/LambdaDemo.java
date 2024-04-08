package ttl.larku.shape.app;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import ttl.larku.shape.domain.Circle;
import ttl.larku.shape.domain.Rectangle;
import ttl.larku.shape.domain.Shape;

public class LambdaDemo {

   public static void main(String[] args) {
//      lambda1();
//      lambda2();
      lambda3();
   }

   public static void lambda2() {
      Circle circle = new Circle(10, 10, 10, "Red", true, 10);
      Rectangle rectangle = new Rectangle(10, 10, 35, 35);

      List<Shape> shapes = new ArrayList<>();
      shapes.add(circle);
      shapes.add(rectangle);

      List<Shape> result = new ArrayList<>();
      List<Integer> lints  = new ArrayList<>();
      for (Shape shape : shapes) {
         if (shape.getLineThickness() > 5) {
            result.add(shape);
            lints.add(shape.getLineThickness() * 2);
         }
      }

      //boolean test(T t)

      List<Shape> result2 = shapes.stream()
            .filter(s -> s.getLineThickness() > 5)
            .collect(Collectors.toList());

      List<Integer> result3 = shapes.stream()
            .filter(s -> s.getLineThickness() > 5)
            .map(s -> {
               lints.add(s.getLineThickness()*2);
               return s.getLineThickness();
            })
            .collect(Collectors.toList());

      shapes.stream()
            .filter(s -> s.getLineThickness() > 5)
            .forEach(System.out::println);

      System.out.println("Fat shapes: " + result);
   }


   public static void lambda1() {
      Circle circle = new Circle(10, 10, 10, "Red", true, 10);
      Rectangle rectangle = new Rectangle(10, 10, 35, 35);

      List<Shape> shapes = new ArrayList<>();
      shapes.add(circle);
      shapes.add(rectangle);

      MyConsumer mc = new MyConsumer();

      Consumer<Shape> anonymous = new Consumer<>() {
         @Override
         public void accept(Shape shape) {
            System.out.println(shape);
         }
      };

      Consumer<Shape> lambda1 = (Shape shape) -> {
         System.out.println(shape);
      };

      Consumer<Shape> lambda2 = (shape) -> {
         System.out.println(shape);
      };

      Consumer<Shape> lambda3 = (shape) -> System.out.println(shape);

      Consumer<Shape> lambda4 = shape -> {
         System.out.println(shape);
      };

      shapes.forEach(lambda4);

      shapes.forEach(shape -> System.out.println(shape));
   }


   //Function R apply(T t)

   //UnaryOperator T apply(T t)

   public static void lambda3() {
      Function<String, Integer> fstrToInt = str -> str.length();

      UnaryOperator<String> uOp = str -> str + str;

      int i = fstrToInt.apply("abc");
      System.out.println("i: " + i);

      String uOpresult = uOp.apply("abc");
      System.out.println("i: " + uOpresult);
   }

}

class MyConsumer implements Consumer<Shape> {
   @Override
   public void accept(Shape shape) {
      System.out.println(shape);
   }
}
