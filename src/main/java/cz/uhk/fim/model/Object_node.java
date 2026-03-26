package cz.uhk.fim.model;

import java.awt.*;
import java.util.Vector;

public class Object_node extends GraphObject {

        protected int width;
        protected int height;
        protected Point pos;
        protected boolean is_selected;
        protected Color selected_color = Color.ORANGE;
        protected Object_node previous_node = null;
        protected Object_node next_node = null;

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

    public Object_node getPrevious_node() {
        return previous_node;
    }

    public void setPrevious_node(Object_node previous_node) {
        this.previous_node = previous_node;
    }

    public Object_node getNext_node() {
        return next_node;
    }

    public void setNext_node(Object_node next_node) {
        this.next_node = next_node;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }

    public Object_node(int sx, int sy, Color color, boolean filled, int width, int height) {
            this(new Point(sx,sy), color, filled, width, height);
        }

        public Object_node(Point coord, Color barva, boolean filled, int width, int height) {
            super(coord, barva, filled);
            this.width = width;
            this.height = height;
        }

        @Override
        public void draw(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
//        g2.rotate(0.12);
            if (is_selected){
                g2.setColor(selected_color);
            }else {
                g2.setColor(color);
            }
            if (next_node != null){
                g.drawLine(next_node.coord.x,next_node.coord.y,coord.x,coord.y);
            }
            if (previous_node != null){
                g.drawLine(previous_node.coord.x,previous_node.coord.y,coord.x,coord.y);
            }
            if (filled)
                g2.fillRect(coord.x- width /2, coord.y- height /2, width, height);
            else
                g2.drawRect(coord.x- width /2, coord.y- height /2, width, height);
//        g2.rotate(-0.12);
        }

        @Override
        public String toString() {
            return "rectangle (" + coord + ") " + color + " " + filled;
        }

        @Override
        public boolean over(int x, int y) {
            int sx = coord.x;
            int sy = coord.y;
            return sx - width / 2 <= x && x <= sx + width / 2 && sy - height / 2 <= y && y <= sy + height / 2;
        }


}
