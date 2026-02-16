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
    private VokabelListePanel vp;
    private VokabelListeFrame vf;

    private final MailPwd mailPwd;
    private final WortTrainer wortTrainer;
    private final WortSpeicher wortSpeicher;
    private final HangmanGame hangmanGame;
    private String currentUser;
    private boolean vListeGeladen = false;
    private boolean schonGeprueft = false;

    public Controller() {
        mailPwd = new MailPwd();
        wortTrainer = new WortTrainer();
        wortSpeicher = new WortSpeicher();
        hangmanGame = new HangmanGame();
        hangmanGame.setWortTrainer(wortTrainer);

        //wortTrainer.addEintrag(new TextEintrag("Was bedeutet MVC?", "Model View Controller"));
        //wortTrainer.addEintrag(new BildEintrag("https://www.google.at/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", "Google"));

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
                handleStartTrainer();
                break;

            case "startHangman":
                handleStartHangman();
                break;

            case "Vokabelliste":
                vListeEditWindow();
                break;

            case "vokabelliste laden":
                handlevListeLaden();
                break;

            case "vokabelliste speichern":
                handlevListeSpeichern();
                break;

            case "vokabelliste Eintrag hinzufügen":
                eintragHinzufügenWindow();
                break;

            case "vokabelliste zurück":
                if(vf != null) vf.dispose();
                startMenuWindow();
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
                if (pcf != null) pcf.dispose();
                startMenuWindow();
                break;

            case "statsBackToMenu": // nicht hier geschlossen bzw. warum geht es nicht, wird es hier nicht auch schon gschlossen
                if (stf != null) stf.dispose();
                startMenuWindow();
                break;

            case "eintragSpeichern":
                handleEintragHinzufügen();
                break;

            case "eintragAbbrechen":
                if (ehf != null) ehf.dispose();
                break;

            case "Speichern": // nötig?
                handleSpeichern();
                break;

            case "Laden": // nötig?
                handleLaden();
                break;

            case "hangman spielen": // nötig?
                hangmanWindow();
                break;

            case "rate":
                handleHangmanGuess();
                break;

            case "neuesSpiel":
                startNeuesHangmanSpiel();
                break;

            case "vokabelliste Eintrag bearbeiten":
                handleEintragBearbeiten();
                break;

            case "vokabelliste Eintrag löschen":
                handleEintragLöschen();
                break;
        }
    }

    private void handleLogin() {
        String mail = lp.getMail();
        String pwd = lp.getPwd();

        if(mailPwd.getLoginInfo().containsKey(mail)) {
            if (mailPwd.getLoginInfo().get(mail).equals(pwd)) {
                currentUser = mail;
                lf.dispose();
                startMenuWindow();
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
        if (!mail.contains("@") || !mail.toLowerCase().contains("tgm")) {
            rp.invalidMail();
            return;
        }
        if (!pwd.equals(pwdR)) {
            rp.pwdMismatch();
            return;
        }
        if (mailPwd.getLoginInfo().containsKey(mail)) {
            rp.userExists();
            return;
        }

        mailPwd.addUser(mail, pwd);
        rp.successMsg();
    }

    private void handlePwdChange() {
        String oldP = pcp.getOldPwd();
        String newP = pcp.getNewPwd();
        String confP = pcp.getConfirmPwd();

        if (oldP.isBlank() || newP.isBlank() || confP.isBlank()) {
            pcp.allFields();
            return;
        }
        if (!mailPwd.getLoginInfo().get(currentUser).equals(oldP)) {
            pcp.wrongOldPwd();
            return;
        }
        if(oldP.equals(newP)) {
            pcp.noChangePwd();
            return;
        }
        if (!newP.equals(confP)) {
            pcp.pwdMismatch(); return;
        }

        mailPwd.changePwd(currentUser, newP);
        pcp.successMsg();
    }

    private void handleStartTrainer() {
        if(!vListeGeladen) {
            JOptionPane.showMessageDialog(smf, "Bitte zuerst eine Vokabelliste im Menü, unter Vokabelliste, laden.", "Keine Vokabelliste", JOptionPane.WARNING_MESSAGE); // Label von Vokabelliste bearbeiten zu nur Vokabelliste ändern?
            return;
        }
        if(!wortTrainer.hatEintraege()) {
            JOptionPane.showMessageDialog(smf, "Bitte zuerst Einträge zur Vokabelliste im Menü, unter Vokabelliste, hinzufügen.", "Keine Vokabelliste", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(smf != null) smf.dispose();
        //if(stf != null) stf.dispose();
        trainerWindow();
    }

    private void handleStartHangman() {
        if(!vListeGeladen) {
            JOptionPane.showMessageDialog(smf, "Bitte zuerst eine Vokabelliste im Menü, unter Vokabelliste, laden.", "Keine Vokabelliste", JOptionPane.WARNING_MESSAGE); // Label von Vokabelliste bearbeiten zu nur Vokabelliste ändern?
            return;
        }
        if(!wortTrainer.hatEintraege()) {
            JOptionPane.showMessageDialog(smf, "Bitte zuerst Einträge zur Vokabelliste im Menü, unter Vokabelliste, hinzufügen.", "Keine Vokabelliste", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (smf != null) smf.dispose();
        hangmanWindow();
    }

    private void handlevListeLaden() {
        JFileChooser fc = new JFileChooser();
        if(fc.showOpenDialog(vf) == JFileChooser.APPROVE_OPTION){
            try{
                WortEintrag[] geladen = wortSpeicher.laden(fc.getSelectedFile().getAbsolutePath());
                wortTrainer.setEintraege(geladen);
                wortTrainer.resetStatistik();
                vListeGeladen = true;
                vp.setListeGeladen(true, geladen.length);

                if(geladen.length == 0) { // Was, wenn keine Einträge hinzugefügt und Spiel starten drücken? Rein laden raus rein Vliste noch da?
                    JOptionPane.showMessageDialog(vf, "Die Liste enthält keine Einträge!\nBitte fügen Sie welche hinzu!", "Leere Liste", JOptionPane.WARNING_MESSAGE);
                }
                vp.updateEintraegeAnzeige(geladen);
            }catch(IOException e){
                JOptionPane.showMessageDialog(vf, "Fehler beim Laden der Datei!", "Fehler Datei", JOptionPane.ERROR_MESSAGE);
                vListeGeladen = false;
                vp.setListeGeladen(false, 0);
            }
        }
    }

    private void handlevListeSpeichern() {
        if(!vListeGeladen) {
            JOptionPane.showMessageDialog(vf, "Keine Vokabelliste geladen!", "Keine Vokabelliste", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fc = new JFileChooser();
        if(fc.showSaveDialog(vf) == JFileChooser.APPROVE_OPTION) {
            try{
                wortSpeicher.speichern(fc.getSelectedFile().getAbsolutePath(), wortTrainer.getEintraege());
                JOptionPane.showMessageDialog(vf, "Vokabelliste wurde erfolgreich gespeichert!", "Datei speichern", JOptionPane.INFORMATION_MESSAGE);
            }catch(IOException e) {
                JOptionPane.showMessageDialog(vf, "Fehler beim Speichern!", "Fehler Datei", JOptionPane.ERROR_MESSAGE);
            }
        }
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
        schonGeprueft = true;
    }

    private void handleNächsteFrage() {
        if(!schonGeprueft && wortTrainer.getAktuellerEintrag() != null) {
            String eingabe = tp.getAntwortEingabe();
            if(!eingabe.trim().isEmpty()) {
                wortTrainer.prüfen(eingabe);
                updateStatistikLabel();
            }
        }

        schonGeprueft = false; // zurücksetzen

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

        // Anzahl an Einträgen aktualisieren
        if(vp != null && vListeGeladen) {
            vp.setListeGeladen(true, wortTrainer.getEintraege().length);
            vp.updateEintraegeAnzeige(wortTrainer.getEintraege());
        }
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
        vListeGeladen = false;
        schonGeprueft = false; // sicherheitshalber
        // notwendig, weil ja sowieso geschlossen?
        if (smf != null) smf.dispose();
        if (tf != null) tf.dispose();
        if (pcf != null) pcf.dispose();
        if (vf != null) vf.dispose();
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
        smp = new StartMenuPanel(currentUser, this);
        smf = new StartMenuFrame(smp);
    }

    private void trainerWindow() {
        tp = new TrainerPanel(this);
        tf = new TrainerFrame(tp);
        startNeuenTrainer();
    }

    private void startNeuenTrainer() {
        wortTrainer.resetStatistik();
        wortTrainer.setEintraege(wortTrainer.getEintraege());
        schonGeprueft = false;
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
        if(lf != null) lf.dispose();
        rp = new RegistrationPanel(this);
        rf = new RegistrationFrame(rp);
    }

    private void pwdChangeWindow() {
        if(smf != null) smf.dispose();
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

    private void vListeEditWindow() {
        if(smf != null) smf.dispose();
        vp = new VokabelListePanel(this);
        vf = new VokabelListeFrame(vp);

        if(vListeGeladen) vp.setListeGeladen(true, wortTrainer.getEintraege().length);
    }

    private void handleEintragBearbeiten() {
        int index = vp.getSelectedIndex();
        if (index < 0) return;

        WortEintrag[] eintraege = wortTrainer.getEintraege();
        WortEintrag eintrag = eintraege[index];

        // Dialog mit aktuellen Werten
        String neueFrage = JOptionPane.showInputDialog(vf, "Frage:", eintrag.getFrage());
        if (neueFrage == null || neueFrage.isBlank()) return;

        String neueAntwort = JOptionPane.showInputDialog(vf, "Antwort:", eintrag.getAntwort());
        if (neueAntwort == null || neueAntwort.isBlank()) return;

        // Eintrag aktualisieren
        if (eintrag instanceof TextEintrag) {
            eintraege[index] = new TextEintrag(neueFrage, neueAntwort);
        } else {
            eintraege[index] = new BildEintrag(neueFrage, neueAntwort);
        }

        wortTrainer.setEintraege(eintraege);
        vp.updateEintraegeAnzeige(eintraege);
        vp.setListeGeladen(true, eintraege.length);
    }

    private void handleEintragLöschen() {
        int index = vp.getSelectedIndex();
        if (index < 0) return;

        int confirm = JOptionPane.showConfirmDialog(
                vf,
                "Möchten Sie diesen Eintrag wirklich löschen?",
                "Eintrag löschen",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        WortEintrag[] alte = wortTrainer.getEintraege();
        WortEintrag[] neue = new WortEintrag[alte.length - 1];

        // Alle außer den gelöschten kopieren
        int neuerIndex = 0;
        for (int i = 0; i < alte.length; i++) {
            if (i != index) {
                neue[neuerIndex++] = alte[i];
            }
        }

        wortTrainer.setEintraege(neue);
        vp.updateEintraegeAnzeige(neue);
        vp.setListeGeladen(true, neue.length);
        vp.clearSelection();
    }

    public static void main(String[] args) {
        new Controller();
    }
}