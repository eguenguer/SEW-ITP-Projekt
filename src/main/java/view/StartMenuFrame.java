package view;

import javax.swing.*;
import java.awt.*;

public class StartMenuFrame extends JFrame {
    public StartMenuFrame(Container content) {
        super("Startmenü");
        this.add(content);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(380, 370);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}