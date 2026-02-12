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
    private JButton zurückZumMenüButton = new JButton("Zurück zum Menü");

    public TrainerPanel(String mail, ActionListener controller) {
        this.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        welcomeLabel.setText("Hallo " + mail);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setFont(new Font(null, Font.BOLD, 18));
        topPanel.add(welcomeLabel, BorderLayout.NORTH);

        statistikLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(statistikLabel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(frageLabel, gbc);

        gbc.gridy = 1;
        frageAnzeige.setEditable(false);
        frageAnzeige.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(frageAnzeige);
        scrollPane.setPreferredSize(new Dimension(400, 80));
        centerPanel.add(scrollPane, gbc);

        gbc.gridy = 2; gbc.gridwidth = 1;
        centerPanel.add(antwortLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(antwortEingabe, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        prüfenButton.addActionListener(controller);
        centerPanel.add(prüfenButton, gbc);

        gbc.gridx = 1;
        nächsteFrageButton.addActionListener(controller);
        centerPanel.add(nächsteFrageButton, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(messageLabel, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());

        eintragHinzufügenButton.addActionListener(controller);
        bottomPanel.add(eintragHinzufügenButton);

        speichernButton.addActionListener(controller);
        bottomPanel.add(speichernButton);

        ladenButton.addActionListener(controller);
        bottomPanel.add(ladenButton);

        zurückZumMenüButton.addActionListener(controller);
        zurückZumMenüButton.setActionCommand("backToMenu");
        bottomPanel.add(zurückZumMenüButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getAntwortEingabe() { return antwortEingabe.getText(); }
    public void clearAntwortEingabe() { antwortEingabe.setText(""); }
    public void setFrage(String frage) { frageAnzeige.setText(frage); }
    public void setStatistik(String statistik) { statistikLabel.setText(statistik); }
    public void setMessage(String message, Color color) { messageLabel.setText(message); messageLabel.setForeground(color); }
    public void clearMessage() { messageLabel.setText(""); }
    public void enableAntwortEingabe(boolean b) { antwortEingabe.setEnabled(b); prüfenButton.setEnabled(b); }
    public void showEndStatistik(int r, int f, int g) {
        frageAnzeige.setText("Quiz beendet!\nRichtig: " + r + " / " + g);
        enableAntwortEingabe(false);
        nächsteFrageButton.setEnabled(false);
    }
}