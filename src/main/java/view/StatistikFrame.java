package view;

import javax.swing.*;
import java.awt.*;

public class StatistikFrame extends JFrame {
    public StatistikFrame(Container content) {
        super("Dein Ergebnis");
        this.add(content);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(350, 400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}