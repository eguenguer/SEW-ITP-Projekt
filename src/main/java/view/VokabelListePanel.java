package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VokabelListePanel extends JPanel {
    private JLabel titleLabel;
    private JLabel statusLabel;
    private JList<String> vListeEintraege;
    private DefaultListModel<String> listModel;
    private JButton ladenButton;
    private JButton speichernButton;
    private JButton eintragHinzufügenButton;
    private JButton bearbeitenButton;
    private JButton löschenButton;
    private JButton zurückButton;

    public VokabelListePanel(ActionListener controller){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        titleLabel = new JLabel("Vokabelliste Manager");
        titleLabel.setFont(new Font(null, Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        this.add(titleLabel, gbc);

        statusLabel = new JLabel("Keine Vokabelliste geladen");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        gbc.gridy = 1;
        this.add(statusLabel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        listModel = new DefaultListModel<>();
        vListeEintraege = new JList<>(listModel);
        vListeEintraege.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(vListeEintraege);
        scrollPane.setPreferredSize(new Dimension(450,200));
        this.add(scrollPane, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridwidth = 1;

        //gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridx = 0;
        ladenButton = new JButton("Laden");
        ladenButton.setActionCommand("vokabelliste laden");
        ladenButton.addActionListener(controller);
        ladenButton.setFocusable(false);
        this.add(ladenButton, gbc);

        gbc.gridx = 1;
        speichernButton = new JButton("Speichern");
        speichernButton.setActionCommand("vokabelliste speichern");
        speichernButton.addActionListener(controller);
        speichernButton.setFocusable(false);
        speichernButton.setEnabled(false);
        this.add(speichernButton, gbc);

        //gbc.gridy = 3;
        gbc.gridx = 2;
        eintragHinzufügenButton = new JButton("Eintrag hinzufügen");
        eintragHinzufügenButton.setActionCommand("vokabelliste Eintrag hinzufügen");
        eintragHinzufügenButton.addActionListener(controller);
        eintragHinzufügenButton.setFocusable(false);
        eintragHinzufügenButton.setEnabled(false);
        this.add(eintragHinzufügenButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        bearbeitenButton = new JButton("Bearbeiten");
        bearbeitenButton.setActionCommand("vokabelliste Eintrag bearbeiten");
        bearbeitenButton.addActionListener(controller);
        bearbeitenButton.setFocusable(false);
        this.add(bearbeitenButton, gbc);

        gbc.gridx = 1;
        löschenButton = new JButton("Löschen");
        löschenButton.setActionCommand("vokabelliste Eintrag löschen");
        löschenButton.addActionListener(controller);
        löschenButton.setFocusable(false);
        löschenButton.setEnabled(false);
        this.add(löschenButton, gbc);

        gbc.gridx = 2;
        zurückButton = new JButton("Zurück");
        zurückButton.setActionCommand("vokabelliste zurück");
        zurückButton.addActionListener(controller);
        zurückButton.setFocusable(false);
        this.add(zurückButton, gbc);

        vListeEintraege.addListSelectionListener(e -> {
            boolean ausgew = !vListeEintraege.isSelectionEmpty();
            bearbeitenButton.setEnabled(ausgew);
            löschenButton.setEnabled(ausgew);
        });
    }

    public void setListeGeladen(boolean geladen, int anzahlEintraege){
        if(geladen && anzahlEintraege > 0){
            statusLabel.setText("Vokabelliste geladen (" + anzahlEintraege + " Einträge)");
            statusLabel.setForeground(Color.GREEN);
            speichernButton.setEnabled(true);
            eintragHinzufügenButton.setEnabled(true);
        }else if (geladen && anzahlEintraege == 0){
            statusLabel.setText("Vokabelliste geladen (0 Einträge - bitte Einträge hinzufügen!)");
            statusLabel.setForeground(Color.ORANGE);
            speichernButton.setEnabled(true);
            eintragHinzufügenButton.setEnabled(true);
        }else{
            statusLabel.setText("Keine Vokabelliste geladen");
            statusLabel.setForeground(Color.RED);
            speichernButton.setEnabled(false);
            eintragHinzufügenButton.setEnabled(false);
        }
    }

    public void updateEintraegeAnzeige(model.WortEintrag[] eintraege){
        listModel.clear();
        for (int i = 0; i < eintraege.length; i++) {
            listModel.addElement((i + 1) + ". " + eintraege[i].getFrage() + " --> " + eintraege[i].getAntwort());
        }
    }

    public int getSelectedIndex(){
        return vListeEintraege.getSelectedIndex();
    }

    public void clearSelection(){
        vListeEintraege.clearSelection();
    }
}