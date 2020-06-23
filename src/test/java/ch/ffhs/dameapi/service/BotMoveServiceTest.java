package ch.ffhs.dameapi.service;

import ch.ffhs.dameapi.DameApiApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class BotMoveServiceTest extends DameApiApplicationTests {

    @Autowired
    private GameService gameService;

    @Autowired
    private BotMoveService botMoveService;

    @Autowired
    private InitializeService initializeService;

    @Autowired
    private MoveService moveService;

    @Autowired
    private BoardService boardService;


    @BeforeEach
    public void cleanSession() {
        gameService.removeAllGames();
    }

    @Test
    public void testBotMoveSimple() {
        initializeService.initializeGame(8);

        assertNotNull(botMoveService.getBotMove());
    }

    @Test
    public void testBotMoveHop() {
        initializeService.initializeGame(10);

        moveService.doSimpleMove(boardService.getField(63), boardService.getField(54));
        moveService.doSimpleMove(boardService.getField(36), boardService.getField(45));
        assertNotNull(botMoveService.getBotMove());
    }

    @Test
    public void testBotDoubleHop(){
        initializeService.initializeGame(10);

        moveService.doSimpleMove(boardService.getField(69), boardService.getField(58));
        moveService.doSimpleMove(boardService.getField(32), boardService.getField(43));
        moveService.doSimpleMove(boardService.getField(67), boardService.getField(56));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(76), boardService.getField(67));
        moveService.doSimpleMove(boardService.getField(30), boardService.getField(41));
        moveService.doSimpleMove(boardService.getField(61), boardService.getField(50));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(72), boardService.getField(61));
        moveService.doSimpleMove(boardService.getField(34), boardService.getField(45));
        moveService.doHopMove(boardService.getField(56), boardService.getField(34));
        moveService.doHopMove(boardService.getField(23), boardService.getField(45));
        moveService.doSimpleMove(boardService.getField(78), boardService.getField(69));
        moveService.doSimpleMove(boardService.getField(43), boardService.getField(52));
        moveService.doHopMove(boardService.getField(61), boardService.getField(43));

        //from field number 32 -> 54
        moveService.doHopMove(botMoveService.getBotMove().getSourceField(), botMoveService.getBotMove().getTargetField());
        assertTrue(gameService.getGame().getValidity().getIsValid());
    }
}
