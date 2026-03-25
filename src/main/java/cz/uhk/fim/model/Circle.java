package cz.uhk.fim.model;

import java.awt.*;

public class Circle extends GraphObject{
    int radius;

    public Circle(int sx, int sy, Color barva, boolean filled, int radius) {
        super(new Point(sx, sy), barva, filled);
        this.radius = radius;
    }

    public Circle(Point coord, Color barva, boolean filled, int radius) {
        super(coord, barva, filled);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public Point getCoord() {
        return coord;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        if (filled)
            g.fillOval(coord.x - radius, coord.y- radius, 2* radius, 2* radius);
        else
            g.drawOval(coord.x- radius, coord.y- radius, 2* radius, 2* radius);

    }

    @Override
    public String toString() {
        return "circle (" + coord + ") " + color + " " + filled;
    }

    @Override
    public boolean over(int x, int y) {
        int sx = coord.x;
        int sy = coord.y;

        return ((x-sx)*(x-sx) + (y-sy)*(y-sy) <= radius*radius)
                && (filled || (x-sx)*(x-sx) + (y-sy)*(y-sy) >= (radius-2)*(radius-2));
    }
}
