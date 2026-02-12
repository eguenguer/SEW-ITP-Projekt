package view;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame{

    public LoginFrame(Container content){
        super("Login");
        this.add(content);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
