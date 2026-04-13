package cz.uhk.fim.gui;

import javax.swing.*;
import java.awt.*;

import static java.lang.Integer.parseInt;

public class RectangleEditWindow extends JFrame {
    MainWindow mainWindow;

    JLabel labSirka, labVyska;
    JTextField tfSirka, tfVyska;
    JButton btVytvor;
    JPanel pControl, pInput;

    public RectangleEditWindow(MainWindow mainWindow) {
        super("Pridej obdelnik");
        this.mainWindow = mainWindow;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(200, 150);
        initGui();
        btVytvor.addActionListener(this.mainWindow::btCreateRectangleActionPerformed);
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
