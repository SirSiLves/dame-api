package ch.ffhs.dameapi.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Board {

    //[y][x] first value is always the horizontal, second vertical
    private Field[][] fieldMatrix;
    private final ArrayList<Stone> stoneArrayList;
    private Color winnerColor;
    private final HashMap<Integer, HashMap<String, HistoryMove>> moveHistory; // 1,2,3,4.. <StoneID, lastField>

    public Board() {
        fieldMatrix = null;
        stoneArrayList = new ArrayList<>();
        winnerColor = null;
        moveHistory = new HashMap<>();
    }


    /**
     * Add affected fields and stone after each move to a HashMap.
     *
     *
     * @param currentField field from where the move started
     * @param targetField field where the move ended
     * @param hoppedField field which was hopped over
     * --> Error Handling by general Exception Handling
     */
    public void addMoveToHistory(Field currentField, Field targetField, Field hoppedField) {
        HashMap<String, HistoryMove> movedFields = new HashMap<>();
        movedFields.put("lastCurrentField", new HistoryMove(currentField));
        movedFields.put("lastTargetField", new HistoryMove(targetField));
        movedFields.put("lastHoppedField", new HistoryMove(hoppedField));

        moveHistory.put(moveHistory.size(), movedFields);
    }

    /**
     * Each call removes the last history entry from the HashMap.
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public void removeLastHistoryEntry(){
        Iterator<Map.Entry<Integer, HashMap<String, HistoryMove>>> itr = moveHistory.entrySet().iterator();
        while(itr.hasNext()) {
            itr.next();
        }
        itr.remove();
    }

    public HashMap<Integer, HashMap<String, HistoryMove>> getMoveHistory() {
        return moveHistory;
    }

    public Field[][] getFieldMatrix() {
        this.checkWinner();
        return fieldMatrix;
    }

    public void setFieldMatrix(Field[][] fieldMatrix) {
        this.fieldMatrix = fieldMatrix;
    }

    public void setSingleFieldInMatrix(int y, int x, Field field) {
        fieldMatrix[y][x] = field;
    }

    public Field getSingleFieldFromMatrix(int y, int x) {
        return fieldMatrix[y][x];
    }

    public void addStone(Stone stone) {
        stoneArrayList.add(stone);
    }

    public ArrayList<Stone> getStoneArrayList() {
        return stoneArrayList;
    }

    /**
     * checks if a player won, which color it is and set it to the
     * field Color
     *
     *
     * --> Error Handling by general Exception Handling
     */
    private void checkWinner() {
        boolean blackAlive = false;
        boolean whiteAlive = false;

        if (getStoneArrayList().size() > 0) {
            for (Stone stone : getStoneArrayList()) {
                if (!blackAlive && stone.getStoneColor().equals(Color.BLACK)) {
                    if (stone.getAlive()) blackAlive = true;
                }
                if (!whiteAlive && stone.getStoneColor().equals(Color.WHITE)) {
                    if (stone.getAlive()) whiteAlive = true;
                }
            }

            if (!blackAlive) this.setWinnerColor(Color.WHITE);
            else if (!whiteAlive) this.setWinnerColor(Color.BLACK);
        }
    }

    public void setWinnerColor(Color stoneColor){
        winnerColor = stoneColor;
    }

    public Color getWinnerColor() {
        return winnerColor;
    }
}
