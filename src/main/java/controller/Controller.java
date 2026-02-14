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
    private StartMenuPanel smp;
    private StartMenuFrame smf;
    private TrainerPanel tp;
    private TrainerFrame tf;
    private EintragHinzufügenPanel ehp;
    private EintragHinzufügenFrame ehf;
    private StatistikPanel stp;
    private StatistikFrame stf;
    private HangmanPanel hp;
    private HangmanFrame hf;

    private final MailPwd mailPwd;
    private final WortTrainer wortTrainer;
    private final WortSpeicher wortSpeicher;
    private final HangmanGame hangmanGame;
    private String currentUser;

    public Controller() {
        mailPwd = new MailPwd();
        wortTrainer = new WortTrainer();
        wortSpeicher = new WortSpeicher();
        hangmanGame = new HangmanGame();
        hangmanGame.setWortTrainer(wortTrainer);

        wortTrainer.addEintrag(new TextEintrag("Was bedeutet MVC?", "Model View Controller"));
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

            case "startTrainer":
                if (smf != null) smf.dispose();
                trainerWindow();
                break;

            case "startHangman":
                if (smf != null) smf.dispose();
                hangmanWindow();
                break;

            case "Passwort ändern btn":
                pwdChangeWindow();
                break;

            case "Abmelden":
                handleLogout();
                break;

            case "Passwort ändern":
                handlePwdChange();
                break;

            case "Zurück":
                if (pcf != null) pcf.dispose();
                break;

            case "Prüfen":
                handlePrüfen();
                break;

            case "Nächste Frage":
                handleNächsteFrage();
                break;

            case "backToMenu":
                if (tf != null) tf.dispose();
                if (hf != null) hf.dispose();
                startMenuWindow();
                break;

            case "statsBackToMenu":
                if (stf != null) stf.dispose();
                startMenuWindow();
                break;

            case "Eintrag hinzufügen":
                eintragHinzufügenWindow();
                break;

            case "eintragSpeichern":
                handleEintragHinzufügen();
                break;

            case "eintragAbbrechen":
                if (ehf != null) ehf.dispose();
                break;

            case "Speichern":
                handleSpeichern();
                break;

            case "Laden":
                handleLaden();
                break;

            case "hangman spielen":
                hangmanWindow();
                break;

            case "rate":
                handleHangmanGuess();
                break;

            case "neuesSpiel":
                startNeuesHangmanSpiel();
                break;
        }
    }

    private void handleLogin() {
        String mail = lp.getMail();
        String pwd = lp.getPwd();
        if (mailPwd.getLoginInfo().containsKey(mail) && mailPwd.getLoginInfo().get(mail).equals(pwd)) {
            currentUser = mail;
            lf.dispose();
            startMenuWindow();
        } else {
            lp.wrongPwd();
        }
    }

    private void handleRegistration() {
        String mail = rp.getMail();
        String pwd = rp.getPwd();
        String pwdR = rp.getPwdRepeat();
        if (mail.isBlank() || pwd.isBlank() || pwdR.isBlank()) { rp.allFields(); return; }
        if (!mail.contains("@") || !mail.toLowerCase().contains("tgm")) { rp.invalidMail(); return; }
        if (!pwd.equals(pwdR)) { rp.pwdMismatch(); return; }
        if (mailPwd.getLoginInfo().containsKey(mail)) { rp.userExists(); return; }

        mailPwd.addUser(mail, pwd);
        rp.successMsg();
    }

    private void handlePwdChange() {
        String oldP = pcp.getOldPwd();
        String newP = pcp.getNewPwd();
        String confP = pcp.getConfirmPwd();
        if (oldP.isBlank() || newP.isBlank()) { pcp.allFields(); return; }
        if (!mailPwd.getLoginInfo().get(currentUser).equals(oldP)) { pcp.wrongOldPwd(); return; }
        if (!newP.equals(confP)) { pcp.pwdMismatch(); return; }

        mailPwd.changePwd(currentUser, newP);
        pcp.successMsg();
    }

    private void handlePrüfen() {
        String eingabe = tp.getAntwortEingabe();
        if (eingabe.trim().isEmpty()) {
            tp.setMessage("Bitte Antwort eingeben!", Color.RED);
            return;
        }
        boolean richtig = wortTrainer.prüfen(eingabe);
        if (richtig) tp.setMessage("Richtig!", Color.GREEN);
        else tp.setMessage("Falsch! Antwort: " + wortTrainer.getAktuellerEintrag().getAntwort(), Color.RED);

        updateStatistikLabel();
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

        if (!wortTrainer.hatEintraege()) return;
        if (wortTrainer.alleFragenBeantwortet()) {
            if (tf != null) tf.dispose();
            showStatistikWindow();
            return;
        }
        WortEintrag e = wortTrainer.getNächsterEintrag();
        tp.setFrage(e.getFrage());
        tp.clearAntwortEingabe();
        tp.clearMessage();
        tp.enableAntwortEingabe(true);
        updateStatistikLabel();
    }

    private void handleEintragHinzufügen() {
        String f = ehp.getFrage();
        String a = ehp.getAntwort();
        if (f.isBlank() || a.isBlank()) { ehp.setMessage("Fehler: Felder leer!"); return; }

        if (ehp.getTyp().equals("TEXT")) wortTrainer.addEintrag(new TextEintrag(f, a));
        else wortTrainer.addEintrag(new BildEintrag(f, a));

        ehp.setMessage("Eintrag gespeichert!");
        ehp.clearFields();
    }

    private void handleSpeichern() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(tf) == JFileChooser.APPROVE_OPTION) {
            try {
                wortSpeicher.speichern(fc.getSelectedFile().getAbsolutePath(), wortTrainer.getEintraege());
            } catch (IOException ex) { JOptionPane.showMessageDialog(tf, "Fehler!"); }
        }
    }

    private void handleLaden() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(tf) == JFileChooser.APPROVE_OPTION) {
            try {
                WortEintrag[] geladen = wortSpeicher.laden(fc.getSelectedFile().getAbsolutePath());
                wortTrainer.setEintraege(geladen);
                wortTrainer.resetStatistik();
                handleNächsteFrage();
            } catch (IOException ex) { JOptionPane.showMessageDialog(tf, "Fehler!"); }
        }
    }

    private void handleLogout() {
        currentUser = null;
        if (smf != null) smf.dispose();
        if (tf != null) tf.dispose();
        if (pcf != null) pcf.dispose();
        loginWindow();
    }

    private void handleHangmanGuess() {
        String eingabe = hp.getBuchstabe();

        if (eingabe.isEmpty()) {
            hp.showMessage("Bitte geben Sie einen Buchstaben ein!");
            return;
        }

        if (eingabe.length() > 1) {
            hp.showMessage("Bitte geben Sie keine Wörter ein!");
            return;
        }

        char buchstabe = eingabe.charAt(0);

        if (!Character.isLetter(buchstabe)) {
            hp.showMessage("Bitte geben Sie einen gültigen Buchstaben ein!");
            return;
        }

        if (hangmanGame.buchstabeSchonVerwendet(buchstabe)) {
            hp.showMessage("Buchstabe wurde bereits verwendet!");
            return;
        }

        boolean treffer = hangmanGame.rateBuchstabe(buchstabe);

        hp.updateWort(hangmanGame.getErratenesWort());
        hp.updateVersuche(hangmanGame.getVerbleibendeVersuche(), hangmanGame.getMaxVersuche());
        hp.updateFalscheBuchstaben(hangmanGame.getFalscheBuchstaben());

        if (treffer) {
            hp.showMessage("Treffer! '" + buchstabe + "' ist im Wort!");
        } else {
            hp.showMessage("Fehlschlag! '" + buchstabe + "' ist nicht im Wort.");
        }

        if (hangmanGame.istGewonnen()) {
            hp.showGewonnen(hangmanGame.getWort());
        } else if (hangmanGame.istVerloren()) {
            hp.showVerloren(hangmanGame.getWort());
        }
    }

    private void startNeuesHangmanSpiel() {
        int niveau = hp.getNiveau();
        hangmanGame.neuesSpiel(niveau);

        hp.clearMessage();
        hp.updateWort(hangmanGame.getErratenesWort());
        hp.updateVersuche(hangmanGame.getVerbleibendeVersuche(), hangmanGame.getMaxVersuche());
        hp.updateFalscheBuchstaben("Keine");
        hp.showMessage("Neues Spiel gestartet! Viel Erfolg!");
    }

    private void updateStatistikLabel() {
        tp.setStatistik(wortTrainer.getStatistik() + " | Verbleibend: " + wortTrainer.getAnzahlNochNichtGefragt());
    }

    private void startMenuWindow() {
        smp = new StartMenuPanel(this);
        smf = new StartMenuFrame(smp);
    }

    private void trainerWindow() {
        tp = new TrainerPanel(currentUser, this);
        tf = new TrainerFrame(tp);
        handleNächsteFrage();
    }

    private void showStatistikWindow() {
        stp = new StatistikPanel(wortTrainer.getRichtig(), wortTrainer.getFalsch(), this);
        stf = new StatistikFrame(stp);
    }

    private void loginWindow() {
        if (rf != null) rf.dispose();
        lp = new LoginPanel(this);
        lf = new LoginFrame(lp);
    }

    private void registerWindow() {
        lf.dispose();
        rp = new RegistrationPanel(this);
        rf = new RegistrationFrame(rp);
    }

    private void pwdChangeWindow() {
        pcp = new PasswordChangePanel(this);
        pcf = new PasswordChangeFrame(pcp);
    }

    private void eintragHinzufügenWindow() {
        ehp = new EintragHinzufügenPanel(this);
        ehf = new EintragHinzufügenFrame(ehp);
    }

    private void hangmanWindow() {
        hp = new HangmanPanel(this);
        hf = new HangmanFrame(hp);
        startNeuesHangmanSpiel();
    }

    public static void main(String[] args) {
        new Controller();
    }
}