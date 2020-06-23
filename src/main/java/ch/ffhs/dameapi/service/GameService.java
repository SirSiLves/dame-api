package ch.ffhs.dameapi.service;


import ch.ffhs.dameapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Service
public class GameService {

    @Autowired
    private SessionService sessionService;

    private final HashMap<String, Game> gameSessions;


    public GameService(){
        gameSessions = new HashMap<>();
    }

    /**
     * Search in the HashMap gameSession with the session id key
     *
     *
     * @return return the right game object with the connection http session id from the browser
     * --> Error Handling by general Exception Handling
     */
    public Game getGame(){
        return gameSessions.get(sessionService.getHttpSessionId());
    }

    /**
     * If there are no gameSessions, no game has been initialized. So it returns directly null.
     *
     *
     * @return return Board with specific gameSession
     * --> Error Handling by general Exception Handling
     */
    public Board getGamePicture(){
        if(gameSessions.size() > 0) {
            return this.getGame().getBoard();
        }
        else return null;
    }

    public ArrayList<Stone> getStones() {
        return this.getGame().getBoard().getStoneArrayList();
    }

    public void createGame() {
        gameSessions.put(sessionService.getHttpSessionId(), new Game(sessionService.getHttpSessionId()));
    }

    public Boolean getGameStarted() {
        return gameSessions.get(sessionService.getHttpSessionId()) != null;
    }

    public HashMap<String, Game> getGameSessions(){
        return gameSessions;
    }

    /**
     * Delete a game from the gameSession HashMap with the specific session id
     *
     *
     * @param sessionId session id from the frontend connector
     * --> Error Handling by general Exception Handling
     */
    public void removeSingleGame(String sessionId){

        Iterator<Map.Entry<String, Game>> itr = gameSessions.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry<String, Game> entry = itr.next();

            if(entry.getValue().getSessionId().equals(sessionId)) {
                System.out.println("Game with id "+ entry.getKey() + " removed");
                itr.remove();
            }
        }
    }

    /**
     * Delete all game objects in the gameSession HashMap.
     * This method is mostly used from JUnit-Tests
     *
     *
     * --> Error Handling by general Exception Handling
     */
    public void removeAllGames(){
        Iterator<Map.Entry<String, Game>> itr = gameSessions.entrySet().iterator();
        while(itr.hasNext()) {
            System.out.println("Game with id "+ itr.next().getKey() + " removed");
            itr.remove();
        }
    }
}


enum ConsolePrintState {
    INITIALIZE, MOVE, REVERT
}
