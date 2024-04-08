package org.ttl.javafundas.recordshape.shape.interfaces;

import java.awt.*;

/**
 * @author whynot
 */
public sealed interface Shape permits AbstractShape {
    int getPosX();

    int getPosY();

    Color getColor();

    int getLineThickness();

    double getArea();

    double getPerimeter();

    void draw(Graphics2D g);

    default boolean isAboveOriginX() {
        return getPosX() > 0;
    }
}
