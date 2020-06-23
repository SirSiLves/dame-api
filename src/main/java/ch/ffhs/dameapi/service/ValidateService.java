package ch.ffhs.dameapi.service;


import ch.ffhs.dameapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class ValidateService {

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;


    private static final Map<Integer, String> RULE_TEXTS = new HashMap<>() {
        {
            put(1, "Valid");
            put(2, "Current or target field is not valid");
            put(3, "On the target field is already a stone");
            put(4, "It's other player's turn");
            put(5, "This is not diagonal neighboring field");
            put(6, "Wrong direction. This move is only allowed as a queen");
            put(7, "There is no stone between");
            put(8, "You try to kill your own stone");
            put(9, "You have to do a hop move");
            put(10, "There is no stone on the current field");
            put(11, "Only brown field are allowed");
        }
    };

    /**
     * Validate if move from one field to another is valid with the game rules.
     * For example it is only allowed from a brown field to another brown field directly
     * connected to move. If a move is NOT valid, the violated rule will be stored as String
     * directly in the Validity object. In one game object exists only one Validity object.
     * That's why the method has always to catch again the right validity object
     * With the declaration of the move-type can be done the right validation.
     *
     *
     * @param currentField field object from where the move starts
     * @param targetField  field object from where the move ends
     * @param typeOfMove   NORMAL, HOP, HOPNOMESSAGE
     * --> Error Handling by general Exception Handling
     */
    public void validateMove(Field currentField, Field targetField, TypeOfMove typeOfMove) {
        Validity validity = this.getValidity();
        PossibleHopMoves possibleHopMoves = this.getPossibleHopMoves();

        //define intervals
        int boardSize = boardService.getFieldMatrix().length;
        int lowerInterval = 0;
        int higherInterval = 0;

        if (typeOfMove == TypeOfMove.NORMAL && boardSize == 8) {
            lowerInterval = 7;
            higherInterval = 9;
            this.isHopMoveRequired();

        } else if ((typeOfMove == TypeOfMove.HOP || typeOfMove == TypeOfMove.HOPNOMESSAGE)
                && boardSize == 8) {
            lowerInterval = 14;
            higherInterval = 18;

        } else if (typeOfMove == TypeOfMove.NORMAL && boardSize == 10) {
            lowerInterval = 9;
            higherInterval = 11;
            this.isHopMoveRequired();

        } else if ((typeOfMove == TypeOfMove.HOP || typeOfMove == TypeOfMove.HOPNOMESSAGE)
                && boardSize == 10) {
            lowerInterval = 18;
            higherInterval = 22;

        } else {
            System.out.println("Validate Move - setting the interval - something went wrong");
        }


        //start rule-set definition
        if (currentField == null || targetField == null) {
            validity.setRuleNumber(2);
            validity.setIsValid(false);

        }
        else if (targetField.getStone() != null && targetField.getStone().getAlive()) {
            validity.setRuleNumber(3);
            validity.setIsValid(false);

        }
        else if (targetField.getFieldColor().equals(Color.SANDYBROWN)) {
            validity.setRuleNumber(5);
            validity.setIsValid(false);

        }
        else if (this.getLastTouched() == currentField.getStone().getStoneColor()) {
            validity.setRuleNumber(4);
            validity.setIsValid(false);

        } else if ((typeOfMove == TypeOfMove.HOP || typeOfMove == TypeOfMove.HOPNOMESSAGE) &&
                (this.getHoppedField(currentField, targetField)).getStone() == null) {
            validity.setRuleNumber(7);
            validity.setIsValid(false);

        } else if ((typeOfMove == TypeOfMove.HOP || typeOfMove == TypeOfMove.HOPNOMESSAGE) &&
                (this.getHoppedField(currentField, targetField)).getStone().getStoneColor() ==
                        currentField.getStone().getStoneColor()) {
            validity.setRuleNumber(8);
            validity.setIsValid(false);

        } else if (typeOfMove == TypeOfMove.NORMAL && Objects.requireNonNull(possibleHopMoves).getIsHopeMoveValid()) {
            validity.setRuleNumber(9);
            validity.setIsValid(false);

        } else if (boardSize == 8 || boardSize == 10) {
            validateSimpleMove(currentField, targetField, lowerInterval, higherInterval);

        } else {
            validity.setRuleNumber(1);
            validity.setIsValid(true);
        }

        if (typeOfMove == TypeOfMove.HOPNOMESSAGE) {
            validity.setValidationMessage(null);
        } else if (typeOfMove == TypeOfMove.NORMAL || typeOfMove == TypeOfMove.HOP) {
            validity.setValidationMessage(RULE_TEXTS.get(validity.getRuleNumber()));
        }
    }

    /**
     * This method is generally used by JUnitTests -> ValidateServiceTest
     * To get a field object by a field number on specific game board.
     *
     *
     * @param currentFieldNumber fieldNumber from where the move starts
     * @param targetFieldNumber  fieldNumber from where the move ends
     * @param typeOfMove         NORMAL, HOP, HOPNOMESSAGE
     * --> Error Handling by general Exception Handling
     */
    public void validateMove(int currentFieldNumber, int targetFieldNumber, TypeOfMove typeOfMove) {

        Field currentField = boardService.getField(currentFieldNumber);
        Field targetField = boardService.getField(targetFieldNumber);

        validateMove(currentField, targetField, typeOfMove);
    }

    /**
     * Validates only simple moves, where fields are directly attached together.
     * If the numbers of the current and target field are to far away, the move won't be valid.
     * The intervals are needed for the specific game size 8/10.
     *
     *
     * @param currentField   field object from where the move starts
     * @param targetField    field object from where the move ends
     * @param lowerInterval  NORMAL, HOP, HOPNOMESSAGE
     * @param higherInterval NORMAL, HOP, HOPNOMESSAGE
     * --> Error Handling by general Exception Handling
     */
    private void validateSimpleMove(Field currentField, Field targetField,
                                    int lowerInterval, int higherInterval) {

        Validity validity = this.getValidity();

        int difference = currentField.getFieldNumber() - targetField.getFieldNumber();

        if (difference == -lowerInterval || difference == -higherInterval) {
            if (currentField.getStone().getQueen()) {
                validity.setRuleNumber(1);
                validity.setIsValid(true);
            } else {
                if (currentField.getStone().getStoneColor().equals(Color.BLACK)
                        && targetField.getFieldColor().equals(Color.BROWN)) {
                    validity.setRuleNumber(1);
                    validity.setIsValid(true);
                } else {
                    validity.setRuleNumber(6);
                    validity.setIsValid(false);
                }
            }

        } else if (difference == lowerInterval || difference == higherInterval) {
            if (currentField.getStone().getQueen()) {
                validity.setRuleNumber(1);
                validity.setIsValid(true);
            } else {
                if (currentField.getStone().getStoneColor().equals(Color.WHITE)
                        && targetField.getFieldColor().equals(Color.BROWN)) {
                    validity.setRuleNumber(1);
                    validity.setIsValid(true);
                } else {
                    validity.setRuleNumber(6);
                    validity.setIsValid(false);
                }
            }

        } else {
            validity.setRuleNumber(5);
            validity.setIsValid(false);
        }
    }

    /**
     * Validate if after a simple move or also hop, a hop move has to be done.
     * If yes look around for every possible fields on which can be hopped and
     * add the field to possibleHopMoves object.
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public void isHopMoveRequired() {
        Board tempBoard = boardService.getBoard();
        Color lastTouched = this.getLastTouched();
        int boardSize = tempBoard.getFieldMatrix().length;
        PossibleHopMoves possibleHopMoves = gameService.getGame().getPossibleHopMoves();

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {

                Field tempField = tempBoard.getSingleFieldFromMatrix(y, x); // 17

                if (tempField.getStone() != null && tempField.getStone().getAlive()
                        && tempField.getStone().getStoneColor() != lastTouched
                        && tempField.getFieldColor().equals(Color.BROWN)) {

                    int[] testNumbers = {-22, -18, -14, 14, 18, 22};
                    for (int testNumber : testNumbers) {
                        int tempTargetFieldNumber = tempField.getFieldNumber() + testNumber; // 17 + -14 -> 3
                        Field tempTargetField = boardService.getField(tempTargetFieldNumber);

                        if (boardSize == 8 && tempTargetFieldNumber < 64 && tempTargetFieldNumber > 0) {

                            if (tempTargetField.getFieldColor().equals(Color.BROWN)) {
                                this.validateMove(tempField, tempTargetField, TypeOfMove.HOPNOMESSAGE);
                                Boolean required = this.getValidity().getIsValid();

                                if (required) {
                                    possibleHopMoves.setIsHopeMoveValid(true);
                                    possibleHopMoves.addPossibleHopMoveFields(tempField, tempTargetField);
                                }
                            }

                        } else if (boardSize == 10 && tempTargetFieldNumber < 100 && tempTargetFieldNumber > 0) {
                            if (tempTargetField.getFieldColor().equals(Color.BROWN)) {
                                this.validateMove(tempField, tempTargetField, TypeOfMove.HOPNOMESSAGE);
                                Boolean required = this.getValidity().getIsValid();

                                if (required) {
                                    possibleHopMoves.setIsHopeMoveValid(true);
                                    possibleHopMoves.addPossibleHopMoveFields(tempField, tempTargetField);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Get from the Validity object the color of the last moved stone
     *
     *
     * @return return Color from validity object
     * --> Error Handling by general Exception Handling
     */
    public Color getLastTouched() {
        return this.getValidity().getLastTouched();
    }

    /**
     * Set from the Validity object the color of the last moved stone
     *
     *
     * @param lastTouched
     * --> Error Handling by general Exception Handling
     */
    public void setLastTouched(Color lastTouched) {
        this.getValidity().setLastTouched(lastTouched);
    }

    public Validity getValidity() {
        return gameService.getGame().getValidity();
    }

    public PossibleHopMoves getPossibleHopMoves() {
        return gameService.getGame().getPossibleHopMoves();
    }

    /**
     * Get with currentField & targetField the over hopped field
     *
     *
     * @param currentField from where the hop starts
     * @param targetField  from where the hop ends
     * @return Field object with the hopped fieldNumber
     * --> Error Handling by general Exception Handling
     */
    public Field getHoppedField(Field currentField, Field targetField) {
        int hoppedFieldNumber = currentField.getFieldNumber() +
                ((targetField.getFieldNumber() - currentField.getFieldNumber()) / 2);
        return boardService.getField(hoppedFieldNumber);
    }

    /**
     * Check if a stone reached the top border or bottom border.
     * If he did, transfer the stone to a queen stone.
     *
     *
     * @param targetField where the stone was moved to
     * --> Error Handling by general Exception Handling
     */
    public void validateQueen(Field targetField) {
        int boardSize = boardService.getFieldMatrix().length;
        int fieldNumber = targetField.getFieldNumber();

        if ((targetField.getStone() != null) && (!targetField.getStone().getQueen()) &&
                ((fieldNumber < boardSize) || (fieldNumber >= (boardSize * boardSize - boardSize)))) {
            targetField.getStone().setQueen(true);
        }
    }

    /**
     * Validate if a move could be done. If not
     * the other color wins automatically.
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public void validateDilemma() {

        Board tempBoard = boardService.getBoard();
        boolean movesPossible = false;
        int boardSize = tempBoard.getFieldMatrix().length;
        int fieldCount = boardSize * boardSize;
        Color lastTouched = this.getValidity().getLastTouched();

        //go through all fields
        for (int i = 0; i < fieldCount; i++) {
            Field tempField = boardService.getField(i);

            if (tempField.getStone() != null
                    && !tempField.getStone().getStoneColor().equals(lastTouched)
                    && !tempField.getFieldColor().equals(Color.SANDYBROWN)) {

                for (int j = 0; j < fieldCount; j++) {
                    Field tempTargetField = boardService.getField(j);

                    if (!tempTargetField.getFieldColor().equals(Color.SANDYBROWN)
                            && tempTargetField.getStone() == null) {

                        //check if a normal move is possible
                        if (!movesPossible) {
                            this.validateMove(tempField, tempTargetField, TypeOfMove.NORMAL);
                            if (this.getValidity().getIsValid()) movesPossible = true;
                        } else break; //exit for-loop

                        //check if a hop is possible
                        if (!movesPossible) {
                            this.validateMove(tempField, tempTargetField, TypeOfMove.HOP);
                            if (this.getValidity().getIsValid()) movesPossible = true;
                        } else break; //exit for-loop
                    }
                }
            }
            if (movesPossible) break; //exit for-loop
        }

        //no move is possible, set winner
        if (!movesPossible) {
            if (this.getValidity().getLastTouched().equals(Color.WHITE)) tempBoard.setWinnerColor(Color.WHITE);
            else tempBoard.setWinnerColor(Color.BLACK);

            //Set everything is fine, we have a winner
            this.getValidity().setIsValid(true);
            this.getValidity().setRuleNumber(1);
            this.getValidity().setValidationMessage(RULE_TEXTS.get(1));
        }
    }
}
