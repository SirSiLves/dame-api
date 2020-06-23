package ch.ffhs.dameapi.service;


import ch.ffhs.dameapi.model.Board;
import ch.ffhs.dameapi.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PrintService {

    @Autowired
    private GameService gameService;


    /**
     * Print the current game status to console
     *
     *
     * @param state INITIALIZE / MOVE
     * --> Error Handling by general Exception Handling
     */
    public void printGameToConsole(ConsolePrintState state) {
        StringBuilder printString = new StringBuilder();

        if(gameService.getGameStarted()){
            Board tempBoard = gameService.getGame().getBoard();

            if (state.equals(ConsolePrintState.INITIALIZE)) {
                printString.append("--------------------------------\n")
                        .append("Game ")
                        .append(gameService.getGame().getBoard().getFieldMatrix().length)
                        .append(" x ")
                        .append(gameService.getGame().getBoard().getFieldMatrix().length)
                        .append(" successfully created!")
                        .append("\n--------------------------------\n");

            } else if (state.equals(ConsolePrintState.MOVE)) {
                printString.append("--------------------------------\n")
                        .append("Move successfully executed!")
                        .append("\n--------------------------------\n");
            }
            else if (state.equals(ConsolePrintState.REVERT)) {
                printString.append("--------------------------------\n")
                        .append("Move successfully reverted!")
                        .append("\n--------------------------------\n");
            }

            else{
                printString.append("--------------------------------\n");
            }

            for (int y = 0; y < tempBoard.getFieldMatrix().length; y++) {
                printString.append("|");

                for (int x = 0; x < tempBoard.getFieldMatrix().length; x++) {

                    Field tempField = tempBoard.getSingleFieldFromMatrix(y, x);

                    if (tempField.getStone() != null && tempField.getStone().getAlive()) {
                        printString.append("<>" + "|");
                    } else {
                        if (tempField.getFieldNumber() > 10) printString.append(tempField.getFieldNumber()).append("|");
                        else printString.append(" ").append(tempField.getFieldNumber()).append("|");
                    }
                }
                printString.append("\n");
            }
        }
        else{
            printString.append("--------------------------------\n")
                    .append("Game is NOT initialized")
                    .append("\n--------------------------------\n");

        }
        System.out.println(printString);
    }

    /**
     * Print the triggered http session id to console
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public void printTriggeredSessionId() {

        String printString = "--------------------------------\n" +
                "Triggered by session id: \n" +
                gameService.getGame().getSessionId() +
                "\n--------------------------------\n";
        System.out.println(printString);
    }

    /**
     * Print all fields with their field numbers to console
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public void printFieldNumberingToConsole() {
        Board board = gameService.getGame().getBoard();

        int fieldSize = board.getFieldMatrix().length;

        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                System.out.println(board.getSingleFieldFromMatrix(y, x).getFieldNumber()
                        + " - " + board.getSingleFieldFromMatrix(y, x).getFieldColor());
            }
        }
    }
}
