package ch.ffhs.dameapi.service;


import ch.ffhs.dameapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MoveService {

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ValidateService validateService;

    @Autowired
    private PrintService printService;

    @Autowired
    private BotMoveService botMoveService;


    /**
     * After a successfully validation of a move with type NORMAL,
     * the stone from the currentField to the targetField will be changed.
     * Right afterwards the move will be filled into the moveHistory object
     * with currentField, targetField.
     * In addition check for queen transformation and
     * reset all possible hopped fields in preparation for the next move.
     *
     *
     * @param currentField where the stone was moved from
     * @param targetField  where the stone was moved to
     * @return return the last Validity state after the move
     * --> Error Handling by general Exception Handling
     */
    public Validity doSimpleMove(Field currentField, Field targetField) {

        if (!gameService.getGameStarted()) {
            //Game is NOT initialized
            printService.printGameToConsole(null);
            return null;
        }

        validateService.validateMove(currentField, targetField, TypeOfMove.NORMAL);

        if (validateService.getValidity().getIsValid()) {

            //find the affected fields
            Field tempCurrentField = boardService.getField(currentField.getFieldNumber());
            Field tempTargetField = boardService.getField(targetField.getFieldNumber());

            //move the stone from one field to the other
            Stone stone = currentField.getStone();
            tempTargetField.setStone(stone);
            tempCurrentField.setStone(null);

            //write to history
            boardService.getBoard().addMoveToHistory(tempCurrentField, tempTargetField, null);

            //set last played player
            validateService.setLastTouched(stone.getStoneColor());
            //check and set stone to a queen
            validateService.validateQueen(targetField);

            //reset possible hop fields
            validateService.getPossibleHopMoves().resetPossibleHopMoveField();
            //validate dilemma
            validateService.validateDilemma();

            printService.printGameToConsole(ConsolePrintState.MOVE);
            printService.printTriggeredSessionId();
        }

        return validateService.getValidity();
    }


    /**
     * After a successfully validation of a move with type HOP,
     * the stone from the currentField to the targetField will be changed.
     * The over hopped stone, will be declared as killed.
     * Right afterwards the move will be filled into the moveHistory object
     * with currentField, targetField, hoppedField and hoppedStone.
     * In addition check for queen transformation and
     * reset all possible hopped fields in preparation for the next move.
     * Repeat all these steps, if there are more stones to over hop.
     *
     *
     * @param currentField where the stone was moved from
     * @param targetField  where the stone was moved to
     * @return return the last Validity state after the move
     * --> Error Handling by general Exception Handling
     */
    public Validity doHopMove(Field currentField, Field targetField) {

        validateService.validateMove(currentField, targetField, TypeOfMove.HOP);

        if (validateService.getValidity().getIsValid()) {

            //find the affected fields
            Field tempCurrentField = boardService.getField(currentField.getFieldNumber());
            Field tempTargetField = boardService.getField(targetField.getFieldNumber());

            //move the stone from one field to the other
            Stone stone = currentField.getStone();
            tempTargetField.setStone(stone);
            tempCurrentField.setStone(null);

            //set state for a hopped field
            Field hoppedField = validateService.getHoppedField(currentField, targetField);

            //write to history
            boardService.getBoard().addMoveToHistory(tempCurrentField, tempTargetField, hoppedField);

            //kill the hoped stone
            hoppedField.getStone().setAlive(false);
            hoppedField.setStone(null);

            //check for other hop moves with currently stone
            validateService.isHopMoveRequired();
            boolean hopsDone = true;
            PossibleHopMoves possibleHopMoves = gameService.getGame().getPossibleHopMoves();
            if (possibleHopMoves.getIsHopeMoveValid()) {

                //check possible hop field
                if (possibleHopMoves.getPossibleHopMoveFields().size() > 0) {
                    int i = 0;
                    //check if hop is possible for the currently stone. If not check next possible field
                    while (i < possibleHopMoves.getPossibleHopMoveFields().size()) {
                        Field field = possibleHopMoves.getPossibleHopMoveFields().get(i).getTargetField();
                        if(tempTargetField.getStone() != null){
                            validateService.validateMove(tempTargetField, field, TypeOfMove.HOPNOMESSAGE);
                            if (validateService.getValidity().getIsValid()) {
                                this.doHopMove(tempTargetField, field);
                                hopsDone = false;
                            }
                        }
                        i++;
                    }
                }
            }


            //all hopes are done
            if (hopsDone) {
                validateService.getValidity().setIsValid(true);
                //set last played player
                validateService.setLastTouched(stone.getStoneColor());
                //reset possible hop fields
                validateService.getPossibleHopMoves().resetPossibleHopMoveField();
                //check and set stone to a queen
                validateService.validateQueen(targetField);
                //validate dilemma
                validateService.validateDilemma();
            }

            printService.printGameToConsole(ConsolePrintState.MOVE);
            printService.printTriggeredSessionId();
        }

        return validateService.getValidity();
    }


    /**
     * Check if the right color has to move. Get possible moves and
     * run them. Reset state for next move.
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public Validity doBotMove() {
        if (gameService.getGame().getBotStatus() &&
                boardService.getBoard().getWinnerColor() == null
                && validateService.getValidity().getIsValid()) {

            Moves botMove = botMoveService.getBotMove();

            //decision if simple or hop move is to be done
            if (Math.abs(botMove.getSourceField().getFieldNumber() - botMove.getTargetField().getFieldNumber()) < 12) {
                doSimpleMove(botMove.getSourceField(), botMove.getTargetField());
            } else {
                doHopMove(botMove.getSourceField(), botMove.getTargetField());
            }

            // recreate initial status
            gameService.getGame().setBotStatus(true);
        }

        return validateService.getValidity();
    }
}
