package ttl.larku.shape.domain;

//Allow a user to draw a Circle
//Allow a user to draw a Rectangle

public class Circle extends AbstractShape {

   private int radius;


   public Circle(int radius, int x1, int y1) {
      this(radius, x1, y1, "Black", true, 1);
   }

   public Circle(int radius, int x1, int y1, String color, boolean isDraggable, int lineThickness) {
      super(x1, y1, color, isDraggable, lineThickness);

      this.radius = radius;
   }

   public int getRadius() {
      return radius;
   }

   public void setRadius(int radius) {
      this.radius = radius;
   }


   //circumference - perimeter
   public double getPerimeter() {
      return 2 * Math.PI * radius;
   }
   //diameter

   public double getDiameter() {
      return radius * 2;
   }

   //area
   public double getArea() {
      return Math.PI * radius * radius;
   }

   @Override
   public void draw() {
      System.out.println(STR."Circle::draw "); //with \{x1}, \{y1}, radius: \{radius}");
   }

   @Override
   public String toString() {
      var sstr = super.toString();
      return "Circle{" +
            "radius=" + radius +
            " " + sstr +
            '}';
   }
}
