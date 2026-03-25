package cz.uhk.fim.model;

import java.awt.*;

public abstract class GraphObject {
    protected Point coord;
    protected Color color;
    protected boolean filled;

    public GraphObject() {
    }

    public GraphObject(Point coord, Color color, boolean filled) {
        this.coord = coord;
        this.color = color;
        this.filled = filled;
    }

    public Color getColor() {
        return color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public Point getCoord() {
        return coord;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }

    public abstract boolean over(int x, int y);
    public abstract void draw(Graphics g);

    public void move(int x, int y) {
        coord.setLocation(x,y);
    }
}
