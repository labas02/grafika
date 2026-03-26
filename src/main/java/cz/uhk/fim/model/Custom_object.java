package cz.uhk.fim.model;

import java.awt.*;

public class Custom_object extends GraphObject {
    Object_node[] nodes;
    Point pos;
    Color color;


    public Custom_object(Object_node[] nodes, Point pos, Color color) {
        this.nodes = nodes;
        this.pos = find_center();
        this.color = color;
    }

    public Custom_object(Point coord, Color color, boolean filled, Object_node[] nodes, Point pos, Color color1) {
        super(coord, color, filled);
        this.nodes = nodes;
        this.pos = find_center();
        this.color = color1;
    }

    @Override
    public boolean over(int x, int y) {
        return false;
    }

    @Override
    public void draw(Graphics g) {
            g.setColor(color);
            int [] vx = new int[nodes.length];
            int [] vy = new int[nodes.length];
            for (int i = 0; i < nodes.length; i++) {
                vx[i] = nodes[i].pos.x;
                vy[i] = nodes[i].pos.y;
            }

            if (filled) {
                g.fillPolygon(vx, vy, nodes.length);
            } else{
            g.drawPolygon(vx, vy, nodes.length);
            }
        }

        public Point find_center(){
            Point pos = new Point();
            int x = 0;
            int y = 0;
            for (int i = 0; i < nodes.length; i++) {
                 x += nodes[i].pos.x;
                 y += nodes[i].pos.y;
            }
            return new Point(x/nodes.length,y/nodes.length);
        }
    }
