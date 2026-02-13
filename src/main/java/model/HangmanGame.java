package model;

public class HangmanGame {
    private WortTrainer wortTrainer;
    private String wort;
    private String erratenesWort;
    private String falscheBuchstaben;
    private int niveau;
    private int maxVersuche;
    private int aktuelleVersuche;

    public void setWortTrainer(WortTrainer wortTrainer){
        this.wortTrainer = wortTrainer;
    }

    public void neuesSpiel(int niveau) {
        this.niveau = niveau;
        this.wort = zufallsWort().toLowerCase();
        this.erratenesWort = "";
        for(int i = 0; i < wort.length(); i++) {
            if(wort.charAt(i) == ' '){
                erratenesWort += " ";
            }else {
                erratenesWort += "_";
            }
        }
        this.falscheBuchstaben = "";
        this.aktuelleVersuche = 0;

        int anzLeerzeichen = 0;
        for(int i = 0; i < wort.length(); i++){
            if(wort.charAt(i) == ' '){
                anzLeerzeichen++;
            }
        }

        int tatsWLaenge = wort.length() - anzLeerzeichen;
        if(niveau == 1) {
            maxVersuche = tatsWLaenge + 10;
        } else if(niveau == 2) {
            maxVersuche = tatsWLaenge + 5;
        } else {
            maxVersuche = tatsWLaenge;
        }
    }


    private String zufallsWort() {
        if(wortTrainer != null && wortTrainer.hatEintraege()){
            WortEintrag[] eintraege = wortTrainer.getEintraege();
            int random = (int)(Math.random() * eintraege.length);
            return eintraege[random].getAntwort();
        }
        throw new IllegalStateException("Keine Wörter für Hangman vorhanden!");
    }

    public boolean rateBuchstabe(char buchstabe) {
        buchstabe = Character.toLowerCase(buchstabe);
        aktuelleVersuche++;

        boolean treffer = false;
        String neuesErratenesWort = "";

        for(int i = 0; i < wort.length(); i++) {
            if(wort.charAt(i) == buchstabe) {
                neuesErratenesWort += buchstabe;
                treffer = true;
            } else {
                neuesErratenesWort += erratenesWort.charAt(i);
            }
        }

        erratenesWort = neuesErratenesWort;

        if(!treffer && falscheBuchstaben.indexOf(buchstabe) == -1) {
            if(!falscheBuchstaben.equals("")) {
                falscheBuchstaben += ", ";
            }
            falscheBuchstaben += buchstabe;
        }

        return treffer;
    }

    public boolean istGewonnen() {
        return erratenesWort.equals(wort);
    }

    public boolean istVerloren() {
        return aktuelleVersuche >= maxVersuche && !istGewonnen();
    }

    public boolean buchstabeSchonVerwendet(char buchstabe) {
        buchstabe = Character.toLowerCase(buchstabe);
        return erratenesWort.indexOf(buchstabe) != -1 ||
                falscheBuchstaben.indexOf(buchstabe) != -1;
    }

    public String getErratenesWort() {
        return erratenesWort;
    }

    public String getWort() {
        return wort;
    }

    public String getFalscheBuchstaben() {
        if(falscheBuchstaben.isEmpty()) {
            return "Keine";
        }
        return falscheBuchstaben;
    }

    public int getVerbleibendeVersuche() {
        return maxVersuche - aktuelleVersuche;
    }

    public int getMaxVersuche() {
        return maxVersuche;
    }

    public int getAktuelleVersuche() {
        return aktuelleVersuche;
    }
}