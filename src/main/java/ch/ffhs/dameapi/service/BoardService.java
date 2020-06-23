package ch.ffhs.dameapi.service;


import ch.ffhs.dameapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class BoardService {

    @Autowired
    private GameService gameService;

    @Autowired
    private PrintService printService;


    /**
     * Simple check if board has been already created
     *
     *
     * @return return the board object with the connected http session id
     * --> Error Handling by general Exception Handling
     */
    public Board getBoard() {
        Game game = gameService.getGame();
        if (game != null) return game.getBoard();
        return null;
    }

    /**
     * Take from the last entry in the moveHistory-HashMap lastCurrentField, lastTargetField,
     * lastHoppedField and hoppedStone. Overwrite the movedStone from the lastTargetField
     * to the lastCurrentField. If a stone has been killed during a hop, he will be revived
     * and placed on the hoppedField. Revert lastTouch otherwise the same player can move twice.
     * If there were more then one hop successively, go through loadPreviousMove() again.
     * If a move has been reverted, delete the reverted move from the history.
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public void loadPreviousMove() {
        HashMap<Integer, HashMap<String, HistoryMove>> moveHistory = this.getBoard().getMoveHistory();
        HashMap<String, HistoryMove> lastAffectedFields = moveHistory.get(moveHistory.size() - 1);

        HistoryMove lastCurrentField = lastAffectedFields.get("lastCurrentField");
        HistoryMove lastTargetField = lastAffectedFields.get("lastTargetField");
        HistoryMove lastHoppedField = lastAffectedFields.get("lastHoppedField");
        Stone movedStone = lastTargetField.getStone();

        if (movedStone.getStoneColor().equals(Color.BLACK)) {
            gameService.getGame().getValidity().setLastTouched(Color.WHITE);
        } else {
            gameService.getGame().getValidity().setLastTouched(Color.BLACK);
        }

        if (lastHoppedField.getField() != null) {
            lastHoppedField.getField().setStone(lastAffectedFields.get("lastHoppedField").getStone());
            lastHoppedField.getStone().setAlive(true);
        }

        //was the stone a queen before
        if (!lastTargetField.getQueenBefore()) {
            movedStone.setQueen(false);
        }

        lastCurrentField.getField().setStone(movedStone);
        lastTargetField.getField().setStone(null);

        this.getBoard().removeLastHistoryEntry();

        //reset last validity
        gameService.getGame().getValidity().setIsValid(true);
        gameService.getGame().getPossibleHopMoves().resetPossibleHopMoveField();

        //was there more than one hop at once? Compare the 2 last entries from the moveHistory
        if (moveHistory.size() > 2 && moveHistory.get(moveHistory.size() - 1).get("lastHoppedField") != null) {
            HistoryMove nextLastTargetField = moveHistory.get(moveHistory.size() - 1).get("lastTargetField");
            if (movedStone.getStoneId() == nextLastTargetField.getStone().getStoneId()) {
                this.loadPreviousMove();
            }
        }

        //reset winner state
        if(this.getBoard().getWinnerColor() != null) this.getBoard().setWinnerColor(null);

        printService.printGameToConsole(ConsolePrintState.REVERT);
        printService.printTriggeredSessionId();
    }

    /**
     * Create custom fieldMatrix[][] on which the game will be played.
     * colorSwitch is needed for the different color ordering on the board.
     *
     *
     * @param gameSize in general an int of 8 / 10
     *                 --> Error Handling by general Exception Handling
     */
    public void createFields(int gameSize) {
        Field[][] fieldMatrix = new Field[gameSize][gameSize];
        Board board = this.getBoard();
        board.setFieldMatrix(fieldMatrix);

        int fieldNumber = 0;
        boolean colorSwitch = false; //for changing the color start
        for (int y = 0; y < gameSize; y++) {

            for (int x = 0; x < gameSize; x++) {
                if (!colorSwitch) {
                    if (x % 2 == 0) {
                        board.setSingleFieldInMatrix(y, x, new Field(fieldNumber, Color.SANDYBROWN));
                    } else {
                        board.setSingleFieldInMatrix(y, x, new Field(fieldNumber, Color.BROWN));
                    }
                } else {
                    if (x % 2 != 0) {
                        board.setSingleFieldInMatrix(y, x, new Field(fieldNumber, Color.SANDYBROWN));
                    } else {
                        board.setSingleFieldInMatrix(y, x, new Field(fieldNumber, Color.BROWN));
                    }
                }
                fieldNumber++;
            }

            colorSwitch = !colorSwitch;
        }
    }

    /**
     * Create custom fieldMatrix[][] on which the game will be played.
     * colorSwitch is needed for the different color ordering on the board.
     *
     *
     * @param fieldNumber private int fieldNumber of the field object
     * @return return the targetField in the fieldMatrix with inputted fieldNumber.
     * --> Error Handling by general Exception Handling
     */
    public Field getField(int fieldNumber) {
        Board board = this.getBoard();
        if (board != null) {
            int fieldSize = board.getFieldMatrix().length;

            for (int y = 0; y < fieldSize; y++) {
                for (int x = 0; x < fieldSize; x++) {
                    if (board.getSingleFieldFromMatrix(y, x).getFieldNumber() == fieldNumber) {
                        return board.getSingleFieldFromMatrix(y, x);
                    }
                }
            }
        }
        return null;
    }

    public Field[][] getFieldMatrix() {
        return getBoard().getFieldMatrix();
    }
}
