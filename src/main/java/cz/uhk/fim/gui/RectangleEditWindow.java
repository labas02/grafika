package cz.uhk.fim.gui;

import cz.uhk.fim.model.GraphObject;
import cz.uhk.fim.model.Rectangle;

import javax.swing.*;
import java.awt.*;

import static java.lang.Integer.parseInt;

public class RectangleEditWindow extends JFrame {
    MainWindow mainWindow;

    JLabel labSirka, labVyska;
    JTextField tfSirka, tfVyska;
    JButton btVytvor;
    JPanel pControl, pInput;
    Rectangle obj;

    public RectangleEditWindow(MainWindow mainWindow, Rectangle obj) {
        super("Pridej obdelnik");
        this.mainWindow = mainWindow;
        this.obj = obj;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(200, 150);
        initGui();
        btVytvor.addActionListener(e->{
                obj.setHeight(height());
                obj.setWidth(width());
                mainWindow.btCreateRectangleActionPerformed();});
    }

    int width() {
        return parseInt(tfSirka.getText());
    }

    int height() {
        return parseInt(tfVyska.getText());
    }

    void initGui() {
        int len = 4;
        pInput = new JPanel();
        pInput.setLayout(new FlowLayout());
        labSirka = new JLabel("Sirka:");
        pInput.add(labSirka);
        tfSirka = new JTextField(len);
        pInput.add(tfSirka);
        labVyska = new JLabel("Vyska:");
        pInput.add(labVyska);
        tfVyska = new JTextField(len);
        pInput.add(tfVyska);
        add(pInput, BorderLayout.CENTER);

        pControl = new JPanel();
        pControl.setLayout(new FlowLayout());
        btVytvor = new JButton("Vytvor");
        pControl.add(btVytvor);
        add(pControl, BorderLayout.SOUTH);
    }

}
