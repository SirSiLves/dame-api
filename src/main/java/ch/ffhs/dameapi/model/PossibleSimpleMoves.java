package ch.ffhs.dameapi.model;

import java.util.ArrayList;


public class PossibleSimpleMoves {

    private Boolean isSimpleMoveValid;
    private final ArrayList<Moves> possibleSimpleMoveFields;

    public PossibleSimpleMoves() {
        isSimpleMoveValid = false;
        possibleSimpleMoveFields = new ArrayList<>();
    }


    public Boolean getIsSimpleMoveValid() {
        return isSimpleMoveValid;
    }

    public void setIsSimpleMoveValid(Boolean valid) {
        isSimpleMoveValid = valid;
    }

    public ArrayList<Moves> getPossibleSimpleMoveFields() {
        return possibleSimpleMoveFields;
    }

    public void addPossibleSimpleMoveFields(Field sourceField, Field targetField) {
        Moves possibleMove = new Moves(sourceField, targetField);
        possibleSimpleMoveFields.add(possibleMove);
    }

    public void resetPossibleSimpleMoveField(){
        possibleSimpleMoveFields.clear();
        this.setIsSimpleMoveValid(false);
    }
}

