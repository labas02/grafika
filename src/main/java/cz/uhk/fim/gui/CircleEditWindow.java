package cz.uhk.fim.gui;

import cz.uhk.fim.model.Circle;

import javax.swing.*;
import java.awt.*;

public class CircleEditWindow extends JFrame {
    MainWindow refWindow;

    JLabel labR;
    JTextField tfR;
    JButton btVytvor;
    JPanel pControl, pInput;
    Circle obj;

    public CircleEditWindow() {}
    public CircleEditWindow(MainWindow refWindow,Circle obj) {
        super("Pridej kruh");
        this.refWindow = refWindow;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(200, 150);
        initGui();
        btVytvor.addActionListener(this.refWindow::btCreateCircleActionPerformed);
    }

    void initGui() {
        int len = 4;
        pInput = new JPanel();
        pInput.setLayout(new FlowLayout());

        labR = new JLabel("r:");
        pInput.add(labR);
        tfR = new JTextField("",len);
        pInput.add(tfR);
        add(pInput,BorderLayout.CENTER);

        pControl = new JPanel();
        pControl.setLayout(new FlowLayout());
        btVytvor = new JButton("Vytvor");
        pControl.add(btVytvor);

        add(pControl,BorderLayout.SOUTH);
    }

    public int getR() {
        return Integer.parseInt(tfR.getText());
    }

}
