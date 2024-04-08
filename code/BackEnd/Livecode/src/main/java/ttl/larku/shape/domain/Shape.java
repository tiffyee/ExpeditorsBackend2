package ttl.larku.shape.domain;

public interface Shape {
   public int getX1();

   public void setX1(int x1);

   public int getY1();

   public void setY1(int y1);

   public String getColor();

   public void setColor(String color);

   public boolean isDraggable() ;

   public void setDraggable(boolean draggable);

   public int getLineThickness();

   public void setLineThickness(int lineThickness);

   public void draw();

   public default int getLineThicknessSquared() {
      return getLineThickness() * getLineThickness();
   }

//   public default double getPerimeter() {
//
//   }
}
