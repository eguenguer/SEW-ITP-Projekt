package view;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {
        public RegistrationFrame(Container content){
            super("Registrierung");
            this.add(content);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setSize(400, 300);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
}
