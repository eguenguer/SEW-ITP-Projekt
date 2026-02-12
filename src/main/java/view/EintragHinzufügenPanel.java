package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EintragHinzufügenPanel extends JPanel {
    private JLabel titelLabel = new JLabel("Neuen Eintrag hinzufügen");
    private JLabel typLabel = new JLabel("Typ:");
    private JLabel frageLabel = new JLabel("Frage/URL:");
    private JLabel antwortLabel = new JLabel("Antwort:");
    private JLabel messageLabel = new JLabel();

    private JComboBox<String> typComboBox = new JComboBox<>(new String[]{"TEXT", "BILD"});
    private JTextField frageField = new JTextField();
    private JTextField antwortField = new JTextField();

    private JButton hinzufügenButton = new JButton("Hinzufügen");
    private JButton abbrechenButton = new JButton("Abbrechen");

    public EintragHinzufügenPanel(ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        titelLabel.setHorizontalAlignment(JLabel.CENTER);
        titelLabel.setFont(new Font(null, Font.BOLD, 14));
        this.add(titelLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(typLabel, gbc);

        gbc.gridx = 1;
        this.add(typComboBox, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(frageLabel, gbc);

        gbc.gridx = 1;
        this.add(frageField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        this.add(antwortLabel, gbc);

        gbc.gridx = 1;
        this.add(antwortField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        abbrechenButton.addActionListener(controller);
        abbrechenButton.setFocusable(false);
        abbrechenButton.setActionCommand("eintragAbbrechen");
        this.add(abbrechenButton, gbc);

        gbc.gridx = 1;
        hinzufügenButton.addActionListener(controller);
        hinzufügenButton.setFocusable(false);
        hinzufügenButton.setActionCommand("eintragSpeichern");
        this.add(hinzufügenButton, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(messageLabel, gbc);
    }

    public String getTyp() {
        return (String) typComboBox.getSelectedItem();
    }

    public String getFrage() {
        return frageField.getText();
    }

    public String getAntwort() {
        return antwortField.getText();
    }

    public void clearFields() {
        frageField.setText("");
        antwortField.setText("");
        messageLabel.setText("");
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}