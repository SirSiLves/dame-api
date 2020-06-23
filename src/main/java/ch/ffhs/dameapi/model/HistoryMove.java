package ch.ffhs.dameapi.model;


public class HistoryMove {

    private final Field field;
    private final Stone stone;
    private final Boolean wasAQueen;

    public HistoryMove(Field field) {
        this.field = field;

        if(field != null && this.field.getStone() != null){
            this.stone = field.getStone();
            this.wasAQueen = stone.getQueen();
        }
        else {
            this.stone = null;
            this.wasAQueen = null;
        }
    }


    public Field getField() {
        return field;
    }

    public Stone getStone() {
        return stone;
    }

    public Boolean getQueenBefore() {
        return wasAQueen;
    }
}


