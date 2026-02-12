package model;

public class BildEintrag extends WortEintrag {
    private String url;

    public BildEintrag(String url, String antwort) {
        super(antwort);
        this.url = url;
    }

    @Override
    public String getFrage() {
        return url;
    }

    @Override
    public String getTyp() {
        return "BILD";
    }
}