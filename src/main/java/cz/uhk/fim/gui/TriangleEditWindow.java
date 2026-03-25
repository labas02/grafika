package cz.uhk.fim.gui;

import javax.swing.*;
import java.awt.*;

public class TriangleEditWindow extends JFrame {
    JTextField tf1x, tf1y, tf2x, tf2y, tf3x, tf3y;
    JLabel labx, laby;
    JButton btVytvor;
    JPanel pInput, pxInput, pyInput, pControl;

    MainWindow refWindow;

    public TriangleEditWindow(MainWindow refWindow) {
        super("Pridej trojuhelnik");
        this.refWindow = refWindow;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(200, 150);
        initGui();
        btVytvor.addActionListener(this.refWindow::btCreateTriangleActionPerformed);
    }

    public int [] xcoord() {
        int [] x = new int [3];
        x[0] = Integer.parseInt(tf1x.getText());
        x[1] = Integer.parseInt(tf2x.getText());
        x[2] = Integer.parseInt(tf3x.getText());
        return x;
    }

    public int [] ycoord() {
        int[] y = new int[3];
        y[0] = Integer.parseInt(tf1y.getText());
        y[1] = Integer.parseInt(tf2y.getText());
        y[2] = Integer.parseInt(tf3y.getText());
        return y;
    }

    void initGui()
    {
        int len = 4;
        pInput = new JPanel();
        pInput.setLayout(new BoxLayout(pInput, BoxLayout.Y_AXIS));

        pxInput = new JPanel();
        pxInput.setLayout(new FlowLayout());
        labx = new JLabel("x:");
        pxInput.add(labx);
        tf1x = new JTextField("",len);
        pxInput.add(tf1x);
        tf2x = new JTextField("",len);
        pxInput.add(tf2x);
        tf3x = new JTextField("",len);
        pxInput.add(tf3x);

        pyInput = new JPanel();
        pyInput.setLayout(new FlowLayout());
        laby = new JLabel("y:");
        pyInput.add(laby);
        tf1y = new JTextField("",len);
        pyInput.add(tf1y);
        tf2y = new JTextField("",len);
        pyInput.add(tf2y);
        tf3y = new JTextField("",len);
        pyInput.add(tf3y);

        pInput.add(pxInput);
        pInput.add(pyInput);
        add(pInput, BorderLayout.CENTER);

        pControl = new JPanel();
        pControl.setLayout(new FlowLayout());
        btVytvor =  new JButton("Vytvor");
        pControl.add(btVytvor);
        add(pControl,BorderLayout.SOUTH);
    }

}
