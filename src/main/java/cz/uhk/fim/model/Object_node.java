package cz.uhk.fim.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.awt.*;

public class Object_node extends GraphObject {

        protected int width;
        protected int height;
        protected boolean is_selected;
        protected Color selected_color = Color.ORANGE;
        @JsonIgnore
        private Object_node previous_node = null;
        @JsonIgnore
        private Object_node next_node = null;

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

    public Object_node getNext_node() {
        return next_node;
    }

    public void setPrevious_node(Object_node previous_node) {
        this.previous_node = previous_node;
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

    public void setNeighboring_node_null(Object_node o){
            if (next_node == o){
                next_node = null;
            } else if (previous_node == o) {
                previous_node = null;
            }
    }
    public void set_neighboring_node(Object_node o){
            if (has_empty_neighbor()){
                if (next_node==null){
                    next_node = o;
                }else {
                    previous_node = o;
                }
            }
    }

    public Object_node(int sx, int sy, Color color, boolean filled, int width, int height) {
            this(new Point(sx,sy), color, filled, width, height);
        }

        public Object_node(Point coord, Color barva, boolean filled, int width, int height) {
            super(coord, barva, filled,2);
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
                g.drawLine(next_node.getCoord().x, next_node.getCoord().y, getCoord().x, getCoord().y);
            }
            if (previous_node != null){
                g.drawLine(previous_node.getCoord().x, previous_node.getCoord().y, getCoord().x, getCoord().y);
            }
            if (filled)
                g2.fillRect(getCoord().x- width /2, getCoord().y- height /2, width, height);
            else
                g2.drawRect(getCoord().x- width /2, getCoord().y- height /2, width, height);
//        g2.rotate(-0.12);
        }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Object_node { ");
        sb.append("coord=").append(getCoord());
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", color=").append(color);
        sb.append(", filled=").append(filled);
        sb.append(", selected=").append(is_selected);

        // Avoid infinite recursion by only printing neighbor coordinates
        sb.append(", previous_node=");
        if (previous_node != null) {
            sb.append(previous_node.getCoord());
        } else {
            sb.append("null");
        }

        sb.append(", next_node=");
        if (next_node != null) {
            sb.append(next_node.getCoord());
        } else {
            sb.append("null");
        }

        sb.append(" }");

        return sb.toString();
    }

        @Override
        public boolean over(int x, int y) {
            int sx = getCoord().x;
            int sy = getCoord().y;
            return sx - width / 2 <= x && x <= sx + width / 2 && sy - height / 2 <= y && y <= sy + height / 2;
        }


        public void flip_neighbors(){
            Object_node tmp = next_node;
            next_node = previous_node;
            previous_node = tmp;
        }

    public boolean has_empty_neighbor(){
        return next_node == null || previous_node==null;
    }


}
