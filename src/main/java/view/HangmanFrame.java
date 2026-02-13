package view;

import javax.swing.*;
import java.awt.*;

public class HangmanFrame extends JFrame {
    public HangmanFrame(Container content) {
        super("Java Hangman");
        this.add(content);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}