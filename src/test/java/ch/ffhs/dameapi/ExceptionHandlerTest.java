package ch.ffhs.dameapi;

import ch.ffhs.dameapi.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ExceptionHandlerTest extends DameApiApplicationTests {

    @Autowired
    private GameService gameService;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    public void prepareTestEnvironment() {
        gameService.removeAllGames();
    }

    @Test
    public void testGetGameBeforeInitialized() throws MalformedURLException {
        this.setBaseURL("/game/getGamePicture");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URL(this.getBaseURL()).toString(), String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        this.setBaseURL("/game/getGameSessions");
        responseEntity = restTemplate.getForEntity(new URL(this.getBaseURL()).toString(), String.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testInvalidMoveController() throws MalformedURLException {

        //set cookie for same session id
        HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.put(HttpHeaders.COOKIE, responseEntity.getHeaders().get("Set-Cookie"));
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //set json post object
        HttpEntity<String> entity = new HttpEntity<>("Hallo", requestHeaders);

        this.setBaseURL("/move/default");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(new URL(this.getBaseURL()).toString(), entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testCheckValidNullPointer() throws MalformedURLException {
        this.setBaseURL("/test/checkValid/9999/9999/NORMAL");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URL(this.getBaseURL()).toString(), String.class);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("This request creates a NullPointer Exception", responseEntity.getBody());
    }
}
