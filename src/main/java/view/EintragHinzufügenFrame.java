package view;

import javax.swing.*;
import java.awt.*;

public class EintragHinzufügenFrame extends JFrame {
    public EintragHinzufügenFrame(Container content) {
        super("Eintrag hinzufügen");
        this.add(content);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}