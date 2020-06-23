package ch.ffhs.dameapi.model;

import java.util.ArrayList;


public class PossibleHopMoves {

    private Boolean isHopMoveValid;
    private final ArrayList<Moves> possibleHopMoveFields;

    public PossibleHopMoves() {
        isHopMoveValid = false;
        possibleHopMoveFields = new ArrayList<>();
    }


    public Boolean getIsHopeMoveValid() {
        return isHopMoveValid;
    }

    public void setIsHopeMoveValid(Boolean valid) {
        isHopMoveValid = valid;
    }

    public ArrayList<Moves> getPossibleHopMoveFields() {
        return possibleHopMoveFields;
    }

    public void addPossibleHopMoveFields(Field sourceField, Field targetField) {
        boolean alreadyAdded = false;
        for(Moves m : possibleHopMoveFields){
            if(m.getSourceField().getFieldNumber() == sourceField.getFieldNumber()){
                alreadyAdded = true;
                break;
            }
        }

        if(!alreadyAdded){
            Moves possibleMove = new Moves(sourceField, targetField);
            possibleHopMoveFields.add(possibleMove);
        }
    }

    public void resetPossibleHopMoveField(){
        possibleHopMoveFields.clear();
        this.setIsHopeMoveValid(false);
    }
}
