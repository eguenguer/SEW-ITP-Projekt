package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartMenuPanel extends JPanel {
    private JButton trainerButton = new JButton("Wort-Trainer");
    private JButton hangmanButton = new JButton("Hangman");
    private JButton vListe = new JButton("Vokabelliste");
    private JButton pwdButton = new JButton("Passwort ändern"); // Neu hier
    private JButton abmeldenButton = new JButton("Abmelden");
    private JLabel titelLabel = new JLabel("Hauptmenü");
    private JLabel userLabel;

    public StartMenuPanel(String mail, ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        titelLabel.setFont(new Font(null, Font.BOLD, 18));
        titelLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titelLabel, gbc);

        gbc.gridy = 1;
        userLabel = new JLabel("Hallo " + mail);
        userLabel.setFont(new Font(null, Font.PLAIN, 12));
        userLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(userLabel, gbc);

        gbc.gridy = 2;
        trainerButton.addActionListener(controller);
        trainerButton.setActionCommand("startTrainer");
        trainerButton.setFocusable(false);
        this.add(trainerButton, gbc);

        gbc.gridy = 3;
        hangmanButton.addActionListener(controller);
        hangmanButton.setActionCommand("startHangman");
        hangmanButton.setFocusable(false);
        this.add(hangmanButton, gbc);

        gbc.gridy = 4;
        vListe.addActionListener(controller);
        vListe.setFocusable(false);
        this.add(vListe, gbc);

        gbc.gridy = 5;
        pwdButton.addActionListener(controller);
        pwdButton.setActionCommand("Passwort ändern btn");
        pwdButton.setFocusable(false);
        this.add(pwdButton, gbc);

        gbc.gridy = 6;
        abmeldenButton.addActionListener(controller);
        abmeldenButton.setActionCommand("Abmelden");
        abmeldenButton.setFocusable(false);
        this.add(abmeldenButton, gbc);
    }
}