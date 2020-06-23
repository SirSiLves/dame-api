package ch.ffhs.dameapi.service;

import ch.ffhs.dameapi.DameApiApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class SessionServiceTest extends DameApiApplicationTests {

    @Autowired
    private InitializeService initializeService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SessionService sessionService;


    @BeforeEach
    public void cleanSession(){
        gameService.removeAllGames();
    }

    @Test
    public void testSessionHandling(){
        assertEquals(0, gameService.getGameSessions().size());

        initializeService.initializeGame(8);
        assertEquals(1, gameService.getGameSessions().size());
        assertEquals("JUnitTestId123456789", gameService.getGame().getSessionId());

        initializeService.initializeGame(10);
        assertEquals(1, gameService.getGameSessions().size());
        assertEquals("JUnitTestId123456789", gameService.getGame().getSessionId());

        String customSessionId1 = "aabbccdd123456789";
        this.setMockSessionid(customSessionId1);

        initializeService.initializeGame(8);
        assertEquals(2, gameService.getGameSessions().size());
        assertEquals(customSessionId1, gameService.getGame().getSessionId());

        initializeService.initializeGame(10);
        assertEquals(2, gameService.getGameSessions().size());
        assertEquals(customSessionId1, gameService.getGame().getSessionId());
    }

    @Test
    public void testSessionLimit(){
        int max = 10000;

        for(int i = 0; i < max; i++){
            String customId = "customId" + i;
            this.setMockSessionid(customId);

            initializeService.initializeGame(8);
        }
        assertEquals(max, gameService.getGameSessions().size());
    }
}
