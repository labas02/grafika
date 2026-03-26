package cz.uhk.fim.gui;

import cz.uhk.fim.model.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.*;
import static java.awt.MouseInfo.getPointerInfo;
import static java.awt.event.MouseEvent.*;

public class Custom_object_window extends JFrame {
    MainWindow refWindow;


    JButton btVytvor;
    JPanel controlPanel;
    Object_canvas canvas;

    Option_window option_window;

    private List<Object_node> nodes = new ArrayList<>();
    private List<Object_node> selected_nodes = new ArrayList<>();
    private List<Object_node> selected_nodes_for_edit = new ArrayList<>();

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
        btVytvor.addActionListener(e-> refWindow.btCreateCustomActionPerformed());
    }

    void initGui() {

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        btVytvor = new JButton("Vytvor");
        controlPanel.add(btVytvor);
        option_window = new Option_window();
        option_window.setVisible(false);

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
            System.out.println(selected_nodes_for_edit.toString());
            switch (e.getButton()){
                case BUTTON1:
                    int px = e.getX();
                    int py = e.getY();
                    for (Object_node o : nodes) {
                        if(o.over(px, py)) {
                            if (o.isIs_selected()) {

                                o.setIs_selected(false);
                                selected_nodes_for_edit.remove(o);
                            }else {
                                if (selected_nodes_for_edit.size() == 2){
                                    selected_nodes_for_edit.getFirst().setIs_selected(false);
                                    selected_nodes_for_edit.removeFirst();
                                    selected_nodes_for_edit.addLast(o);
                                }else {
                                    selected_nodes_for_edit.addLast(o);
                                }
                                o.setIs_selected(true);
                            }
                            return;
                        }
                    }
                    nodes.add(new Object_node(new Point(px,py), blue,true,10,10));
                    break;
                case BUTTON3:
                    Point pt = getPointerInfo().getLocation();
                    option_window.setLocation(pt);
                    option_window.setVisible(true);
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

        JMenuItem mn_connect, mn_disconnect, mn_delete;
        JButton bt_connect, bt_disconnect,bt_delete;


        protected JPanel action_list;

        public Option_window() throws HeadlessException {
            init_window();
            init_gui();
        }

        void init_window(){

            setFocusable(true);
            setSize(200,200);
        }

        void init_gui(){
            action_list = new JPanel();

            mn_connect = new JMenuItem("connect");
            bt_connect = new JButton("connect");
            mn_connect.add(bt_connect);
            action_list.add(mn_connect);

            mn_disconnect = new JMenuItem("disconnect");
            bt_disconnect = new JButton("disconnect");
            mn_disconnect.add(bt_disconnect);
            action_list.add(mn_disconnect);

            mn_delete = new JMenuItem("delete");
            bt_delete = new JButton("delete");
            mn_delete.add(bt_delete);
            action_list.add(mn_delete);
            add(action_list);

            init_listeners();
            setVisible(true);
        }

        void init_listeners(){
            bt_connect.addActionListener(e->button_events(1));
            bt_disconnect.addActionListener(e->button_events(2));
            bt_delete.addActionListener(e->button_events(3));
            this.addFocusListener(focus);
        }
        FocusListener focus = new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                setVisible(false);
            }
        };

        private void button_events(int which){
            /*
            * 1 = connect
            * 2 = disconnect
            * 3 = delete
            * */
            switch (which){
                case 1:
                    selected_nodes_for_edit.getFirst().setNext_node(selected_nodes_for_edit.getLast());
                    selected_nodes_for_edit.getLast().setPrevious_node(selected_nodes_for_edit.getFirst());
                    selected_nodes_for_edit.getFirst().setIs_selected(false);
                    selected_nodes_for_edit.getLast().setIs_selected(false);
                    selected_nodes_for_edit = new ArrayList<>();
                    canvas.repaint();
                    break;
                case 2:
                    break;
                case 3:
                    Object_node o = selected_nodes_for_edit.getFirst();
                    selected_nodes.remove(o);
                    selected_nodes_for_edit.remove(o);
                    nodes.remove(o);
                    canvas.repaint();
                    break;
            }
        }
    }
}