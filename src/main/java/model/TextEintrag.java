package model;

public class TextEintrag extends WortEintrag {
    private String frageText;

    public TextEintrag(String frageText, String antwort) {
        super(antwort);
        this.frageText = frageText;
    }

    @Override
    public String getFrage() {
        return frageText;
    }

    @Override
    public String getTyp() {
        return "TEXT";
    }
}