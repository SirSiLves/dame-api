package ch.ffhs.dameapi.service;

import ch.ffhs.dameapi.DameApiApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class GameServiceTest extends DameApiApplicationTests {

    @Autowired
    private GameService gameService;

    @Autowired
    private InitializeService initializeService;


    @Test
    public void testStartUpStoneCountDefault() {
        initializeService.initializeGame(8);
        assertEquals(24, gameService.getStones().size());
    }

    @Test
    public void testStartUpStoneCountExtended() {
        initializeService.initializeGame(10);
        assertEquals(40, gameService.getStones().size());
    }

    @Test
    public void testStartUpStoneCountCustom() {
        initializeService.initializeGame(20);
        assertEquals(180, gameService.getStones().size());
    }

    @Test
    public void testStartUpStoneCountEmpty() {
        initializeService.initializeGame(0);
        assertEquals(0, gameService.getStones().size());
    }
}
