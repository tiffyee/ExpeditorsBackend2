package org.ttl.javafundas.recordshape.shape.noinheritance;

/**
 * @author whynot
 */
public record Circle(int posX, int posY, int radius, String color, int lineThickness) {

    private static int instancesCreated;

    public Circle {
        instancesCreated++;
    }

    public Circle(int posX, int posY, int radius) {
        this(posX, posY, radius, "Black", 2);
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public void draw() {
        System.out.println("Drawing Circle with posX,Y: " + posX() + ", " + posY());
    }
}