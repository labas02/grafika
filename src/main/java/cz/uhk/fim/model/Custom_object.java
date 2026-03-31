package cz.uhk.fim.model;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

import static java.util.Arrays.stream;

public class Custom_object extends GraphObject {
    protected ArrayList<Object_node> nodes;
    Color color;


    public Custom_object(ArrayList<Object_node> nodes, Point pos, Color color) {
        this.nodes = nodes;
        this.coord = find_center();
        this.color = color;
    }

    public Custom_object(Point coord, Color color, boolean filled, ArrayList<Object_node> nodes, Point pos, Color color1) {
        super(coord, color, filled);
        this.nodes = nodes;
        this.coord = find_center();
        this.color = color1;
    }

    @Override
    public boolean over(int x, int y) {
        return false;
    }

    @Override
    public void draw(Graphics g) {
        System.out.println(coord);
            Point pr_node_coord = null;
            Point curr_node_coord = null;
            Point nx_node_coord = null;
            g.setColor(Color.BLACK);
            int j = 0;
            for (int i = 0; i < nodes.size(); i++) {
                Object_node curr_node = nodes.get(i);
                Object_node pr_node = curr_node.getPrevious_node();
                Object_node nx_node = curr_node.getNext_node();
                curr_node_coord = curr_node.coord;
                if (pr_node !=null){
                    g.drawLine(pr_node.coord.x,pr_node.coord.y,curr_node.coord.x,curr_node.coord.y);
                    pr_node_coord = pr_node.coord;

                }
                if (nx_node != null ){
                    g.drawLine(curr_node.coord.x,curr_node.coord.y,nx_node.coord.x,nx_node.coord.y);
                    nx_node_coord = nx_node.coord;

                }

                if (pr_node!=null && nx_node!= null){
                    if (Math.abs(curr_node_coord.x-coord.x)>Math.abs(nx_node_coord.x-coord.x)&&
                            Math.abs(curr_node_coord.y-coord.y)>Math.abs(nx_node_coord.y-coord.y)){

                        int[] vx = new int[]{pr_node_coord.x, curr_node_coord.x, nx_node_coord.x};
                        int[] vy = new int[]{pr_node_coord.y, curr_node_coord.y, nx_node_coord.y};
                        System.out.println("\ncurr node: "+curr_node_coord);
                        System.out.println("\npr node: "+pr_node_coord);
                        System.out.println("\nnx node: "+nx_node_coord);
                        g.fillPolygon(vx,vy,3);
                    }



                }


                }


            /*
            if (filled) {
                g.fillPolygon(vx,vy, nodes.size()*3);
            } else{
            g.drawPolygon(vx,vy, nodes.size()*3);
            }

             */
        }



    public Point find_center(){
            Point pos = new Point();
            int x = 0;
            int y = 0;
            for (int i = 0; i < nodes.size(); i++) {
                 x += nodes.get(i).coord.x;
                 y += nodes.get(i).coord.y;
            }
            return new Point(x/nodes.size(),y/nodes.size());
        }
    }
