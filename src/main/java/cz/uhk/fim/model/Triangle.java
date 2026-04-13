package cz.uhk.fim.model;

import java.awt.*;

public class Triangle extends GraphObject{
    Point [] vert;

    private static Point getCenter(Point[] pts)
    {
        int x = 0;
        int y = 0;
        for (int i = 0; i < 3; i++) {
            x += pts[i].x;
            y += pts[i].y;
        }

        return new Point(x/3,y/3);
    }

    public Triangle(Point[] vert, Color barva, boolean vypln) {
        super(getCenter(vert),barva,vypln,4);
        this.vert = vert;
    }

    public Point[] getVert() {
        return vert;
    }

    public Color getBarva() {
        return color;
    }

    public boolean isFilled() {
        return filled;
    }

    public Point getCoord() {
        return super.getCoord();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int [] vx = new int[3];
        int [] vy = new int[3];
        for (int i = 0; i < 3; i++) {
            vx[i] = vert[i].x;
            vy[i] = vert[i].y;
        }

        if (filled)
            g.fillPolygon(vx, vy, 3) ;
        else
            g.drawPolygon(vx, vy, 3) ;
    }

    @Override
    public String toString() {
        return "triangle (" + super.getCoord() + ") " + color + " " + filled;

    }

    @Override
    public boolean over(int x, int y) {
        return false;
    }
}
