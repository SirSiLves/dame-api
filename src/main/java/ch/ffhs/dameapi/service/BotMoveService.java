package ch.ffhs.dameapi.service;


import ch.ffhs.dameapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class BotMoveService {

    @Autowired
    private ValidateService validateService;

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;


    /**
     * Get all possible bot move fields and
     * return a random target move field
     *
     *
     * @return return the next bot move
     * --> Error Handling by general Exception Handling
     */
    public Moves getBotMove() {

        // to not end in a loop. See MoveService around Line 80
        gameService.getGame().setBotStatus(false);

        //create & access ArrayList with possible hop moves
        validateService.isHopMoveRequired();

        ArrayList<Moves> possibleHopMoves = gameService.getGame().getPossibleHopMoves().getPossibleHopMoveFields();

        if (possibleHopMoves.size() > 0) {
            // to do a random move, it choose on of the entry in the ArrayList randomly
            int randomNumber = createRandomNumber(possibleHopMoves.size());

            //get the random source- and targetField for the hop move and do it
            return possibleHopMoves.get(randomNumber);
        }
        else {
            //create & access ArrayList with possible simple moves
            createPossibleSimpleMoves();
            ArrayList<Moves> possibleSimpleMoves = gameService.getGame().getPossibleSimpleMoves().getPossibleSimpleMoveFields();

            if (possibleSimpleMoves.size() > 0) {
                // to do a random move, it choose on of the entry in the ArrayList randomly
                int randomNumber = createRandomNumber(possibleSimpleMoves.size());

                //get the random source- and targetField for the simple move and do it
                return possibleSimpleMoves.get(randomNumber);
            }
        }
            //There is something wrong. The bot didn't found a valid move -> check for dilemma
            validateService.validateDilemma();
            return null;
    }

    /**
     * Validate if a simple move can be done.
     * If yes look around for every possible fields on which can be moved and
     * add the field to possibleSimpleMoves ArrayList.
     *
     * --> Error Handling by general Exception Handling
     */
    private void createPossibleSimpleMoves() {
        Board tempBoard = boardService.getBoard();
        Color lastTouched = validateService.getLastTouched();
        int boardSize = tempBoard.getFieldMatrix().length;
        PossibleSimpleMoves possibleSimpleMoves = gameService.getGame().getPossibleSimpleMoves();
        possibleSimpleMoves.resetPossibleSimpleMoveField();

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {

                Field tempField = tempBoard.getSingleFieldFromMatrix(y, x); // 17

                if (tempField.getStone() != null && tempField.getStone().getAlive()
                        && !tempField.getStone().getStoneColor().equals(lastTouched)
                        && !tempField.getFieldColor().equals(Color.SANDYBROWN)) {


                    int[] testNumbers = {-11, -9, -7, 7, 9, 11};
                    for (int testNumber : testNumbers) {
                        int tempTargetFieldNumber = tempField.getFieldNumber() + testNumber; // 17 + -14 -> 3
                        Field tempTargetField = boardService.getField(tempTargetFieldNumber);
                        validateService.validateMove(tempField, tempTargetField, TypeOfMove.NORMAL);

                        if (validateService.getValidity().getIsValid()) {
                            possibleSimpleMoves.setIsSimpleMoveValid(true);
                            possibleSimpleMoves.addPossibleSimpleMoveFields(tempField, tempTargetField);
                        }
                    }
                }
            }
        }
    }

    /**
     * creates a random int number, within a specific range from 1 to max
     *
     * @param max the maximum hight of the range
     *            <p>
     *            --> Error Handling by general Exception Handling
     */
    private int createRandomNumber(Integer max) {
        int min = 1;
        int range = max - min + 1;
        int random = 0;

        // generate random numbers within 1 to 10
        for (int i = 0; i < max; i++) {
            random = (int) (Math.random() * range) + min - 1;
        }
        return random;
    }
}
