package ch.ffhs.dameapi.service;


import ch.ffhs.dameapi.DameApiApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ControllerTest extends DameApiApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameService gameService;


    @BeforeEach
    public void prepareTestEnvironment() {
        gameService.removeAllGames();
    }

    @Test
    public void testInitializeController() throws MalformedURLException {
        this.setBaseURL("/initialize/default");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URL(this.getBaseURL()).toString(), String.class);
        assertEquals("Game 8x8 successfully initialized.", responseEntity.getBody());

        this.setBaseURL("/initialize/extended");
        responseEntity = restTemplate.getForEntity(new URL(this.getBaseURL()).toString(), String.class);
        assertEquals("Game 10x10 successfully initialized.", responseEntity.getBody());
    }

    @Test
    public void testGameController() throws MalformedURLException {
        this.setBaseURL("/initialize/default");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URL(this.getBaseURL()).toString(), String.class);

        //set cookie for same session id
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, responseEntity.getHeaders().get("Set-Cookie"));
        HttpEntity<String> entity = new HttpEntity<>(requestHeaders);

        this.setBaseURL("/game/getGamePicture");
        responseEntity = restTemplate.exchange(this.getBaseURL(), HttpMethod.GET, entity, String.class);
        //sometimes the object tree is differently generated -> see json_text 3 / 4
        if (JSON_TEXTS.get(3).equals(responseEntity.getBody()))
            assertEquals(JSON_TEXTS.get(3), responseEntity.getBody());
        else assertEquals(JSON_TEXTS.get(4), responseEntity.getBody());

        this.setBaseURL("/game/getGameSessions");
        responseEntity = restTemplate.exchange(this.getBaseURL(), HttpMethod.GET, entity, String.class);

        String sessionId = Objects.requireNonNull(responseEntity.getBody()).substring(2, responseEntity.getBody().indexOf(":{") - 1);
        assertNotNull(gameService.getGameSessions().get(sessionId));

        //enable bot
        this.setBaseURL("/game/setBotStatus");
        responseEntity = restTemplate.postForEntity(new URL(this.getBaseURL()).toString(), new HttpEntity<>(true, requestHeaders), String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //disable bot
        this.setBaseURL("/game/setBotStatus");
        responseEntity = restTemplate.postForEntity(new URL(this.getBaseURL()).toString(), new HttpEntity<>(false, requestHeaders), String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testMoveController() throws MalformedURLException {
        this.setBaseURL("/initialize/default");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(new URL(this.getBaseURL()).toString(), String.class);

        //set cookie for same session id
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, responseEntity.getHeaders().get("Set-Cookie"));
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //set json post object
        HttpEntity<String> entity = new HttpEntity<>(JSON_TEXTS.get(2), requestHeaders);

        this.setBaseURL("/move/default");
        responseEntity = restTemplate.postForEntity(new URL(this.getBaseURL()).toString(), entity, String.class);

        assertEquals(JSON_TEXTS.get(1), responseEntity.getBody());

        //enable bot
        this.setBaseURL("/game/setBotStatus");
        responseEntity = restTemplate.postForEntity(new URL(this.getBaseURL()).toString(), new HttpEntity<>(true, requestHeaders), String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //execute bot move
        this.setBaseURL("/move/doBotMove");
        responseEntity = restTemplate.postForEntity(new URL(this.getBaseURL()).toString(), new HttpEntity<>(null, requestHeaders), String.class);

        assertEquals(JSON_TEXTS.get(5), responseEntity.getBody());

        //disable bot
        this.setBaseURL("/game/setBotStatus");
        responseEntity = restTemplate.postForEntity(new URL(this.getBaseURL()).toString(), new HttpEntity<>(false, requestHeaders), String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private static final Map<Integer, String> JSON_TEXTS = new HashMap<>() {
        {
            put(1, "{\"ruleNumber\":1,\"validationMessage\":\"Valid\",\"isValid\":true,\"lastTouched\":\"BLACK\"}");
            put(2, "{\"currentField\":{\"fieldColor\":\"BROWN\",\"fieldNumber\": 19,\"stone\":{\"stoneId\":9,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},\t\"targetField\":{\"fieldColor\":\"BROWN\",\"fieldNumber\":28,\"stone\":null}}");
            put(3, "{\"fieldMatrix\":[[{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":0,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":1,\"stone\":{\"stoneId\":0,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":2,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":3,\"stone\":{\"stoneId\":1,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":4,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":5,\"stone\":{\"stoneId\":2,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":6,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":7,\"stone\":{\"stoneId\":3,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}}],[{\"fieldColor\":\"BROWN\",\"fieldNumber\":8,\"stone\":{\"stoneId\":4,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":9,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":10,\"stone\":{\"stoneId\":5,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":11,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":12,\"stone\":{\"stoneId\":6,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":13,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":14,\"stone\":{\"stoneId\":7,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":15,\"stone\":null}],[{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":16,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":17,\"stone\":{\"stoneId\":8,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":18,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":19,\"stone\":{\"stoneId\":9,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":20,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":21,\"stone\":{\"stoneId\":10,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":22,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":23,\"stone\":{\"stoneId\":11,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false}}],[{\"fieldColor\":\"BROWN\",\"fieldNumber\":24,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":25,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":26,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":27,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":28,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":29,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":30,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":31,\"stone\":null}],[{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":32,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":33,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":34,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":35,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":36,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":37,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":38,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":39,\"stone\":null}],[{\"fieldColor\":\"BROWN\",\"fieldNumber\":40,\"stone\":{\"stoneId\":12,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":41,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":42,\"stone\":{\"stoneId\":13,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":43,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":44,\"stone\":{\"stoneId\":14,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":45,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":46,\"stone\":{\"stoneId\":15,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":47,\"stone\":null}],[{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":48,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":49,\"stone\":{\"stoneId\":16,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":50,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":51,\"stone\":{\"stoneId\":17,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":52,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":53,\"stone\":{\"stoneId\":18,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":54,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":55,\"stone\":{\"stoneId\":19,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}}],[{\"fieldColor\":\"BROWN\",\"fieldNumber\":56,\"stone\":{\"stoneId\":20,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":57,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":58,\"stone\":{\"stoneId\":21,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":59,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":60,\"stone\":{\"stoneId\":22,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":61,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":62,\"stone\":{\"stoneId\":23,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":63,\"stone\":null}]],\"stoneArrayList\":[{\"stoneId\":0,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":1,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":2,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":3,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":4,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":5,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":6,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":7,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":8,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":9,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":10,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":11,\"stoneColor\":\"BLACK\",\"alive\":true,\"queen\":false},{\"stoneId\":12,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":13,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":14,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":15,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":16,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":17,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":18,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":19,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":20,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":21,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":22,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false},{\"stoneId\":23,\"stoneColor\":\"WHITE\",\"alive\":true,\"queen\":false}],\"winnerColor\":null,\"moveHistory\":{}}");
            put(4, "{\"fieldMatrix\":[[{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":0,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":1,\"stone\":{\"stoneId\":0,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":2,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":3,\"stone\":{\"stoneId\":1,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":4,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":5,\"stone\":{\"stoneId\":2,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":6,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":7,\"stone\":{\"stoneId\":3,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}}],[{\"fieldColor\":\"BROWN\",\"fieldNumber\":8,\"stone\":{\"stoneId\":4,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":9,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":10,\"stone\":{\"stoneId\":5,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":11,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":12,\"stone\":{\"stoneId\":6,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":13,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":14,\"stone\":{\"stoneId\":7,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":15,\"stone\":null}],[{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":16,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":17,\"stone\":{\"stoneId\":8,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":18,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":19,\"stone\":{\"stoneId\":9,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":20,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":21,\"stone\":{\"stoneId\":10,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":22,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":23,\"stone\":{\"stoneId\":11,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true}}],[{\"fieldColor\":\"BROWN\",\"fieldNumber\":24,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":25,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":26,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":27,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":28,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":29,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":30,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":31,\"stone\":null}],[{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":32,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":33,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":34,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":35,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":36,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":37,\"stone\":null},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":38,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":39,\"stone\":null}],[{\"fieldColor\":\"BROWN\",\"fieldNumber\":40,\"stone\":{\"stoneId\":12,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":41,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":42,\"stone\":{\"stoneId\":13,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":43,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":44,\"stone\":{\"stoneId\":14,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":45,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":46,\"stone\":{\"stoneId\":15,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":47,\"stone\":null}],[{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":48,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":49,\"stone\":{\"stoneId\":16,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":50,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":51,\"stone\":{\"stoneId\":17,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":52,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":53,\"stone\":{\"stoneId\":18,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":54,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":55,\"stone\":{\"stoneId\":19,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}}],[{\"fieldColor\":\"BROWN\",\"fieldNumber\":56,\"stone\":{\"stoneId\":20,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":57,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":58,\"stone\":{\"stoneId\":21,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":59,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":60,\"stone\":{\"stoneId\":22,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":61,\"stone\":null},{\"fieldColor\":\"BROWN\",\"fieldNumber\":62,\"stone\":{\"stoneId\":23,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}},{\"fieldColor\":\"SANDYBROWN\",\"fieldNumber\":63,\"stone\":null}]],\"stoneArrayList\":[{\"stoneId\":0,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":1,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":2,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":3,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":4,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":5,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":6,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":7,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":8,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":9,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":10,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":11,\"stoneColor\":\"BLACK\",\"queen\":false,\"alive\":true},{\"stoneId\":12,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":13,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":14,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":15,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":16,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":17,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":18,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":19,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":20,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":21,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":22,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true},{\"stoneId\":23,\"stoneColor\":\"WHITE\",\"queen\":false,\"alive\":true}],\"winnerColor\":null,\"moveHistory\":{}}");
            put(5, "{\"ruleNumber\":1,\"validationMessage\":\"Valid\",\"isValid\":true,\"lastTouched\":\"WHITE\"}");
        }
    };
}
