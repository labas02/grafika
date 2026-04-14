package cz.uhk.fim.model;

import java.awt.*;

public abstract class GraphObject {
    private Point coord;
    protected Color color;
    protected boolean filled;
    protected int object_type;
/*
* object_type
* 1 = circle
* 2 = rectangle
* 3 = custom_object
* 4 = triangle
* */
    
    
    public GraphObject() {
    }

    public GraphObject(Point coord, Color color, boolean filled,int object_type) {
        this.setCoord(coord);
        this.color = color;
        this.filled = filled;
        this.object_type = object_type;
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

    public int get_type(){return object_type;}

    public void set_type(int object_type){ this.object_type = object_type;}

    public abstract boolean over(int x, int y);
    public abstract void draw(Graphics g);

    public void move(int x, int y) {
        getCoord().setLocation(x,y);
    }

    public String get_type_string() {
        switch (object_type){
            case 1:
                return "kruh";
            case 2:
                return "ctverec";
            case 4:
                return "custom";
        }
        return null;
    }
}
