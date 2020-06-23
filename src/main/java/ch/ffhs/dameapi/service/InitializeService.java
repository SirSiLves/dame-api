package ch.ffhs.dameapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InitializeService {

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private StoneService stoneService;

    @Autowired
    private PrintService printService;


    /**
     * Creates a new game object, one board, 64 or 100 fields and 24 or 40 stones.
     * After successfully creating the game will be printed to console.
     *
     *
     * @param gameSize game board size. Normally 8/10
     * --> Error Handling by general Exception Handling
     */
    public void initializeGame(int gameSize) {

        gameService.createGame();
        boardService.createFields(gameSize);
        stoneService.createStones();

        printService.printGameToConsole(ConsolePrintState.INITIALIZE);
        printService.printTriggeredSessionId();
    }

}
