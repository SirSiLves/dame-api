package ch.ffhs.dameapi.controller;


import ch.ffhs.dameapi.exception.model.ApiException;
import ch.ffhs.dameapi.model.Board;
import ch.ffhs.dameapi.model.Game;
import ch.ffhs.dameapi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;


    /**
     * Returns the up to date Game Picture. Get Method on /api/game/getGamePicture
     * <p>
     * Request Method: GET
     * no params are required
     *
     * @return the game picture & HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "getGamePicture", method = RequestMethod.GET)
    public ResponseEntity<Board> getGamePicture() {
        if (gameService.getGamePicture() != null) {
            return new ResponseEntity<>(gameService.getGamePicture(), HttpStatus.OK);
        } else {
            throw new ApiException("The game is not yet initialized", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the actual session. Get Method on /api/game/getGameSessions
     * <p>
     * Request Method: GET
     * no params are required
     *
     * @return the game session & HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "getGameSessions", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Game>> getGameSessions() {
        if (gameService.getGamePicture() != null) {
            return new ResponseEntity<>(gameService.getGameSessions(), HttpStatus.OK);
        } else {
            throw new ApiException("The game is not yet initialized", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Changes the player mode. Single plays against the Computer. Multi plays against another person
     * Post Method on /api/game/setPlayerMode
     * <p>
     * Request Method: Post
     *
     * @param botEnabled on toggle true/false
     * @return HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "setBotStatus", method = RequestMethod.POST)
    public ResponseEntity<String> setBotStatus(@RequestBody Boolean botEnabled) {
        gameService.getGame().setBotStatus(botEnabled);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
