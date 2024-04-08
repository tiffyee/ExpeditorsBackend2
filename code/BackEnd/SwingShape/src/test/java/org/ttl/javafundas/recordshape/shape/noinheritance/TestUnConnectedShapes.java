package org.ttl.javafundas.recordshape.shape.noinheritance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author whynot
 */
public class TestUnConnectedShapes {

    @Test
    public void testCircleConstructor() {
        int testRadius = 5;
        Circle c = new Circle(10, 20, testRadius);

        assertEquals("Black", c.color());
        assertEquals(5, c.radius());

        assertEquals(2 * Math.PI * testRadius, c.getPerimeter());
    }

    @Test
    public void testRectangleConstructorSmallest() {
        int testSideLength = 10;

        Rectangle r = new Rectangle(10, 20, testSideLength);

        assertEquals(testSideLength, r.width());
        assertEquals(2, r.lineThickness());
        assertTrue(r.isSquare());
    }

    @Test
    public void testBothShapes() {
        int testRadius = 5;
        Circle c = new Circle(10, 20, testRadius);

        int testSideLength = 10;
        Rectangle r = new Rectangle(40, 55, testSideLength);

        c.draw();
        r.draw();
    }
}
