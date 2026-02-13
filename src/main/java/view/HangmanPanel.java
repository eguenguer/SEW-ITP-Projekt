package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HangmanPanel extends JPanel {
    private JLabel titleLabel;
    private JLabel wortLabel;
    private JLabel versucheLabel;
    private JLabel falscheBuchstabenLabel;
    private JLabel messageLabel;
    private JTextField buchstabeField;
    private JButton rateButton;
    private JButton neuesSpielButton;
    private JButton zurueckButton;
    private JRadioButton niveau1Button;
    private JRadioButton niveau2Button;
    private JRadioButton niveau3Button;
    private ButtonGroup niveauGroup;

    public HangmanPanel(ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        titleLabel = new JLabel("Hangman");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font(null, Font.BOLD, 18));
        this.add(titleLabel, gbc);

        gbc.gridy = 1;
        wortLabel = new JLabel("_____________");
        wortLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(wortLabel, gbc);

        gbc.gridy = 2;
        versucheLabel = new JLabel("Verbleibende Versuche: 0");
        versucheLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(versucheLabel, gbc);

        gbc.gridy = 3;
        falscheBuchstabenLabel = new JLabel("Falsche Buchstaben: Keine");
        falscheBuchstabenLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(falscheBuchstabenLabel, gbc);

        gbc.gridy = 4;
        messageLabel = new JLabel("");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(messageLabel, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        buchstabeField = new JTextField();
        buchstabeField.setFont(new Font(null, Font.PLAIN, 18));
        buchstabeField.setHorizontalAlignment(JTextField.CENTER);
        this.add(buchstabeField, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        rateButton = new JButton("Raten");
        rateButton.setFocusable(false);
        rateButton.setActionCommand("rate");
        rateButton.addActionListener(controller);
        this.add(rateButton, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        niveau1Button = new JRadioButton("Einfach");
        this.add(niveau1Button, gbc);

        gbc.gridx = 1;
        niveau2Button = new JRadioButton("Mittel", true);
        this.add(niveau2Button, gbc);

        gbc.gridx = 2;
        niveau3Button = new JRadioButton("Schwer");
        this.add(niveau3Button, gbc);

        niveauGroup = new ButtonGroup();
        niveauGroup.add(niveau1Button);
        niveauGroup.add(niveau2Button);
        niveauGroup.add(niveau3Button);

        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        neuesSpielButton = new JButton("Neues Spiel");
        neuesSpielButton.setFocusable(false);
        neuesSpielButton.setActionCommand("neuesSpiel");
        neuesSpielButton.addActionListener(controller);
        this.add(neuesSpielButton, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        zurueckButton = new JButton("Zurück");
        zurueckButton.setFocusable(false);
        zurueckButton.setActionCommand("backToMenu");
        zurueckButton.addActionListener(controller);
        this.add(zurueckButton, gbc);
    }

    public String getBuchstabe() {
        String text = buchstabeField.getText();
        buchstabeField.setText("");
        return text;
    }

    public int getNiveau() {
        if(niveau1Button.isSelected()) return 1;
        if(niveau2Button.isSelected()) return 2;
        if(niveau3Button.isSelected()) return 3;
        return 2;
    }

    public void updateWort(String wort) {
        wortLabel.setText(wort);
    }

    public void updateVersuche(int verbleibend, int max) {
        versucheLabel.setText("Verbleibende Versuche: " + verbleibend + " / " + max);
    }

    public void updateFalscheBuchstaben(String buchstaben) {
        falscheBuchstabenLabel.setText("Falsche Buchstaben: " + buchstaben);
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    public void showGewonnen(String wort) {
        messageLabel.setForeground(Color.GREEN);
        messageLabel.setText("GEWONNEN! Das Wort war: " + wort);
    }

    public void showVerloren(String wort) {
        messageLabel.setForeground(Color.RED);
        messageLabel.setText("VERLOREN! Das Wort war: " + wort);
    }

    public void clearMessage() {
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setText("");
    }
}