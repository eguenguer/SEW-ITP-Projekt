package model;

import java.io.*;

public class WortSpeicher {

    public void speichern(String dateiname, WortEintrag[] liste) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateiname + ".txt"))) {
            for (WortEintrag e : liste) {
                if (e != null) {
                    writer.write(e.getTyp() + ";" + e.getFrage() + ";" + e.getAntwort());
                    writer.newLine();
                }
            }
        }
    }

    public WortEintrag[] laden(String dateiname) throws IOException {
        // Zuerst Zeilen zählen
        int anzahl = 0;
        try (BufferedReader counter = new BufferedReader(new FileReader(dateiname))) {
            while (counter.readLine() != null) {
                anzahl++;
            }
        }

        // Array erstellen und befüllen
        WortEintrag[] geladeneListe = new WortEintrag[anzahl];
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(dateiname))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                String[] teile = zeile.split(";");
                if (teile.length < 3) continue;

                if (teile[0].equals("TEXT")) {
                    geladeneListe[index] = new TextEintrag(teile[1], teile[2]);
                } else if (teile[0].equals("BILD")) {
                    geladeneListe[index] = new BildEintrag(teile[1], teile[2]);
                }
                index++;
            }
        }
        return geladeneListe;
    }
}