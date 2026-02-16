package view;

import javax.swing.*;
import java.awt.*;

public class VokabelListeFrame extends JFrame {
    public VokabelListeFrame(Container content) {
        this.setTitle("Vokabelliste Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400); // mal so austesten // sonst ja automatische Size
        this.setResizable(false);
        this.add(content);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}