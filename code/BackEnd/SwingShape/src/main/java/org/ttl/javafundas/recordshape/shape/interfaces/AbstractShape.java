package org.ttl.javafundas.recordshape.shape.interfaces;

import java.awt.*;

/**
 * @author whynot
 */
abstract public sealed class AbstractShape implements Shape permits Circle, Rectangle, Triangle{

    protected Color color;
    protected int lineThickness;
    protected int posX, posY;

    private static int instancesCreated;

    protected RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                     RenderingHints.VALUE_ANTIALIAS_ON);

    public AbstractShape(int posX, int posY, Color color, int lineThickness) {
        this.posX = posX;
        this.posY = posY;

        this.color = color;
        this.lineThickness = lineThickness;

        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        instancesCreated++;

    }

    public AbstractShape(Color color, int lineThickness) {
        this(-1, -1, color, lineThickness);
    }

    protected AbstractShape() {}

    public static int getInstancesCreated() {
        return instancesCreated;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getLineThickness() {
        return lineThickness;
    }

    @Override
    public void draw(Graphics2D g2d) {
//        g2d.setRenderingHints(renderingHints);
    }

}