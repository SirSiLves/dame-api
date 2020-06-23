package ch.ffhs.dameapi.service;

import ch.ffhs.dameapi.DameApiApplicationTests;
import ch.ffhs.dameapi.model.Color;
import ch.ffhs.dameapi.model.HistoryMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


public class MoveServiceTest extends DameApiApplicationTests {

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private InitializeService initializeService;

    @Autowired
    private MoveService moveService;


    @BeforeEach
    public void cleanSession() {
        gameService.removeAllGames();
    }

    @Test
    public void testSimpleMove() {
        initializeService.initializeGame(8);

        assertNull(boardService.getField(35).getStone());
        moveService.doSimpleMove(boardService.getField(44), boardService.getField(35));
        assertEquals(Color.WHITE, boardService.getField(35).getStone().getStoneColor());

        initializeService.initializeGame(10);

        assertNull(boardService.getField(43).getStone());
        moveService.doSimpleMove(boardService.getField(32), boardService.getField(43));
        assertEquals(Color.BLACK, boardService.getField(43).getStone().getStoneColor());
    }

    @Test
    public void testHopMove() {
        initializeService.initializeGame(8);
        moveService.doSimpleMove(boardService.getField(44), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));

        assertNull(boardService.getField(17).getStone());
        moveService.doHopMove(boardService.getField(35), boardService.getField(17));
        assertEquals(Color.WHITE, boardService.getField(17).getStone().getStoneColor());
    }

    @Test
    public void testDoubleHopMove() {
        initializeService.initializeGame(8);

        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(39));
        moveService.doSimpleMove(boardService.getField(8), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(53), boardService.getField(46));
        moveService.doSimpleMove(boardService.getField(26), boardService.getField(35));

        assertNull(boardService.getField(8).getStone());
        moveService.doHopMove(boardService.getField(44), boardService.getField(26));
        assertEquals(Color.WHITE, boardService.getField(8).getStone().getStoneColor());
    }

    @Test
    public void testCurvedDoubleHop(){
        initializeService.initializeGame(8);

        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(40), boardService.getField(33));
        moveService.doHopMove(boardService.getField(26), boardService.getField(40));
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(39));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(53), boardService.getField(46));
        moveService.doSimpleMove(boardService.getField(19), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(60), boardService.getField(53));
        moveService.doSimpleMove(boardService.getField(8), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(33));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(24));
        moveService.doHopMove(boardService.getField(33), boardService.getField(19));
        moveService.doHopMove(boardService.getField(12), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(44), boardService.getField(35));
        moveService.doHopMove(boardService.getField(26), boardService.getField(44));
        moveService.doHopMove(boardService.getField(51), boardService.getField(37));
        moveService.doHopMove(boardService.getField(37), boardService.getField(19));
        moveService.doHopMove(boardService.getField(10), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(49), boardService.getField(42));
        moveService.doSimpleMove(boardService.getField(3), boardService.getField(10));
        moveService.doSimpleMove(boardService.getField(58), boardService.getField(51));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(35));
        moveService.doHopMove(boardService.getField(28), boardService.getField(42));

        assertNull(boardService.getField(42).getStone());
        assertEquals(Color.BLACK, boardService.getField(60).getStone().getStoneColor());
    }

    @Test
    public void testQueenMoves() {
        initializeService.initializeGame(10);
        moveService.doSimpleMove(boardService.getField(30), boardService.getField(41));
        moveService.doSimpleMove(boardService.getField(61), boardService.getField(50));
        moveService.doSimpleMove(boardService.getField(41), boardService.getField(52));
        moveService.doHopMove(boardService.getField(63), boardService.getField(41));
        moveService.doSimpleMove(boardService.getField(32), boardService.getField(43));
        moveService.doSimpleMove(boardService.getField(41), boardService.getField(30));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(69), boardService.getField(58));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(50), boardService.getField(41));
        moveService.doHopMove(boardService.getField(32), boardService.getField(50));
        moveService.doSimpleMove(boardService.getField(58), boardService.getField(49));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(67), boardService.getField(58));
        moveService.doSimpleMove(boardService.getField(43), boardService.getField(54));
        moveService.doHopMove(boardService.getField(65), boardService.getField(43));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(10));
        moveService.doSimpleMove(boardService.getField(12), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(30), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(1), boardService.getField(12));
        //last field without queen state
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(1));
        assertTrue(boardService.getField(1).getStone().getQueen());

        moveService.doHopMove(boardService.getField(12), boardService.getField(30));
        //move queen stone
        moveService.doSimpleMove(boardService.getField(1), boardService.getField(10));
        assertTrue(boardService.getField(10).getStone().getQueen());

        moveService.doSimpleMove(boardService.getField(36), boardService.getField(45));
        moveService.doSimpleMove(boardService.getField(78), boardService.getField(67));
        moveService.doSimpleMove(boardService.getField(45), boardService.getField(54));
        moveService.doSimpleMove(boardService.getField(67), boardService.getField(56));
        moveService.doSimpleMove(boardService.getField(27), boardService.getField(36));
        moveService.doHopMove(boardService.getField(49), boardService.getField(27));
        moveService.doHopMove(boardService.getField(16), boardService.getField(38));
        // move queen stone once again
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(21));
        assertTrue(boardService.getField(21).getStone().getQueen());

        moveService.doSimpleMove(boardService.getField(38), boardService.getField(49));
        //lets do a double hop with queen
        moveService.doHopMove(boardService.getField(21), boardService.getField(43));
        assertTrue(boardService.getField(65).getStone().getQueen());
    }


    @Test
    public void testTripleHop() {
        initializeService.initializeGame(8);

        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(39));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(30));
        moveService.doSimpleMove(boardService.getField(44), boardService.getField(37));
        moveService.doHopMove(boardService.getField(30), boardService.getField(44));
        moveService.doHopMove(boardService.getField(51), boardService.getField(37));
        moveService.doHopMove(boardService.getField(26), boardService.getField(44));
        moveService.doHopMove(boardService.getField(53), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(19), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(37), boardService.getField(28));
        moveService.doHopMove(boardService.getField(26), boardService.getField(44));
        moveService.doSimpleMove(boardService.getField(28), boardService.getField(19));
        moveService.doHopMove(boardService.getField(12), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(55), boardService.getField(46));
        moveService.doSimpleMove(boardService.getField(3), boardService.getField(10));
        moveService.doSimpleMove(boardService.getField(39), boardService.getField(30));
        moveService.doHopMove(boardService.getField(21), boardService.getField(39));
        moveService.doHopMove(boardService.getField(60), boardService.getField(46));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(39));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(62), boardService.getField(55));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(30));
        moveService.doHopMove(boardService.getField(39), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(7), boardService.getField(14));
        moveService.doHopMove(boardService.getField(21), boardService.getField(7));
        moveService.doSimpleMove(boardService.getField(5), boardService.getField(14));
        moveService.doHopMove(boardService.getField(7), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(24), boardService.getField(33));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(12));
        moveService.doSimpleMove(boardService.getField(8), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(12), boardService.getField(3));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(24));

        //lets do a triple move with the queen stone: 3 -> 17 -> 35 -> 53
        //| 0|<>| 2|<>| 4| 5| 6| 7|
        //| 8| 9|<>|11|12|13|14|15|
        //|16|17|18|19|20|21|22|23|
        //|<>|25|<>|27|28|29|30|31|
        //|32|<>|34|35|36|37|38|39|
        //|<>|41|42|43|<>|45|46|47|
        //|48|<>|50|51|52|53|54|<>|
        //|<>|57|<>|59|60|61|62|63|

        assertNull(boardService.getField(53).getStone());
        moveService.doHopMove(boardService.getField(3), boardService.getField(17));
        assertTrue(boardService.getField(53).getStone().getQueen());
    }

    @Test
    public void testRevertMove() {
        initializeService.initializeGame(8);

        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        assertNull(boardService.getField(17).getStone());
        assertEquals(1, boardService.getBoard().getMoveHistory().size());

        boardService.loadPreviousMove();
        assertNotNull(boardService.getField(17).getStone());
        assertEquals(0, boardService.getBoard().getMoveHistory().size());

        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        assertNull(boardService.getField(17).getStone());
        assertEquals(1, boardService.getBoard().getMoveHistory().size());

        moveService.doSimpleMove(boardService.getField(46), boardService.getField(39));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(30));
        moveService.doSimpleMove(boardService.getField(44), boardService.getField(37));

        assertNotNull(boardService.getField(30).getStone());
        moveService.doHopMove(boardService.getField(30), boardService.getField(44));
        assertNull(boardService.getField(30).getStone());
        assertNotNull(boardService.getField(44).getStone());

        //get last entry from history
        int moveHistorySize = boardService.getBoard().getMoveHistory().size();
        HistoryMove historyHopped = boardService.getBoard().getMoveHistory().get(moveHistorySize - 1).get("lastHoppedField");
        assertNotNull(historyHopped.getStone());
        assertFalse(historyHopped.getStone().getAlive());

        boardService.loadPreviousMove();
        assertNotNull(boardService.getField(30).getStone());
        assertNull(boardService.getField(44).getStone());
        assertTrue(historyHopped.getStone().getAlive());
    }

    @Test
    public void testQueenTripleHop(){
        initializeService.initializeGame(10);

        moveService.doSimpleMove(boardService.getField(63), boardService.getField(54));
        moveService.doSimpleMove(boardService.getField(30), boardService.getField(41));
        moveService.doSimpleMove(boardService.getField(61), boardService.getField(52));
        moveService.doHopMove(boardService.getField(41), boardService.getField(63));
        moveService.doHopMove(boardService.getField(74), boardService.getField(52));
        moveService.doSimpleMove(boardService.getField(36), boardService.getField(45));
        moveService.doHopMove(boardService.getField(54), boardService.getField(36));
        moveService.doHopMove(boardService.getField(27), boardService.getField(45));
        moveService.doSimpleMove(boardService.getField(67), boardService.getField(56));
        moveService.doHopMove(boardService.getField(45), boardService.getField(67));
        moveService.doHopMove(boardService.getField(78), boardService.getField(56));
        moveService.doSimpleMove(boardService.getField(32), boardService.getField(41));
        moveService.doHopMove(boardService.getField(52), boardService.getField(30));
        moveService.doSimpleMove(boardService.getField(34), boardService.getField(45));
        moveService.doHopMove(boardService.getField(56), boardService.getField(34));
        moveService.doHopMove(boardService.getField(25), boardService.getField(43));
        moveService.doSimpleMove(boardService.getField(65), boardService.getField(54));
        moveService.doHopMove(boardService.getField(43), boardService.getField(65));
        moveService.doHopMove(boardService.getField(76), boardService.getField(54));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(72), boardService.getField(63));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(54), boardService.getField(45));
        moveService.doSimpleMove(boardService.getField(5), boardService.getField(14));
        moveService.doSimpleMove(boardService.getField(81), boardService.getField(72));
        moveService.doSimpleMove(boardService.getField(32), boardService.getField(41));
        moveService.doSimpleMove(boardService.getField(90), boardService.getField(81));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(63), boardService.getField(54));
        moveService.doSimpleMove(boardService.getField(41), boardService.getField(50));
        moveService.doSimpleMove(boardService.getField(72), boardService.getField(63));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(45), boardService.getField(36));
        moveService.doSimpleMove(boardService.getField(32), boardService.getField(41));
        moveService.doSimpleMove(boardService.getField(54), boardService.getField(45));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(36), boardService.getField(27));
        //triple hop with a normal stone
        moveService.doHopMove(boardService.getField(18), boardService.getField(36));

        moveService.doSimpleMove(boardService.getField(85), boardService.getField(74));
        moveService.doSimpleMove(boardService.getField(32), boardService.getField(43));
        moveService.doSimpleMove(boardService.getField(74), boardService.getField(63));
        moveService.doSimpleMove(boardService.getField(43), boardService.getField(54));
        moveService.doHopMove(boardService.getField(63), boardService.getField(45));
        moveService.doSimpleMove(boardService.getField(41), boardService.getField(52));
        moveService.doSimpleMove(boardService.getField(83), boardService.getField(72));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(32));
        moveService.doSimpleMove(boardService.getField(92), boardService.getField(81));
        moveService.doSimpleMove(boardService.getField(16), boardService.getField(25));
        moveService.doSimpleMove(boardService.getField(72), boardService.getField(63));

        //triple hop as a black queeen
        //| 0|<>| 2|<>| 4| 5| 6|<>| 8|<>|
        //|<>|11|<>|13|14|15|16|17|18|19|
        //|20|21|22|23|24|<>|26|27|28|<>|
        //|<>|31|<>|33|34|35|(x)|37|<>|39|
        //|40|41|42|43|44|<>|46|47|48|49|
        //|<>|51|<>|53|54|55|56|57|58|59|
        //|60|61|62|<>|64|65|66|67|68|<>|
        //|<>|71|72|73|74|75|76|77|78|79|
        //|80|<>|82|83|84|85|86|<>|88|<>|
        //|(x)|91|92|93|<>|95|<>|97|<>|99|

        int movedStoneId = boardService.getField(90).getStone().getStoneId();
        moveService.doHopMove(boardService.getField(90), boardService.getField(72));

        assertNull(boardService.getField(72).getStone());
        assertEquals(movedStoneId, boardService.getField(36).getStone().getStoneId());
    }

    @Test
    public void testMoveDilemma(){
        initializeService.initializeGame(8);

        moveService.doSimpleMove(boardService.getField(19), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(37));
        moveService.doHopMove(boardService.getField(28), boardService.getField(46));
        moveService.doHopMove(boardService.getField(55), boardService.getField(37));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(30));
        moveService.doHopMove(boardService.getField(37), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(40), boardService.getField(33));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(49), boardService.getField(40));
        moveService.doSimpleMove(boardService.getField(3), boardService.getField(10));
        moveService.doSimpleMove(boardService.getField(56), boardService.getField(49));
        moveService.doSimpleMove(boardService.getField(12), boardService.getField(19));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(35));
        moveService.doHopMove(boardService.getField(24), boardService.getField(42));
        moveService.doHopMove(boardService.getField(42), boardService.getField(56));
        moveService.doSimpleMove(boardService.getField(58), boardService.getField(49));
        moveService.doHopMove(boardService.getField(56), boardService.getField(42));
        moveService.doHopMove(boardService.getField(42), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(44), boardService.getField(35));
        moveService.doHopMove(boardService.getField(28), boardService.getField(42));
        moveService.doHopMove(boardService.getField(51), boardService.getField(33));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(33), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(26), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(53), boardService.getField(44));
        moveService.doHopMove(boardService.getField(35), boardService.getField(53));
        moveService.doHopMove(boardService.getField(60), boardService.getField(46));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(37));
        moveService.doHopMove(boardService.getField(28), boardService.getField(46));
        moveService.doSimpleMove(boardService.getField(62), boardService.getField(53));
        moveService.doHopMove(boardService.getField(46), boardService.getField(60));
        moveService.doSimpleMove(boardService.getField(24), boardService.getField(17));
        moveService.doHopMove(boardService.getField(10), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(40), boardService.getField(33));

        moveService.doHopMove(boardService.getField(24), boardService.getField(42));
        assertEquals(Color.BLACK,boardService.getField(42).getStone().getStoneColor());

        //no moves are possible for the white stone -> black wins
        //| 0|<>| 2| 3| 4|<>| 6|<>|
        //|<>| 9| 10|11|12|13|<>|15|
        //|16|17|18|<>|20|21|22|<>|
        //|24|25|26|27|28|29|30|31|
        //|32|33|34|35|36|37|38|39|
        //|40|41|<>|43|44|45|46|47|
        //|48|49|50|51|52|53|54|55|
        //|56|57|58|59|<>|61|62|63|

        assertEquals(Color.WHITE,boardService.getField(23).getStone().getStoneColor());

        //no moves are any more possible
        assertFalse(moveService.doSimpleMove(boardService.getField(23), boardService.getField(14)).getIsValid());
        assertFalse(moveService.doHopMove(boardService.getField(23), boardService.getField(14)).getIsValid());

        assertEquals(Color.BLACK, boardService.getBoard().getWinnerColor());
    }

    @Test
    public void testRevertQueenMove(){
        initializeService.initializeGame(8);

        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(33));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(39));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(51), boardService.getField(42));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(53), boardService.getField(46));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(62), boardService.getField(53));
        moveService.doSimpleMove(boardService.getField(3), boardService.getField(10));
        moveService.doSimpleMove(boardService.getField(58), boardService.getField(51));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(44), boardService.getField(37));
        moveService.doSimpleMove(boardService.getField(7), boardService.getField(14));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(35));
        moveService.doHopMove(boardService.getField(28), boardService.getField(42));
        moveService.doHopMove(boardService.getField(49), boardService.getField(35));
        moveService.doHopMove(boardService.getField(26), boardService.getField(44));
        moveService.doHopMove(boardService.getField(44), boardService.getField(58));
        moveService.doSimpleMove(boardService.getField(37), boardService.getField(28));
        moveService.doHopMove(boardService.getField(19), boardService.getField(37));
        moveService.doHopMove(boardService.getField(46), boardService.getField(28));
        moveService.doHopMove(boardService.getField(21), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(56), boardService.getField(49));
        moveService.doHopMove(boardService.getField(24), boardService.getField(42));
        moveService.doHopMove(boardService.getField(42), boardService.getField(56));
        moveService.doSimpleMove(boardService.getField(40), boardService.getField(33));
        moveService.doSimpleMove(boardService.getField(12), boardService.getField(19));
        moveService.doSimpleMove(boardService.getField(55), boardService.getField(46));
        moveService.doSimpleMove(boardService.getField(1), boardService.getField(10));
        moveService.doSimpleMove(boardService.getField(53), boardService.getField(44));
        moveService.doHopMove(boardService.getField(35), boardService.getField(53));
        moveService.doSimpleMove(boardService.getField(46), boardService.getField(37));
        moveService.doSimpleMove(boardService.getField(53), boardService.getField(62));
        moveService.doSimpleMove(boardService.getField(60), boardService.getField(53));
        moveService.doHopMove(boardService.getField(62), boardService.getField(44));
        moveService.doHopMove(boardService.getField(44), boardService.getField(30));
        moveService.doHopMove(boardService.getField(39), boardService.getField(21));
        moveService.doHopMove(boardService.getField(21), boardService.getField(7));
        moveService.doSimpleMove(boardService.getField(19), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(33), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(24), boardService.getField(17));
        moveService.doHopMove(boardService.getField(10), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(7), boardService.getField(14));
        moveService.doSimpleMove(boardService.getField(26), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(35), boardService.getField(42));
        moveService.doHopMove(boardService.getField(21), boardService.getField(35));
        moveService.doHopMove(boardService.getField(35), boardService.getField(49));

        //revert last queen double hop
        boardService.loadPreviousMove();

        assertNull(boardService.getField(49).getStone());

        //do double hop again
        moveService.doHopMove(boardService.getField(21), boardService.getField(35));
        moveService.doHopMove(boardService.getField(35), boardService.getField(49));

        assertEquals(Color.WHITE, boardService.getField(49).getStone().getStoneColor());
    }

    @Test
    public void testNormalRevert(){
        initializeService.initializeGame(8);

        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(8), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(44), boardService.getField(37));
        moveService.doHopMove(boardService.getField(26), boardService.getField(44));
        moveService.doHopMove(boardService.getField(53), boardService.getField(35));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(30));
        moveService.doSimpleMove(boardService.getField(51), boardService.getField(44));
        moveService.doSimpleMove(boardService.getField(12), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(35), boardService.getField(28));
        moveService.doHopMove(boardService.getField(21), boardService.getField(35));
        moveService.doHopMove(boardService.getField(35), boardService.getField(53));
        moveService.doHopMove(boardService.getField(62), boardService.getField(44));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(58), boardService.getField(51));
        moveService.doSimpleMove(boardService.getField(7), boardService.getField(14));
        moveService.doSimpleMove(boardService.getField(49), boardService.getField(42));
        moveService.doSimpleMove(boardService.getField(17), boardService.getField(26));
        moveService.doSimpleMove(boardService.getField(42), boardService.getField(33));
        moveService.doSimpleMove(boardService.getField(30), boardService.getField(39));
        moveService.doSimpleMove(boardService.getField(60), boardService.getField(53));
        moveService.doSimpleMove(boardService.getField(23), boardService.getField(30));
        moveService.doHopMove(boardService.getField(37), boardService.getField(23));
        moveService.doSimpleMove(boardService.getField(10), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(33), boardService.getField(24));
        moveService.doSimpleMove(boardService.getField(26), boardService.getField(35));
        moveService.doHopMove(boardService.getField(44), boardService.getField(26));
        moveService.doHopMove(boardService.getField(26), boardService.getField(8));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(24), boardService.getField(17));
        moveService.doSimpleMove(boardService.getField(28), boardService.getField(37));
        moveService.doHopMove(boardService.getField(46), boardService.getField(28));
        moveService.doHopMove(boardService.getField(28), boardService.getField(10));
        moveService.doHopMove(boardService.getField(1), boardService.getField(19));
        moveService.doSimpleMove(boardService.getField(8), boardService.getField(1));
        moveService.doSimpleMove(boardService.getField(14), boardService.getField(21));
        moveService.doSimpleMove(boardService.getField(51), boardService.getField(44));
        moveService.doSimpleMove(boardService.getField(21), boardService.getField(28));
        moveService.doSimpleMove(boardService.getField(56), boardService.getField(49));
        moveService.doSimpleMove(boardService.getField(28), boardService.getField(35));
        moveService.doHopMove(boardService.getField(44), boardService.getField(26));
        moveService.doHopMove(boardService.getField(26), boardService.getField(12));

        boardService.loadPreviousMove();

        assertNull(boardService.getField(12).getStone());
        moveService.doHopMove(boardService.getField(44), boardService.getField(26));
        moveService.doHopMove(boardService.getField(26), boardService.getField(12));
        assertEquals(Color.WHITE, boardService.getField(12).getStone().getStoneColor());




    }
}
