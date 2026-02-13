package controller;

import model.*;
import view.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Controller implements ActionListener {
    private LoginPanel lp;
    private LoginFrame lf;
    private RegistrationPanel rp;
    private RegistrationFrame rf;
    private PasswordChangePanel pcp;
    private PasswordChangeFrame pcf;
    private TrainerPanel tp;
    private TrainerFrame tf;
    private EintragHinzufügenPanel ehp;
    private EintragHinzufügenFrame ehf;

    private final MailPwd mailPwd;
    private final WortTrainer wortTrainer;
    private final WortSpeicher wortSpeicher;
    private String currentUser;

    public Controller() {
        mailPwd = new MailPwd();
        wortTrainer = new WortTrainer();
        wortSpeicher = new WortSpeicher();

        wortTrainer.addEintrag(new TextEintrag("Was bedeutet MVC?", "Model View Controller"));
        wortTrainer.addEintrag(new TextEintrag("Was ist Java?", "Programmiersprache"));
        wortTrainer.addEintrag(new BildEintrag("https://www.google.at/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", "Google"));

        lp = new LoginPanel(this);
        lf = new LoginFrame(lp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();

        switch (ac) {
            case "Anmelden":
                handleLogin();
                break;

            case "Konto anlegen":
                registerWindow();
                break;

            case "register":
                handleRegistration();
                break;

            case "backToLogin":
                loginWindow();
                break;

            case "Passwort ändern btn":
                pwdChangeWindow();
                break;

            case "Passwort ändern":
                handlePwdChange();
                break;

            case "Zurück":
                pcf.dispose();
                break;

            case "Abmelden":
                handleLogout();
                break;

            case "Prüfen":
                handlePrüfen();
                break;

            case "Nächste Frage":
                handleNächsteFrage();
                break;

            case "Eintrag hinzufügen":
                eintragHinzufügenWindow();
                break;

            case "eintragSpeichern":
                handleEintragHinzufügen();
                break;

            case "eintragAbbrechen":
                ehf.dispose();
                break;

            case "Speichern":
                handleSpeichern();
                break;

            case "Laden":
                handleLaden();
                break;
        }
    }

    private void handleLogin() {
        String mail = lp.getMail();
        String pwd = lp.getPwd();

        if (mailPwd.getLoginInfo().containsKey(mail)) {
            if (mailPwd.getLoginInfo().get(mail).equals(pwd)) {
                lp.successMsg();
                currentUser = mail;
                lf.dispose();
                trainerWindow();
            } else {
                lp.wrongPwd();
            }
        } else {
            lp.noUser();
        }
    }

    private void handleRegistration() {
        String mail = rp.getMail();
        String pwd = rp.getPwd();
        String pwdR = rp.getPwdRepeat();

        if (mail.isBlank() || pwd.isBlank() || pwdR.isBlank()) {
            rp.allFields();
            return;
        }

        if (mailPwd.getLoginInfo().containsKey(mail)) {
            rp.userExists();
            return;
        }

        if (!mail.toLowerCase().contains("tgm") || !mail.contains("@")) {
            rp.invalidMail();
            return;
        }

        if (!pwd.equals(pwdR)) {
            rp.pwdMismatch();
            return;
        }

        mailPwd.addUser(mail, pwd);
        rp.successMsg();
    }

    private void handlePwdChange() {
        String oldPwd = pcp.getOldPwd();
        String newPwd = pcp.getNewPwd();
        String confirmPwd = pcp.getConfirmPwd();

        if (oldPwd.isBlank() || newPwd.isBlank() || confirmPwd.isBlank()) {
            pcp.allFields();
            return;
        }

        if (!mailPwd.getLoginInfo().get(currentUser).equals(oldPwd)) {
            pcp.wrongOldPwd();
            return;
        }

        if (oldPwd.equals(newPwd)) {
            pcp.noChangePwd();
            return;
        }

        if (!newPwd.equals(confirmPwd)) {
            pcp.pwdMismatch();
            return;
        }

        mailPwd.changePwd(currentUser, newPwd);
        pcp.successMsg();
    }

    private void handleLogout() {
        currentUser = null;
        wortTrainer.resetStatistik();
        tf.dispose();
        if (pcf != null) pcf.dispose();
        if (ehf != null) ehf.dispose();
        lp = new LoginPanel(this);
        lf = new LoginFrame(lp);
    }

    private void handlePrüfen() {
        String eingabe = tp.getAntwortEingabe();

        if (eingabe.trim().isEmpty()) {
            tp.setMessage("Bitte gib eine Antwort ein!", Color.RED);
            return;
        }

        boolean richtig = wortTrainer.prüfen(eingabe);

        if (richtig) {
            tp.setMessage("Richtig!", Color.GREEN);
        } else {
            WortEintrag eintrag = wortTrainer.getAktuellerEintrag();
            tp.setMessage("Falsch! Richtige Antwort: " + eintrag.getAntwort(), Color.RED);
        }

        int verbleibend = wortTrainer.getAnzahlNochNichtGefragt();
        tp.setStatistik(wortTrainer.getStatistik() + " | Verbleibend: " + verbleibend);

        tp.enableAntwortEingabe(false);
    }

    private void handleNächsteFrage() {
        if(wortTrainer.getAktuellerEintrag()!= null){
            String eingabe =tp.getAntwortEingabe();
            wortTrainer.prüfen(eingabe);
        }

        if(wortTrainer.alleFragenBeantwortet()){
            if(tf != null){
                tf.dispose();
            }
            showStatistikWindow();
            return;
        }

        if (!wortTrainer.hatEintraege()) {
            tp.setMessage("Keine Einträge vorhanden! Bitte füge zuerst Einträge hinzu.", Color.RED);
            tp.setFrage("");
            return;
        }

        if (wortTrainer.alleFragenBeantwortet()) {
            int gesamt = wortTrainer.getRichtig() + wortTrainer.getFalsch();
            tp.showEndStatistik(wortTrainer.getRichtig(), wortTrainer.getFalsch(), gesamt);
            tp.setMessage("Quiz beendet! Lade eine neue Datei um erneut zu starten.", Color.BLUE);
            return;
        }

        WortEintrag eintrag = wortTrainer.getNächsterEintrag();
        tp.setFrage(eintrag.getFrage());
        tp.clearMessage();
        tp.clearAntwortEingabe();
        tp.enableAntwortEingabe(true);

        // Zeige verbleibende Fragen an
        int verbleibend = wortTrainer.getAnzahlNochNichtGefragt();
        tp.setStatistik(wortTrainer.getStatistik() + " | Verbleibend: " + verbleibend);
    }

    private void handleEintragHinzufügen() {
        String typ = ehp.getTyp();
        String frage = ehp.getFrage();
        String antwort = ehp.getAntwort();

        if (frage.isBlank() || antwort.isBlank()) {
            ehp.setMessage("Alle Felder müssen ausgefüllt sein!");
            return;
        }

        try {
            if (typ.equals("TEXT")) {
                wortTrainer.addEintrag(new TextEintrag(frage, antwort));
            } else {
                wortTrainer.addEintrag(new BildEintrag(frage, antwort));
            }
            ehp.setMessage("Eintrag erfolgreich hinzugefügt!");
            ehp.clearFields();
        } catch (Exception ex) {
            ehp.setMessage("Fehler: " + ex.getMessage());
        }
    }

    private void handleSpeichern() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Speichern unter");

        int result = fileChooser.showSaveDialog(tf);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filepath.endsWith(".txt")) {
                    filepath += ".txt";
                }
                wortSpeicher.speichern(filepath, wortTrainer.getEintraege());
                JOptionPane.showMessageDialog(tf, "Erfolgreich gespeichert!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(tf, "Fehler beim Speichern: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleLaden() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Datei laden");

        int result = fileChooser.showOpenDialog(tf);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String filepath = fileChooser.getSelectedFile().getAbsolutePath();
                WortEintrag[] geladen = wortSpeicher.laden(filepath);
                wortTrainer.setEintraege(geladen);
                wortTrainer.resetStatistik();
                int verbleibend = wortTrainer.getAnzahlNochNichtGefragt();
                tp.setStatistik(wortTrainer.getStatistik() + " | Verbleibend: " + verbleibend);
                tp.clearAntwortEingabe();
                tp.clearMessage();
                tp.enableAntwortEingabe(true);
                JOptionPane.showMessageDialog(tf, "Erfolgreich geladen! " + geladen.length + " Einträge.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);

                if (wortTrainer.hatEintraege()) {
                    handleNächsteFrage();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(tf, "Fehler beim Laden: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void registerWindow() {
        lf.dispose();
        rp = new RegistrationPanel(this);
        rf = new RegistrationFrame(rp);
    }

    private void loginWindow() {
        rf.dispose();
        lp = new LoginPanel(this);
        lf = new LoginFrame(lp);
    }

    private void pwdChangeWindow() {
        pcp = new PasswordChangePanel(this);
        pcf = new PasswordChangeFrame(pcp);
    }

    private void trainerWindow() {
        tp = new TrainerPanel(currentUser, this);
        tf = new TrainerFrame(tp);

        if (wortTrainer.hatEintraege()) {
            handleNächsteFrage();
        }
    }

    private void eintragHinzufügenWindow() {
        ehp = new EintragHinzufügenPanel(this);
        ehf = new EintragHinzufügenFrame(ehp);
    }

    public static void main(String[] args) {
        new Controller();
    }
}