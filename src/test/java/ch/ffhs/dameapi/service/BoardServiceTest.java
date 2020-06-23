package ch.ffhs.dameapi.service;

import ch.ffhs.dameapi.DameApiApplicationTests;
import ch.ffhs.dameapi.model.Color;
import ch.ffhs.dameapi.model.Field;
import ch.ffhs.dameapi.model.Stone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class BoardServiceTest extends DameApiApplicationTests {

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;


    @BeforeEach
    public void createGameBoard(){
        //is needed for getting a board on this session id
        gameService.createGame();
    }

    @Test
    public void testFieldMatrix(){
        boardService.createFields(3);

        assertEquals(0, boardService.getBoard().getSingleFieldFromMatrix(0,0).getFieldNumber());
        assertEquals(1, boardService.getBoard().getSingleFieldFromMatrix(0,1).getFieldNumber());
        assertEquals(2, boardService.getBoard().getSingleFieldFromMatrix(0,2).getFieldNumber());
        assertEquals(3, boardService.getBoard().getSingleFieldFromMatrix(1,0).getFieldNumber());
        assertEquals(4, boardService.getBoard().getSingleFieldFromMatrix(1,1).getFieldNumber());
        assertEquals(5, boardService.getBoard().getSingleFieldFromMatrix(1,2).getFieldNumber());
        assertEquals(6, boardService.getBoard().getSingleFieldFromMatrix(2,0).getFieldNumber());
        assertEquals(7, boardService.getBoard().getSingleFieldFromMatrix(2,1).getFieldNumber());
        assertEquals(8, boardService.getBoard().getSingleFieldFromMatrix(2,2).getFieldNumber());

        assertEquals(3, boardService.getBoard().getFieldMatrix().length);
    }

    @Test
    public void testFieldFailure(){
        boardService.createFields(5);

        Exception exception = assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                boardService.getBoard().getSingleFieldFromMatrix(0,5));

        String expectedMessage = "Index 5 out of bounds for length 5";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSetFields() {
        boardService.createFields(10);

        int fieldNumberBefore = boardService.getBoard().getSingleFieldFromMatrix(9,9).getFieldNumber();
        Field tempField = new Field(9999, Color.BROWN);

        boardService.getBoard().setSingleFieldInMatrix(9,9,tempField);
        int fieldNumberAfter = boardService.getBoard().getSingleFieldFromMatrix(9,9).getFieldNumber();

        assertNotEquals(fieldNumberBefore,fieldNumberAfter);
        assertEquals(99, fieldNumberBefore);
        assertEquals(9999, fieldNumberAfter);
    }

    @Test
    public void testSetStone(){
        boardService.createFields(4);

        Field tempField = boardService.getBoard().getSingleFieldFromMatrix(0,0);
        Stone tempStone = new Stone(99, Color.WHITE);

        assertNull(tempField.getStone());

        boardService.getBoard().getSingleFieldFromMatrix(0,0).setStone(tempStone);

        assertEquals(Stone.class, tempField.getStone().getClass());
    }
}
