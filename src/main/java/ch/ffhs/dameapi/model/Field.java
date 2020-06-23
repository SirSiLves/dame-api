package ch.ffhs.dameapi.model;


public class Field {

    private final Color fieldColor;
    private final int fieldNumber;
    private Stone stone;

    public Field(int fieldNumber, Color fieldColor) {
        this.fieldNumber = fieldNumber;
        this.fieldColor = fieldColor;
        this.stone = null;
    }


    public Color getFieldColor() {
        return fieldColor;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }
}
