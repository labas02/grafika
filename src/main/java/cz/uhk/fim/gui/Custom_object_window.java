package cz.uhk.fim.gui;

import cz.uhk.fim.model.*;
import cz.uhk.fim.model.Rectangle;
import org.w3c.dom.events.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import static java.awt.Color.*;
import static java.awt.Color.green;
import static java.awt.event.MouseEvent.*;

public class Custom_object_window extends JFrame {
    MainWindow refWindow;


    JButton btVytvor;
    JPanel controlPanel;
    Object_canvas canvas;

    private List<Object_node> nodes = new ArrayList<>();
    private List<Object_node> selected_nodes = new ArrayList<>();

    public Custom_object_window() {
    }

    public Custom_object_window(MainWindow refWindow) {
        super("Pridej kruh");
        this.refWindow = refWindow;
        //setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(200, 150);
        initGui();
        initData();
        initListeners();
        btVytvor.addActionListener(this.refWindow::btCreateCustomActionPerformed);
    }

    void initGui() {

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        btVytvor = new JButton("Vytvor");
        controlPanel.add(btVytvor);

        add(controlPanel, BorderLayout.SOUTH);
        canvas = new Object_canvas();

        add(canvas, BorderLayout.CENTER);
        setSize(800, 600);
        setLocationRelativeTo(null);

        setSize(800, 600);
        setLocationRelativeTo(null);
    }


    private void initData() {

    }

    private void initListeners() {


        Object_edit_mouse_listener mouseListener  = new Object_edit_mouse_listener();
        canvas.addMouseListener((MouseListener) mouseListener);
        canvas.addMouseMotionListener((MouseMotionListener) mouseListener);
    }


    class Object_edit_mouse_listener extends MouseAdapter {
        Object_node object = null;

        public void mouseClicked(MouseEvent e)
        {
            switch (e.getButton()){
                case BUTTON1:
                    int px = e.getX();
                    int py = e.getY();
                    for (Object_node o : nodes) {
                        if(o.over(px, py)) {
                            if (o.isIs_selected()) {
                                o.setIs_selected(false);
                                selected_nodes.remove(o);
                            }else {
                                selected_nodes.add(o);
                                o.setIs_selected(true);
                            }
                            return;
                        }
                    }
                    nodes.add(new Object_node(new Point(px,py), blue,true,10,10));
                    break;
                case BUTTON3:
                    Option_window op_window = new Option_window(refWindow);
                        op_window.setVisible(true);
                    break;
            }


        }

        public void mouseDragged(MouseEvent e){
            int px = e.getX();
            int py = e.getY();

            int w = canvas.getWidth();
            int h = canvas.getHeight();
            if (0 < px && px <  w && 0 < py && py < h )
                object.move(px,py);
            repaint();
        }

        public void mousePressed(MouseEvent e) {
            int px = e.getX();
            int py = e.getY();

            for (Object_node o : nodes)
            {
                if(o.over(px, py))
                    selected_nodes.add(o);
            }
           if (!selected_nodes.isEmpty()) {
               int index = selected_nodes.size() - 1;
               object = selected_nodes.get(index);
           }
        }

        public void mouseReleased(MouseEvent e) {
            int px = e.getX();
            int py = e.getY();

            selected_nodes.clear();
            //list.setListData(selectedObjects.toArray());
            object = null;
            repaint();
        }

        public void mouseMoved(MouseEvent e)
        {

        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }


    }

    class Object_canvas extends JPanel {
        public Object_canvas() {
            setLayout(null);
        }


        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setStroke(new BasicStroke(6f));
            //g2d.setXORMode(Color.BLACK);
            g2d.setColor(black);
//            g2d.drawRect(3, 3, getWidth()-6, getHeight()-6);
            g2d.setStroke(new BasicStroke(2f));
            nodes.forEach(o -> o.draw(g2d));
        }
    }

    class Option_window extends JFrame{
        MainWindow refWindow;

        protected JList action_list;

        public Option_window(MainWindow refWindow) throws HeadlessException {
            super("option menu");

            this.refWindow = refWindow;
            action_list = new JList();
            add(action_list);
            setVisible(true);
        }
    }
}