package model;


public abstract class WortEintrag {
    private String antwort;

    public WortEintrag(String antwort) {
        setAntwort(antwort);
    }

    public String getAntwort() {
        return antwort;
    }

    public void setAntwort(String antwort) {
        if (antwort == null || antwort.trim().isEmpty()) {
            throw new IllegalArgumentException("Die Antwort darf nicht leer sein!");
        }
        this.antwort = antwort;
    }

    public abstract String getFrage();
    public abstract String getTyp();
}