package cz.uhk.fim.gui;

import cz.uhk.fim.model.*;
import cz.uhk.fim.model.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.*;

public class MainWindow extends javax.swing.JFrame {
    TriangleEditWindow winTri;
    CircleEditWindow winCirc;
    RectangleEditWindow winRect;
    Custom_object_window winCustom;

    MujDrawPanel drawPanel;
    JPanel controlPanel, objectPanel;
    JTextField tfx, tfy;
    JLabel labx, laby;
    JList list;
    JScrollPane scList;
    JButton btCircle, btRectangle, btTriangle,btCustom;
    JComboBox<Barva> cbBarva;
    JCheckBox xbFilled;

    private List<GraphObject> objects = new ArrayList<>();
    private List<GraphObject> selectedObjects = new ArrayList<>();

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
        initGui();
        initData();
        initWindows();
        initListeners();
    }

    private void initWindows() {
        winTri = new TriangleEditWindow(this);
        winCirc = new CircleEditWindow(this);
        winRect = new RectangleEditWindow(this);
        winTri.setVisible(false);
        winCirc.setVisible(false);
        winRect.setVisible(false);
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
        btRectangle = new JButton("Rectangle");
        controlPanel.add(btRectangle);
        btTriangle = new JButton("Triangle");
        controlPanel.add(btTriangle);
        btCustom = new JButton("custom");
        controlPanel.add(btCustom);
        add(controlPanel, BorderLayout.SOUTH);

        objectPanel = new JPanel();
        objectPanel.setLayout(new FlowLayout());
        list = new JList();
        list.setModel(new DefaultListModel());
        list.setListData(selectedObjects.toArray());

        scList = new JScrollPane(list);
        scList.setSize(60, 600);
        objectPanel.add(scList);
        add(objectPanel, BorderLayout.EAST);

        drawPanel = new MujDrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
    }


    private void initListeners()
    {
        btCircle.addActionListener(this::btKruhActionPerformed);
        btRectangle.addActionListener(this::btObdelnikActionPerformed);
        btTriangle.addActionListener(this::btTrojuhelnikActionPerformed);
        btCustom.addActionListener(this::btCustomActionPerformed);

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
        }

        public void mouseDragged(MouseEvent e){
            int px = e.getX();
            int py = e.getY();

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
            list.setListData(selectedObjects.toArray());
            int index = selectedObjects.size() - 1;
            list.setSelectedIndex(index);
            object = selectedObjects.get(index);
        }

        public void mouseReleased(MouseEvent e) {
            int px = e.getX();
            int py = e.getY();

            selectedObjects.clear();
            list.setListData(selectedObjects.toArray());
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

    void btTrojuhelnikActionPerformed(ActionEvent evt) {
        winTri.setVisible(true);
    }

    void btKruhActionPerformed(ActionEvent evt) {
        winCirc.setVisible(true);
    }

    void btCustomActionPerformed(ActionEvent evt) {
        winCustom = new Custom_object_window(this);
        winCustom.setVisible(true);
    }

    void btObdelnikActionPerformed(ActionEvent evt) {
        winRect.setVisible(true);
    }

    public void btCreateRectangleActionPerformed(ActionEvent actionEvent) {
        Color color = ((Barva) cbBarva.getSelectedItem()).color();
        boolean vypln = xbFilled.isSelected();
        int x =  Integer.parseInt(tfx.getText());
        int y =  Integer.parseInt(tfy.getText());
        int sirka = winRect.width();
        int vyska = winRect.height();

        winRect.setVisible(false);
        objects.add(new Rectangle(x,y,color,vypln,sirka,vyska));
        repaint();
    }

    void btCreateTriangleActionPerformed(ActionEvent e) {
        Color color = ((Barva) cbBarva.getSelectedItem()).color();
        boolean vypln = xbFilled.isSelected();

        Point [] vrcholy = new Point[3];
        int x [] = winTri.xcoord();
        int y [] = winTri.ycoord();

        for (int i = 0; i < 3; i++) {
            vrcholy[i] = new Point(x[i], y[i]);
        }

        winTri.setVisible(false);
        objects.add(new Triangle(vrcholy, color, vypln));
        repaint();
    }

    void btCreateCircleActionPerformed(ActionEvent e) {
        Color color = ((Barva) cbBarva.getSelectedItem()).color();
        boolean vypln = xbFilled.isSelected();
        int x =  Integer.parseInt(tfx.getText());
        int y =  Integer.parseInt(tfy.getText());

        winCirc.setVisible(false);
        objects.add(new Circle(x,y, color, vypln, winCirc.getR()));
        repaint();
    }

    void btCreateCustomActionPerformed(ArrayList<Object_node> nodes){

        objects.add(new Custom_object(nodes,new Point(0,0), BLACK));
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
        objects.add(new Triangle(vrcholy, green,false));
    }

    class MujDrawPanel extends JPanel {
        public MujDrawPanel() {
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
            objects.forEach(o -> o.draw(g2d));
        }
    }
}
