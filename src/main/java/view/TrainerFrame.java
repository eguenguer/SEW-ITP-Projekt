package view;

import javax.swing.*;
import java.awt.*;

public class TrainerFrame extends JFrame {
    public TrainerFrame(Container content) {
        super("ITP-Fachbegriffstrainer");
        this.add(content);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}