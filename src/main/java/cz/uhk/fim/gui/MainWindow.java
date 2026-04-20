package cz.uhk.fim.gui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.uhk.fim.model.*;
import cz.uhk.fim.model.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

import static java.awt.Color.*;

public class MainWindow extends JFrame {
    CircleEditWindow winCirc;
    RectangleEditWindow winRect;
    Custom_object_window winCustom;

    MujDrawPanel drawPanel;
    JPanel controlPanel, objectPanel;
    JTextField tfx, tfy;
    JLabel labx, laby;


    JPanel listPanel;

    JButton btCircle,btObdelnik,btCustom, btSaveState,btLoadState;
    JComboBox<Barva> cbBarva;
    JCheckBox xbFilled;

    private ArrayList<GraphObject> objects = new ArrayList<>();
    private ArrayList<GraphObject> selectedObjects = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    public MainWindow() {
        super("Grafika");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initData();
        initGui();
        initWindows();
        initListeners();
    }

    private void initWindows() {
    }

    private void initGui()
    {
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        labx = new JLabel("x");
        controlPanel.add(labx);
        tfx = new JTextField("", 8);
        controlPanel.add(tfx);
        laby = new JLabel("y");
        controlPanel.add(laby);
        tfy = new JTextField("", 8);
        controlPanel.add(tfy);

        xbFilled = new JCheckBox("vypln", false);
        controlPanel.add(xbFilled);

        cbBarva = new JComboBox<>(Barva.values());
        controlPanel.add(cbBarva);

        btCircle = new JButton("Circle");
        controlPanel.add(btCircle);
        btObdelnik = new JButton("obdelnik");
        controlPanel.add(btObdelnik);
        btCustom = new JButton("custom");
        controlPanel.add(btCustom);
        btSaveState = new JButton("save state");
        controlPanel.add(btSaveState);
        btLoadState = new JButton("load state");
        controlPanel.add(btLoadState);
        add(controlPanel, BorderLayout.SOUTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        listPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setMaximumSize(new Dimension(300, 0));
        add(scrollPane,BorderLayout.EAST);

        drawPanel = new MujDrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
        update_list();
    }

    private void update_list(){
        listPanel.removeAll();
        for (GraphObject obj : objects) {
            listPanel.add(create_row(obj));
        }
        revalidate();
        repaint();
    }

    private JPanel create_row(GraphObject obj) {
        JPanel row = new JPanel(new BorderLayout());

        JLabel label = new JLabel(obj.get_type_string());
        label.setPreferredSize(new Dimension(100, 20));
        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(100, 20));
        JButton deleteButton = new JButton("delete");

        deleteButton.addActionListener(e->{
            objects.remove(obj);
            update_list();
            revalidate();
            repaint();
        });

        editButton.addActionListener(e -> {
            System.out.println("Edit clicked: ");
            switch (obj.get_type()){
                case 1:
                    winCirc = new CircleEditWindow(this, (Circle) obj);
                    winCirc.setVisible(true);
                    break;
                case 2:
                    winRect = new RectangleEditWindow(this, (Rectangle) obj);
                    winRect.setVisible(true);
                    break;
                case 3:
                    winCustom = new Custom_object_window(this,(Custom_object) obj);
                    winCustom.setVisible(true);
                    break;
                case 4:
                    break;
            }
            update_list();
            revalidate();
            repaint();
        });

        row.add(label, BorderLayout.WEST);
        row.add(editButton, BorderLayout.CENTER);
        row.add(deleteButton, BorderLayout.EAST);

        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // keeps rows compact

        return row;
    }


    private void initListeners()
    {
        btCircle.addActionListener(this::btKruhActionPerformed);
        btCustom.addActionListener(this::btCustomActionPerformed);
        btObdelnik.addActionListener(this::btObdelnikActionPerformed);
        btSaveState.addActionListener(evt -> btExportActionPerformed(evt));
        btLoadState.addActionListener(this::btImportActionPerformed);

        CustomMouseListener mouseListener  = new CustomMouseListener();
        drawPanel.addMouseListener((MouseListener) mouseListener);
        drawPanel.addMouseMotionListener((MouseMotionListener) mouseListener);
    }

    class CustomMouseListener extends MouseAdapter{
        GraphObject object = null;

        public void mouseClicked(MouseEvent e)
        {
            tfx.setText(String.valueOf(e.getX()));
            tfy.setText(String.valueOf(e.getY()));
            update_list();
        }

        public void mouseDragged(MouseEvent e){
            int px = e.getX();
            int py = e.getY();
            System.out.println(objects.toString());

            int w = drawPanel.getWidth();
            int h = drawPanel.getHeight();
            if (0 < px && px <  w && 0 < py && py < h )
                object.move(px,py);
            repaint();
        }

        public void mousePressed(MouseEvent e) {
            int px = e.getX();
            int py = e.getY();

            for (GraphObject o : objects)
            {
                if(o.over(px, py))
                    selectedObjects.add(o);
            }

            int index = selectedObjects.size() - 1;

            object = selectedObjects.get(index);
        }

        public void mouseReleased(MouseEvent e) {
            int px = e.getX();
            int py = e.getY();

            selectedObjects.clear();
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


    void btKruhActionPerformed(ActionEvent evt) {
        objects.add(new Circle(new Point(Integer.parseInt(tfx.getText()),Integer.parseInt(tfy.getText())),((Barva) cbBarva.getSelectedItem()).color(),xbFilled.isSelected(),0));
        winCirc = new CircleEditWindow(this,(Circle) objects.getLast());
        winCirc.setVisible(true);
        update_list();
        repaint();
    }

    void btCustomActionPerformed(ActionEvent evt) {
        System.out.println(xbFilled.getText());
        objects.add(new Custom_object(new ArrayList<>(),new Point(0,0),((Barva) cbBarva.getSelectedItem()).color(),xbFilled.isSelected()));
        winCustom = new Custom_object_window(this, (Custom_object) objects.getLast());
        winCustom.setVisible(true);
        update_list();
        repaint();
    }

    void btExportActionPerformed(ActionEvent evt)  {
        ObjectMapper mapper = new ObjectMapper();
        try {

            mapper.writeValue(new File("data/json"),objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void btImportActionPerformed(ActionEvent evt){
        objects = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonnode = mapper.readTree(new File("data/json"));
            ArrayList<GraphObject> tmp = new ArrayList<>();
            jsonnode.forEach(element->{
                Point coord;
                JsonNode jsoncolor;
                Color color;
                switch (element.get("_type").asInt()){
                    case 1:
                        coord = new Point(element.get("coord").get("x").asInt(),element.get("coord").get("y").asInt());
                        jsoncolor = element.get("color");
                        color = new Color(jsoncolor.get("red").asInt(),jsoncolor.get("green").asInt(),jsoncolor.get("blue").asInt());
                        objects.add(new Circle(coord,color,element.get("filled").asBoolean(),element.get("radius").asInt()));
                        break;
                    case 2:
                        coord = new Point(element.get("coord").get("x").asInt(),element.get("coord").get("y").asInt());
                        jsoncolor = element.get("color");
                        color = new Color(jsoncolor.get("red").asInt(),jsoncolor.get("green").asInt(),jsoncolor.get("blue").asInt());
                        objects.add(new Rectangle(coord,color,element.get("filled").asBoolean(),element.get("width").asInt(),element.get("height").asInt()));
                        System.out.println(2);
                        break;
                    case 3:
                        coord = new Point(element.get("coord").get("x").asInt(),element.get("coord").get("y").asInt());
                        jsoncolor = element.get("color");
                        color = new Color(jsoncolor.get("red").asInt(),jsoncolor.get("green").asInt(),jsoncolor.get("blue").asInt());
                        ArrayList<Object_node> nodes = new ArrayList<>();
                        JsonNode jsonNodes = element.get("nodes");
                        jsonNodes.forEach(node->{
                            Point ndcoord;
                            if (nodes.isEmpty()){
                                ndcoord = new Point(node.get("coord").get("x").asInt(), node.get("coord").get("y").asInt());
                                Object_node new_node = new Object_node(ndcoord, BLUE, node.get("filled").asBoolean(), 10, 10);
                                nodes.add(new_node);
                            }
                            else {
                                ndcoord = new Point(node.get("coord").get("x").asInt(), node.get("coord").get("y").asInt());
                                System.out.println(ndcoord);
                                Object_node new_node = new Object_node(ndcoord, BLUE, node.get("filled").asBoolean(), 10, 10);
                                nodes.add(new_node);
                            }
                            });
                        System.out.println(nodes.size());
                        for (int i = 0; i < nodes.size(); i++) {
                            if (i == 0) {
                                nodes.get(i).setNext_node(nodes.get(i + 1));
                                nodes.get(i).setPrevious_node(nodes.get(nodes.size() - 1));
                            }
                            else if (i == nodes.size() - 1) {
                                nodes.get(i).setNext_node(nodes.get(0));
                                nodes.get(i).setPrevious_node(nodes.get(i - 1));
                            }
                            else {
                                nodes.get(i).setNext_node(nodes.get(i + 1));
                                nodes.get(i).setPrevious_node(nodes.get(i - 1));
                            }
                        }


                        System.out.println(nodes.toString());
                        objects.add(new Custom_object(nodes,coord, color,element.get("filled").asBoolean()));
                        System.out.println(3);
                        break;

                    default:
                        break;
                }

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        update_list();
        revalidate();
        repaint();
    }

    void btObdelnikActionPerformed(ActionEvent evt) {
        boolean vypln = xbFilled.isSelected();
        int x =  Integer.parseInt(tfx.getText());
        int y =  Integer.parseInt(tfy.getText());
        Color color = ((Barva) cbBarva.getSelectedItem()).color();
        objects.add(new Rectangle(new Point(x,y),color,vypln,0,0));
        winRect = new RectangleEditWindow(this, (Rectangle) objects.getLast());
        winRect.setVisible(true);
    }

    public void btCreateRectangleActionPerformed() {
        winRect.setVisible(false);
        repaint();
    }


    void btCreateCircleActionPerformed(ActionEvent e) {
        winCirc.setVisible(false);
        repaint();
    }

    void btCreateCustomActionPerformed(ArrayList<Object_node> nodes){
        remove(winCustom);
        winCustom = null;
        revalidate();
        repaint();
    }

        private void initData() {
        int sx = 260;
        int sy = 300;

        objects.add(new Rectangle(new Point(sx,sy), blue,true,60,80));
        objects.add(new Circle(new Point(200,50), red,false,15));
        Point v0 = new Point(40,120);
        Point v1 = new Point(200,150);
        Point v2 = new Point(280,10);
        Point [] vrcholy  = new Point [3];
        vrcholy[0] = v0;
        vrcholy[1] = v1;
        vrcholy[2] = v2;
    }

    class MujDrawPanel extends JPanel {
        public MujDrawPanel() {
            setLayout(null);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(white);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setStroke(new BasicStroke(6f));
            //g2d.setXORMode(Color.BLACK);
            g2d.setColor(black);
//            g2d.drawRect(3, 3, getWidth()-6, getHeight()-6);
            g2d.setStroke(new BasicStroke(2f));
            objects.forEach(o -> o.draw(g2d));
        }
    }


}
