package cz.uhk.fim.model;

import java.awt.*;
import java.util.ArrayList;

import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class Custom_object extends GraphObject {
    protected ArrayList<Object_node> nodes;
    Color color;
    int[] vx;
    int[] vy;

    public Custom_object(ArrayList<Object_node> nodes, Point pos, Color color) {
        this.nodes = nodes;
        this.color = color;
        filled = true;
        object_type = 3;
        find_center();
    }


    @Override
    public boolean over(int x, int y) {
         vx = new int[nodes.size()];
         vy = new int[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            Point coords = nodes.get(i).getCoord();
            vx[i] = coords.x;
            vy[i] = coords.y;
        }

        Polygon tmp_poly = new Polygon(vx,vy,nodes.size());
        return tmp_poly.contains(x,y);
    }

    @Override
    public void draw(Graphics g) {
        find_center();
        vx = new int[nodes.size()];
        vy = new int[nodes.size()];

        for (int i = 0; i < nodes.size(); i++) {
                Point coords = nodes.get(i).getCoord();
                vx[i] = coords.x;
                vy[i] = coords.y;
        }

            if (filled) {
                g.fillPolygon(vx,vy, nodes.size());
            } else{
            g.drawPolygon(vx,vy, nodes.size());
            }

        }



    public void find_center(){
        if (nodes.isEmpty()){return;}
            int x = 0;
            int y = 0;
            for (int i = 0; i < nodes.size(); i++) {
                 x += nodes.get(i).getCoord().x;
                 y += nodes.get(i).getCoord().y;
            }
            Point new_point = new Point(x/nodes.size(),y/nodes.size());
            Point tmp;
        if (getCoord() != null) {

            tmp = new Point(getCoord().x - new_point.x, getCoord().y - new_point.y);
            for (Object_node node : nodes) {
                node.getCoord().x += tmp.x;
                node.getCoord().y += tmp.y;
            }
        }
        setCoord(new_point);

        };

    public ArrayList<Object_node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Object_node> nodes) {
        this.nodes = nodes;
        find_center();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Custom_object {\n");
        sb.append("  center = ").append(getCoord()).append("\n");
        sb.append("  color = ").append(color).append("\n");
        sb.append("  filled = ").append(filled).append("\n");
        sb.append("  nodes count = ").append(nodes.size()).append("\n");

        sb.append("  nodes:\n");
        for (int i = 0; i < nodes.size(); i++) {
            sb.append("    [").append(i).append("] ");
            sb.append(nodes.get(i).toString()).append("\n");
        }

        sb.append("}");

        return sb.toString();
    }
    }
