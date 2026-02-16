package model;

import java.util.Random;

public class WortTrainer {
    private WortEintrag[] eintraege;
    private int anzahlEintraege;
    private int[] nochNichtGefragt;
    private int verbleibendeAnzahl;
    private int richtig;
    private int falsch;
    private int aktuellerIndex;
    private Random random;

    public WortTrainer() {
        this.eintraege = new WortEintrag[100];
        this.anzahlEintraege = 0;
        this.nochNichtGefragt = new int[100];
        this.verbleibendeAnzahl = 0;
        this.richtig = 0;
        this.falsch = 0;
        this.aktuellerIndex = -1;
        this.random = new Random();
    }

    public void addEintrag(WortEintrag eintrag) {
        if (anzahlEintraege >= eintraege.length) {
            int neueGroesse = eintraege.length == 0 ? 1 : eintraege.length * 2; // speicheroptimierter als +1
            WortEintrag[] neuesArray = new WortEintrag[neueGroesse];
            int[] neueIndizes = new int[neueGroesse];

            for (int i = 0; i < eintraege.length; i++) {
                neuesArray[i] = eintraege[i];
                neueIndizes[i] = nochNichtGefragt[i];
            }
            eintraege = neuesArray;
            nochNichtGefragt = neueIndizes;
        }

        eintraege[anzahlEintraege] = eintrag;
        // Den neuen Index zur "Noch nicht gefragt" Liste hinzufügen
        nochNichtGefragt[verbleibendeAnzahl] = anzahlEintraege;
        verbleibendeAnzahl++;
        anzahlEintraege++;
    }

    public WortEintrag[] getEintraege() {
        WortEintrag[] result = new WortEintrag[anzahlEintraege];
        for (int i = 0; i < anzahlEintraege; i++) {
            result[i] = eintraege[i];
        }
        return result;
    }

    public void setEintraege(WortEintrag[] eintraege) {
        //this.eintraege = eintraege;
        this.eintraege = eintraege.length == 0 ? new WortEintrag[1]: eintraege; // leere Vokabellisten berücksichtigen (in length 0 vliste eintrag --> error --> mit 1 standardplatz wenn legth 0 dann funktionierts
        this.anzahlEintraege = eintraege.length;
        // Index-Array neu dimensionieren
        //this.nochNichtGefragt = new int[anzahlEintraege];
        this.nochNichtGefragt = new int[eintraege.length == 0 ? 1 : eintraege.length];
        resetFragenListe();
    }


    private void resetFragenListe() {
        // Liste mit allen verfügbaren Indizes füllen
        for (int i = 0; i < anzahlEintraege; i++) {
            nochNichtGefragt[i] = i;
        }
        verbleibendeAnzahl = anzahlEintraege;

        // Mischen
        for (int i = verbleibendeAnzahl - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = nochNichtGefragt[i];
            nochNichtGefragt[i] = nochNichtGefragt[j];
            nochNichtGefragt[j] = temp;
        }
    }

    public String getStatistik() {
        return "Richtig: " + richtig + " | Falsch: " + falsch;
    }

    public int getRichtig() {
        return richtig;
    }

    public int getFalsch() {
        return falsch;
    }

    public void resetStatistik() {
        richtig = 0;
        falsch = 0;
    }

    public WortEintrag getAktuellerEintrag() {
        if (aktuellerIndex >= 0 && aktuellerIndex < anzahlEintraege) {
            return eintraege[aktuellerIndex];
        }
        return null;
    }

    public WortEintrag getNächsterEintrag() {
        if (anzahlEintraege == 0) {
            return null;
        }

        // Wenn alle Fragen durch sind, Liste neu mischen
        if (verbleibendeAnzahl <= 0) {
            resetFragenListe();
        }

        // Letztes Element aus dem gemischten Pool nehmen (effizienter als vorne löschen)
        verbleibendeAnzahl--;
        aktuellerIndex = nochNichtGefragt[verbleibendeAnzahl];
        return eintraege[aktuellerIndex];
    }

    public boolean prüfen(String eingabe) {
        if (aktuellerIndex < 0 || aktuellerIndex >= anzahlEintraege) {
            return false;
        }

        if (eintraege[aktuellerIndex].getAntwort().equalsIgnoreCase(eingabe.trim())) {
            richtig++;
            return true;
        } else {
            falsch++;
            return false;
        }
    }

    public boolean hatEintraege() {
        return anzahlEintraege > 0;
    }

    public int getAnzahlNochNichtGefragt() {
        return verbleibendeAnzahl;
    }

    public boolean alleFragenBeantwortet() {
        return verbleibendeAnzahl == 0;
    }
}