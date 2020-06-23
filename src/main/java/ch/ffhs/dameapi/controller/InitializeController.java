package ch.ffhs.dameapi.controller;


import ch.ffhs.dameapi.service.GameService;
import ch.ffhs.dameapi.service.InitializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/initialize")
public class InitializeController {

    @Autowired
    private GameService gameService;

    @Autowired
    private InitializeService initializeService;


    /**
     * Initialize a new game with 8x8 squares. Get Method on /api/initialize/default
     *
     *
     * Request Method: GET
     * no params are required
     * @return      String with message & HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "default", method = RequestMethod.GET)
    public ResponseEntity<Object> eighthGame() {
        initializeService.initializeGame(8);
        return new ResponseEntity<>("Game 8x8 successfully initialized.", HttpStatus.OK);
    }

    /**
     * Initialize a new game with 10x10 squares. Get Method on /api/initialize/extended
     *
     *
     * Request Method: GET
     * no params are required
     * @return      String with message & HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "extended", method = RequestMethod.GET)
    public ResponseEntity<Object> tensGame() {
        initializeService.initializeGame(10);
        return new ResponseEntity<>("Game 10x10 successfully initialized.", HttpStatus.OK);
    }

    /**
     * Returns boolean if games is initialized or not.
     * Get Method on /api/initialize/getGameStartedStatus
     *
     *
     * Request Method: GET
     * no params are required
     * @return Boolean getGameStarted & HTTP Status
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "getGameStartedStatus", method = RequestMethod.GET)
    public ResponseEntity<Object> getGameStartedStatus() {
        return new ResponseEntity<>(gameService.getGameStarted(), HttpStatus.OK);
    }
}
