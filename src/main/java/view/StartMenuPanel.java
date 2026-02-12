package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartMenuPanel extends JPanel {
    private JButton trainerButton = new JButton("Wort-Trainer");
    private JButton hangmanButton = new JButton("Hangman");
    private JButton pwdButton = new JButton("Passwort ändern"); // Neu hier
    private JButton abmeldenButton = new JButton("Abmelden");
    private JLabel titelLabel = new JLabel("Hauptmenü");

    public StartMenuPanel(ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        titelLabel.setFont(new Font(null, Font.BOLD, 18));
        titelLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titelLabel, gbc);

        gbc.gridy = 1;
        trainerButton.addActionListener(controller);
        trainerButton.setActionCommand("startTrainer");
        this.add(trainerButton, gbc);

        gbc.gridy = 2;
        hangmanButton.addActionListener(controller);
        hangmanButton.setActionCommand("startHangman");
        this.add(hangmanButton, gbc);

        gbc.gridy = 3;
        pwdButton.addActionListener(controller);
        pwdButton.setActionCommand("Passwort ändern btn");
        this.add(pwdButton, gbc);

        gbc.gridy = 4;
        abmeldenButton.addActionListener(controller);
        abmeldenButton.setActionCommand("Abmelden");
        this.add(abmeldenButton, gbc);
    }
}