package ttl.larku.shape.domain;

public class MyFunkyShape implements Shape{
   @Override
   public int getX1() {
      return 0;
   }

   @Override
   public void setX1(int x1) {

   }

   @Override
   public int getY1() {
      return 0;
   }

   @Override
   public void setY1(int y1) {

   }

   @Override
   public String getColor() {
      return null;
   }

   @Override
   public void setColor(String color) {

   }

   @Override
   public boolean isDraggable() {
      return false;
   }

   @Override
   public void setDraggable(boolean draggable) {

   }

   @Override
   public int getLineThickness() {
      return 0;
   }

   @Override
   public void setLineThickness(int lineThickness) {

   }

   @Override
   public void draw() {
      System.out.println("MyFunkyShape::draw");
   }
}
