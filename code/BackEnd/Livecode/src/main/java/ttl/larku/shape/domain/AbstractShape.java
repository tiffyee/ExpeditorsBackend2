package ttl.larku.shape.domain;

abstract public class AbstractShape implements Shape{
   private String color;
   private boolean isDraggable;
   private int lineThickness;
   private int x1, y1;

   private static int count;

   public AbstractShape(int x1, int y1, String color, boolean isDraggable, int lineThickness) {
      this.x1 = x1;
      this.y1 = y1;
      this.color = color;
      this.isDraggable = isDraggable;
      this.lineThickness = lineThickness;

      count++;
   }

   public static int getCount() {
     return count;
   }

   public int getX1() {
      return x1;
   }

   public void setX1(int x1) {
      this.x1 = x1;
   }

   public int getY1() {
      return y1;
   }

   public void setY1(int y1) {
      this.y1 = y1;
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public boolean isDraggable() {
      return isDraggable;
   }

   public void setDraggable(boolean draggable) {
      isDraggable = draggable;
   }

   public int getLineThickness() {
      return lineThickness;
   }

   public void setLineThickness(int lineThickness) {
      this.lineThickness = lineThickness;
   }

   @Override
   public String toString() {
      return "AbstractShape{" +
            "color='" + color + '\'' +
            ", isDraggable=" + isDraggable +
            ", lineThickness=" + lineThickness +
            ", x1=" + x1 +
            ", y1=" + y1 +
            '}';
   }
}
