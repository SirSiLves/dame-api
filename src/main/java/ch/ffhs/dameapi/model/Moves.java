package ch.ffhs.dameapi.model;


public class Moves {

    private final Field sourceField;
    private final Field targetField;

    public Moves(Field sourceField, Field targetField) {
        this.sourceField = sourceField;
        this.targetField = targetField;
    }


    public Field getSourceField() {
        return sourceField;
    }

    public Field getTargetField() {
        return targetField;
    }

}
