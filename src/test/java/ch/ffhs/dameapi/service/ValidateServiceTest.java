package ch.ffhs.dameapi.service;

import ch.ffhs.dameapi.DameApiApplicationTests;
import ch.ffhs.dameapi.model.Color;
import ch.ffhs.dameapi.model.Field;
import ch.ffhs.dameapi.model.TypeOfMove;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class ValidateServiceTest extends DameApiApplicationTests {

    @Autowired
    private InitializeService initializeService;

    @Autowired
    private ValidateService validateService;

    @Autowired
    private MoveService moveService;

    @Autowired
    private BoardService boardService;


    @Test
    public void testValidateMoveDefault() {
        initializeService.initializeGame(8);

        // | 0|<>| 2|<>| 4|<>| 6|<>|
        // |<>| 9|<>|11|<>|13|<>|15|
        // |16|<>|18|<>|20|<>|22|<>|
        // |24|25|26|27|28|29|30|31|
        // |32|33|34|35|36|37|38|39|
        // |<>|41|<>|43|<>|45|<>|47|
        // |48|<>|50|<>|52|<>|54|<>|
        // |<>|57|<>|59|<>|61|<>|63|

        //player BLACK
        validateService.validateMove(17, 24, TypeOfMove.NORMAL);
        assertTrue(validateService.getValidity().getIsValid());
        validateService.validateMove(17, 26, TypeOfMove.NORMAL);
        assertTrue(validateService.getValidity().getIsValid());

        validateService.validateMove(17, 9, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(17, 25, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(17, 16, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(17, 18, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //only allowed as queen
        validateService.validateMove(17, 10, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(17, 8, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //out of field
        validateService.validateMove(8, 7, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //jump over the edge
        validateService.validateMove(23, 32, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //player WHITE
        validateService.validateMove(40, 33, TypeOfMove.NORMAL);
        assertTrue(validateService.getValidity().getIsValid());
        validateService.validateMove(42, 33, TypeOfMove.NORMAL);
        assertTrue(validateService.getValidity().getIsValid());

        validateService.validateMove(46, 38, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(46, 45, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(46, 47, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(46, 54, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //only allowed as queen
        validateService.validateMove(46, 53, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(46, 55, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //out of field
        validateService.validateMove(55, 64, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //jump over the edge
        validateService.validateMove(40, 31, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
    }

    @Test
    public void testValidateMoveExtend() {
        initializeService.initializeGame(10);

        // | 0|<>| 2|<>| 4|<>| 6|<>| 8|<>|
        // |<>|11|<>|13|<>|15|<>|17|<>|19|
        // |20|<>|22|<>|24|<>|26|<>|28|<>|
        // |<>|31|<>|33|<>|35|<>|37|<>|39|
        // |40|41|42|43|44|45|46|47|48|49|
        // |50|51|52|53|54|55|56|57|58|59|
        // |60|<>|62|<>|64|<>|66|<>|68|<>|
        // |<>|71|<>|73|<>|75|<>|77|<>|79|
        // |80|<>|82|<>|84|<>|86|<>|88|<>|
        // |<>|91|<>|93|<>|95|<>|97|<>|99|

        //player BLACK
        validateService.validateMove(38, 49, TypeOfMove.NORMAL);
        assertTrue(validateService.getValidity().getIsValid());
        validateService.validateMove(38, 47, TypeOfMove.NORMAL);
        assertTrue(validateService.getValidity().getIsValid());

        validateService.validateMove(38, 28, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(38, 37, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(38, 39, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(38, 48, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //only allowed as queen
        validateService.validateMove(38, 27, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(38, 29, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //out of field
        validateService.validateMove(29, 20, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //jump over the edge
        validateService.validateMove(29, 40, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //player WHITE
        validateService.validateMove(61, 50, TypeOfMove.NORMAL);
        assertTrue(validateService.getValidity().getIsValid());
        validateService.validateMove(61, 52, TypeOfMove.NORMAL);
        assertTrue(validateService.getValidity().getIsValid());

        validateService.validateMove(61, 51, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(61, 60, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(61, 62, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(61, 71, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //only allowed as queen
        validateService.validateMove(61, 70, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
        validateService.validateMove(61, 72, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //out of field
        validateService.validateMove(70, 79, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());

        //jump over the edge
        validateService.validateMove(69, 60, TypeOfMove.NORMAL);
        assertFalse(validateService.getValidity().getIsValid());
    }

    @Test
    public void validateHopMove() {
        initializeService.initializeGame(8);

        //do normal hop Move
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(37));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(30));
        validateService.validateMove(37, 23, TypeOfMove.HOP);
        assertTrue(validateService.getValidity().getIsValid());

        //do double hop Move
        moveService.doHopMove(boardService.getField(37), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(49), boardService.getField(42));
        moveService.doSimpleMove(boardService.getField(3), boardService.getField(10));
        moveService.doSimpleMove(boardService.getField(35), boardService.getField(28));
        validateService.validateMove(21, 35, TypeOfMove.HOP);
        assertTrue(validateService.getValidity().getIsValid());
        moveService.doHopMove(boardService.getField(21), boardService.getField(35));
        assertEquals(10, boardService.getField(49).getStone().getStoneId());
        assertNull(boardService.getField(42).getStone());
        assertNull(boardService.getField(28).getStone());
    }

    @Test
    public void validateHopMoveExtended() {
        initializeService.initializeGame(10);

        //do normal hop Move
        moveService.doSimpleMove(boardService.getField(61), boardService.getField(50));
        moveService.doSimpleMove(boardService.getField(32), boardService.getField(41));
        validateService.validateMove(50, 32, TypeOfMove.HOP);
        assertTrue(validateService.getValidity().getIsValid());
        moveService.doHopMove(boardService.getField(50), boardService.getField(32));
        moveService.doHopMove(boardService.getField(23), boardService.getField(41));

        //do double hop Move
        moveService.doSimpleMove(boardService.getField(63), boardService.getField(54));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(72), boardService.getField(61));
        moveService.doSimpleMove(boardService.getField(5), boardService.getField(14));
        moveService.doSimpleMove(boardService.getField(81), boardService.getField(72));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(72), boardService.getField(63));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(54), boardService.getField(45));
        validateService.validateMove(36, 54, TypeOfMove.HOP);
        assertTrue(validateService.getValidity().getIsValid());
        moveService.doHopMove(boardService.getField(36), boardService.getField(54));
        assertEquals(18, boardService.getField(72).getStone().getStoneId());
        assertNull(boardService.getField(63).getStone());
        assertNull(boardService.getField(45).getStone());
    }

    @Test
    public void testValidateLastTouched() {
        initializeService.initializeGame(8);

        assertNull(validateService.getLastTouched());

        Field currentField = boardService.getField(17);
        Field targetField = boardService.getField(26);

        moveService.doSimpleMove(currentField, targetField);

        assertEquals(validateService.getLastTouched(), Color.BLACK);

        currentField = boardService.getField(42);
        targetField = boardService.getField(35);

        moveService.doSimpleMove(currentField, targetField);

        assertEquals(validateService.getLastTouched(), Color.WHITE);

        // Move again a white stone
        currentField = boardService.getField(35);
        targetField = boardService.getField(28);

        assertFalse(moveService.doSimpleMove(currentField, targetField).getIsValid());
    }

    @Test
    public void testValidateRuleSet() {
        initializeService.initializeGame(8);

        validateService.validateMove(44, 35, TypeOfMove.NORMAL);
        assertEquals(1, validateService.getValidity().getRuleNumber());

        validateService.validateMove(64, 55, TypeOfMove.NORMAL);
        assertEquals(2, validateService.getValidity().getRuleNumber());

        validateService.validateMove(38, 49, TypeOfMove.NORMAL);
        assertEquals(3, validateService.getValidity().getRuleNumber());

        moveService.doSimpleMove(boardService.getField(44), boardService.getField(35));
        validateService.validateMove(35, 26, TypeOfMove.NORMAL);
        assertEquals(4, validateService.getValidity().getRuleNumber());

        validateService.validateMove(17, 25, TypeOfMove.NORMAL);
        assertEquals(5, validateService.getValidity().getRuleNumber());

        moveService.doSimpleMove(boardService.getField(44), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(19), boardService.getField(28));
        validateService.validateMove(35, 44, TypeOfMove.NORMAL);
        assertEquals(6, validateService.getValidity().getRuleNumber());

        validateService.validateMove(40, 26, TypeOfMove.HOP);
        assertEquals(7, validateService.getValidity().getRuleNumber());

        validateService.validateMove(51, 33, TypeOfMove.HOP);
        assertEquals(8, validateService.getValidity().getRuleNumber());

        moveService.doSimpleMove(boardService.getField(46), boardService.getField(37));
        validateService.validateMove(17, 24, TypeOfMove.NORMAL);
        assertEquals(9, validateService.getValidity().getRuleNumber());
    }

    @Test
    public void testValidateQueenState() {
        initializeService.initializeGame(8);

        assertTrue(moveService.doSimpleMove(boardService.getField(44), boardService.getField(35)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(17), boardService.getField(26)).getIsValid());
        assertTrue(moveService.doHopMove(boardService.getField(35), boardService.getField(17)).getIsValid());
        assertTrue(moveService.doHopMove(boardService.getField(8), boardService.getField(26)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(42), boardService.getField(33)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(26), boardService.getField(35)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(33), boardService.getField(24)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(19), boardService.getField(28)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(40), boardService.getField(33)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(10), boardService.getField(19)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(24), boardService.getField(17)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(1), boardService.getField(10)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(17), boardService.getField(8)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(10), boardService.getField(17)).getIsValid());
        assertTrue(moveService.doSimpleMove(boardService.getField(8), boardService.getField(1)).getIsValid());

        assertTrue(boardService.getField(1).getStone().getQueen());
    }
}
