package cz.uhk.fim.model;

import java.awt.*;

public class Circle extends GraphObject{
    int radius;

    public Circle(int sx, int sy, Color barva, boolean filled, int radius) {
        super(new Point(sx, sy), barva, filled,1);
        this.radius = radius;
    }

    public Circle(Point coord, Color barva, boolean filled, int radius) {
        super(coord, barva, filled,1);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public Point getCoord() {
        return super.getCoord();
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setCoord(Point coord) {
        super.setCoord(coord);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        if (filled)
            g.fillOval(super.getCoord().x - radius, super.getCoord().y- radius, 2* radius, 2* radius);
        else
            g.drawOval(super.getCoord().x- radius, super.getCoord().y- radius, 2* radius, 2* radius);

    }

    @Override
    public String toString() {
        return "circle (" + super.getCoord() + ") " + color + " " + filled;
    }

    @Override
    public boolean over(int x, int y) {
        int sx = super.getCoord().x;
        int sy = super.getCoord().y;

        return ((x-sx)*(x-sx) + (y-sy)*(y-sy) <= radius*radius)
                && (filled || (x-sx)*(x-sx) + (y-sy)*(y-sy) >= (radius-2)*(radius-2));
    }
}
