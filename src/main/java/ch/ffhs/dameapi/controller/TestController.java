package ch.ffhs.dameapi.controller;


import ch.ffhs.dameapi.model.TypeOfMove;
import ch.ffhs.dameapi.service.SessionService;
import ch.ffhs.dameapi.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private ValidateService validateService;

    @Autowired
    private SessionService sessionService;


    /**
     * Returns the ID of the http session.
     * Get Method on /api/test/getHttpSessionID
     *
     *
     * Request Method: GET
     * @return String with the http session id
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "getHttpSessionId", method = RequestMethod.GET)
    public String getHttpSessionId() {
        return "Your http session id is " + sessionService.getHttpSessionId() + "\n";
    }

    /**
     * Returns the time of the server. Used for debugging purposes.
     * Get Method on /api/test/getTime
     *
     *
     * Request Method: GET
     * @return String with the actual time of the server
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "getTime", method = RequestMethod.GET)
    public String getTime() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }

    /**
     * Checks if a move is valid or not
     * Get Method on /api/test//checkValid/{currentFieldNumber}/{targetFieldNumber}/{typeOfMove}
     *
     *
     * Request Method: GET
     * @param currentFieldNumber int of the field which the stone is right now
     * @param targetFieldNumber int of the field whicht the stone should move to
     * @param typeOfMove is it a simple or a hop move
     * @return Boolean
     * --> Error Handling by general Exception Handling and @see ApiException
     */
    @RequestMapping(value = "/checkValid/{currentFieldNumber}/{targetFieldNumber}/{typeOfMove}", method = RequestMethod.GET)
    public ResponseEntity<Object> getSingleUser(@PathVariable int currentFieldNumber, @PathVariable int targetFieldNumber, @PathVariable TypeOfMove typeOfMove) {
        validateService.validateMove(currentFieldNumber, targetFieldNumber, typeOfMove);
        return new ResponseEntity<>(validateService.getValidity().getIsValid(), HttpStatus.OK);
    }
}

