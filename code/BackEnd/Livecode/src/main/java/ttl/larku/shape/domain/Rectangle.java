package ttl.larku.shape.domain;

public class Rectangle extends AbstractShape{

   private int width, height;

   public Rectangle(int width, int height, int x1, int y1) {
      this(width, height, x1, y1, "Black", true, 1);
   }

   public Rectangle(int width, int height, int x1, int y1, String color, boolean isDraggable, int lineThickness) {
      super(x1, y1, color, isDraggable, lineThickness);

      this.width = width;
      this.height = height;
   }

   public int getWidth() {
      return width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return height;
   }

   public void setHeight(int height) {
      this.height = height;
   }


   //circumference - perimeter
   public double getPerimeter() {
      return 2 * width + 2 * height;
   }

   //area
   public double getArea() {
      return width * height;
   }

   @Override
   public void draw() {
      System.out.println(STR."Rectangle::draw "); //with \{x1}, \{y1}, w, h: \{width}, \{height}");
   }

   @Override
   public String toString() {
      return "Rectangle{" +
            "width=" + width +
            ", height=" + height +
            " " + super.toString() +
            '}';
   }
}
