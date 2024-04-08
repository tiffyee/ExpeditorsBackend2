package org.ttl.javafundas.recordshape.shape.noinheritance;

/**
 * @author whynot
 */
public record Rectangle(int posX, int posY,
                        int width, int height,
                        String color, int lineThickness) {

    private static int instancesCreated;

    public Rectangle(int posX, int posY, int width, int height) {
        this(posX, posY, width, height, "Black", 2);
    }

    public Rectangle(int posX, int posY, int sideLength) {
        this(posX, posY, sideLength, sideLength, "Black", 2);
    }

    public Rectangle {
        instancesCreated++;
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * width + 2 * height;
    }

    public boolean isSquare() {
        return width == height;
    }

    public void draw() {
        System.out.println("Drawing Rectangle with posX,Y: " + posX + ", " + posY);
    }
}
