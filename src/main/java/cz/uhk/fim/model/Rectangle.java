package cz.uhk.fim.model;

import java.awt.*;

public class Rectangle extends GraphObject {
    protected int width;
    protected int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }


    public Rectangle(Point coord, Color barva, boolean filled, int width, int height) {
        super(coord, barva, filled,2);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
//        g2.rotate(0.12);
        g2.setColor(color);
        if (filled)
            g2.fillRect(getCoord().x- width /2, getCoord().y- height /2, width, height);
        else
            g2.drawRect(getCoord().x- width /2, getCoord().y- height /2, width, height);
//        g2.rotate(-0.12);
    }

    @Override
    public String toString() {
        return "rectangle (" + getCoord() + ") " + color + " " + filled;
    }

    @Override
    public boolean over(int x, int y) {
        int sx = getCoord().x;
        int sy = getCoord().y;
        return sx-width/2 <= x && x <= sx+width/2 && sy-height/2 <= y && y <= sy+height/2;
    }
}
