package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TrainerPanel extends JPanel {
    private JLabel welcomeLabel = new JLabel();
    private JLabel frageLabel = new JLabel("Frage:");
    private JLabel antwortLabel = new JLabel("Deine Antwort:");
    private JLabel statistikLabel = new JLabel("Richtig: 0 | Falsch: 0 | Verbleibend: 0");
    private JLabel messageLabel = new JLabel();

    private JTextArea frageAnzeige = new JTextArea(3, 30);
    private JTextField antwortEingabe = new JTextField(20);

    private JButton prüfenButton = new JButton("Prüfen");
    private JButton nächsteFrageButton = new JButton("Nächste Frage");
    private JButton eintragHinzufügenButton = new JButton("Eintrag hinzufügen");
    private JButton speichernButton = new JButton("Speichern");
    private JButton ladenButton = new JButton("Laden");
    private JButton passwortÄndernButton = new JButton("Passwort ändern btn");
    private JButton abmeldenButton = new JButton("Abmelden");

    public TrainerPanel(String mail, ActionListener controller) {
        this.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        welcomeLabel.setText("Hallo " + mail + " - ITP-Fachbegriffstrainer");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font(null, Font.BOLD, 18));
        topPanel.add(welcomeLabel, BorderLayout.NORTH);

        statistikLabel.setHorizontalAlignment(JLabel.CENTER);
        statistikLabel.setFont(new Font(null, Font.PLAIN, 14));
        topPanel.add(statistikLabel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(frageLabel, gbc);

        gbc.gridy = 1;
        frageAnzeige.setEditable(false);
        frageAnzeige.setLineWrap(true);
        frageAnzeige.setWrapStyleWord(true);
        frageAnzeige.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(frageAnzeige);
        scrollPane.setPreferredSize(new Dimension(400, 80));
        centerPanel.add(scrollPane, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        centerPanel.add(antwortLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(antwortEingabe, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        prüfenButton.addActionListener(controller);
        prüfenButton.setFocusable(false);
        centerPanel.add(prüfenButton, gbc);

        gbc.gridx = 1;
        nächsteFrageButton.addActionListener(controller);
        nächsteFrageButton.setFocusable(false);
        centerPanel.add(nächsteFrageButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setFont(new Font(null, Font.BOLD, 12));
        centerPanel.add(messageLabel, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JPanel verwaltungPanel = new JPanel(new FlowLayout());
        eintragHinzufügenButton.addActionListener(controller);
        eintragHinzufügenButton.setFocusable(false);
        verwaltungPanel.add(eintragHinzufügenButton);

        speichernButton.addActionListener(controller);
        speichernButton.setFocusable(false);
        verwaltungPanel.add(speichernButton);

        ladenButton.addActionListener(controller);
        ladenButton.setFocusable(false);
        verwaltungPanel.add(ladenButton);

        bottomPanel.add(verwaltungPanel);

        JPanel accountPanel = new JPanel(new FlowLayout());
        passwortÄndernButton.addActionListener(controller);
        passwortÄndernButton.setFocusable(false);
        accountPanel.add(passwortÄndernButton);

        abmeldenButton.addActionListener(controller);
        abmeldenButton.setFocusable(false);
        accountPanel.add(abmeldenButton);

        bottomPanel.add(accountPanel);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getAntwortEingabe() {
        return antwortEingabe.getText();
    }

    public void clearAntwortEingabe() {
        antwortEingabe.setText("");
    }

    public void setFrage(String frage) {
        frageAnzeige.setText(frage);
    }

    public void setStatistik(String statistik) {
        statistikLabel.setText(statistik);
    }

    public void setMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);
    }

    public void clearMessage() {
        messageLabel.setText("");
    }

    public void clearFrage() {
        frageAnzeige.setText("");
    }

    public void enableAntwortEingabe(boolean enabled) {
        antwortEingabe.setEnabled(enabled);
        prüfenButton.setEnabled(enabled);

    }

    public void showEndStatistik(int richtig, int falsch, int gesamt) {
        frageAnzeige.setText("QUIZ BEENDET!\n\n" +
                "Gesamtstatistik:\n" +
                "─────────────────\n" +
                "Richtig beantwortet: " + richtig + " (" + String.format("%.1f", (richtig * 100.0 / gesamt)) + "%)\n" +
                "Falsch beantwortet: " + falsch + " (" + String.format("%.1f", (falsch * 100.0 / gesamt)) + "%)\n" +
                "Gesamt: " + gesamt + " Fragen\n\n" +
                "Klicke auf 'Laden' um ein neues Quiz zu starten!");
        antwortEingabe.setEnabled(false);
        prüfenButton.setEnabled(false);
        nächsteFrageButton.setEnabled(false);
    }
}