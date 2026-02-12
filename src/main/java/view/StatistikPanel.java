package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StatistikPanel extends JPanel {
    public StatistikPanel(int richtig, int falsch, ActionListener controller) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int gesamt = richtig + falsch;
        double prozent = (gesamt > 0) ? (richtig * 100.0 / gesamt) : 0;

        JLabel titelLabel = new JLabel("Statistik");
        titelLabel.setFont(new Font(null, Font.BOLD, 24));
        titelLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        this.add(titelLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; addStatLine("Gesamt beantwortet:", String.valueOf(gesamt), gbc);
        gbc.gridy = 2; addStatLine("Richtig:", String.valueOf(richtig), gbc);
        gbc.gridy = 3; addStatLine("Falsch:", String.valueOf(falsch), gbc);
        gbc.gridy = 4; addStatLine("Erfolgsquote:", String.format("%.1f%%", prozent), gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        JButton backButton = new JButton("Zurück zum Hauptmenü");
        backButton.addActionListener(controller);
        backButton.setActionCommand("statsBackToMenu");
        this.add(backButton, gbc);
    }

    private void addStatLine(String text, String wert, GridBagConstraints gbc) {
        gbc.gridx = 0;
        JLabel label = new JLabel(text);
        label.setFont(new Font(null, Font.PLAIN, 16));
        this.add(label, gbc);

        gbc.gridx = 1;
        JLabel wertLabel = new JLabel(wert);
        wertLabel.setFont(new Font(null, Font.BOLD, 16));
        wertLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(wertLabel, gbc);
    }
}