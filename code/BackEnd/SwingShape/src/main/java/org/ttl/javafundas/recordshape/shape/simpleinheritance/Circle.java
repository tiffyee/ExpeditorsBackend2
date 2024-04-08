package org.ttl.javafundas.recordshape.shape.simpleinheritance;

/**
 * @author whynot
 */
public class Circle extends Shape {

    private int radius;

    public Circle(int posX, int posY, int radius, String color, int lineThickness) {
        super(posX, posY, color, lineThickness);

        this.radius = radius;

    }

    public Circle(int posX, int posY, int radius) {
        this(posX, posY, radius, "Black", 2);
    }

    public int getRadius() {
        return radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public void draw() {
        System.out.println("Drawing Circle with posX,Y: " + getPosX() + ", " + getPosY());
    }
}