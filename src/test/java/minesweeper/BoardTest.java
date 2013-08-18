package minesweeper;

/**

 * @Author  Shiera
 * Date: 31.7.2013

 */

import UI.TileAppearance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static minesweeper.BoardStatus.*;
import static minesweeper.TestUtils.*;
import static org.junit.Assert.*;


@RunWith(JUnit4.class)
public class BoardTest {
    Board board;



    @Before
    public void setup(){
        board = new Board(TestUtils.testBoardSize, 3);

    }



    @Test
    public void testBoardIsCleanAfterCleaning() {
        board.setupBoard();
        assertFalse(" board should not be clean after setup, check method setupBoard()", isBoardClean(board));
        board.clearBoard();
        assertTrue("cleaned board should be clean, check method clearBoard()", isBoardClean(board));

    }


    /*
     *   test test all these private methods
     *   placeBombs,  coverBoard
     *   (clearBoard not tested because alredy tested)
     */
    @Test
    public void setupBoardTest1() {
        board.clearBoard();
        board.setStatusXY(0,0,UNCOVERED);
        //   before setup board should be clear and not covered everywhere
        assertTrue("board should be cleared, check test", isBoardClean(board));
        assertFalse("whole board should not be covered, check method setStatusXY()", isBoardCovered(board));
        board.setupBoard();
        // board should now have bombs, numbers and be covered
        assertTrue("The board should have bombs, none found, check placeBombs()", atLeastOneBomb(board));
        assertEquals("wrong amount of bombs placed, check placeBombs()",board.getBombAmount(), amountOfbombsFound(board));
        assertTrue("board should be covered after setup, check coverBoard()", isBoardCovered(board));
    }

    /*
       when trying to
       *C -> M = M count = count + 1
       *M -> M = M count = count
       *M -> C = C count = count -1
       *C -> C = C count = count
       *C -> U = U  count = count
       *M -> U = M  count = count
       *U -> C = U count = count
       U -> M = U count = count
       *U -> U = U count = count

     */
    @Test
    public void setStatusXYTest1(){
        board.setupBoard();
        // C -> M
        int oldMarkedCount = board.getMarkedSpacesCount();
        board.setStatusXY(0,0, MARKED);
        assertEquals("the status should be marked after marking an covered place",MARKED , board.getStatusXY(0,0));
        assertEquals("the marked count should have risen when new tile got marked", oldMarkedCount+1, board.getMarkedSpacesCount());
        // M -> M
        oldMarkedCount = board.getMarkedSpacesCount();
        board.setStatusXY(0,0, MARKED);
        assertEquals("the status should be marked after trying to mark an mark an marked tile",MARKED , board.getStatusXY(0,0));
        assertEquals("the marked count should not change when marking an marked place", oldMarkedCount, board.getMarkedSpacesCount());
        // M -> U
        board.setStatusXY(0,0, UNCOVERED);
        assertEquals("the marked count should be the same after trying to uncover an marked tile", oldMarkedCount, board.getMarkedSpacesCount());
        assertEquals("the status should be marked after trying to uncover an marked tile",MARKED, board.getStatusXY(0,0) );
        // M -> C
        board.setStatusXY(0,0, COVERED);
        assertEquals("the marked count should be 1 less when covering an marked tile", oldMarkedCount-1, board.getMarkedSpacesCount());
        assertEquals("the status should be covered after covering an marked tile",COVERED, board.getStatusXY(0,0) );


    }

    @Test
    public void setStatusXYTest2(){
        board.setupBoard();
        // C -> C
        int oldMarkedCount = board.getMarkedSpacesCount();
        board.setStatusXY(0,0, COVERED);
        assertEquals("the marked count should be the same after covering an covered tile", oldMarkedCount, board.getMarkedSpacesCount());
        assertEquals("the status should be coverd after covering an covered tile",COVERED , board.getStatusXY(0,0));
        // C -> U
        board.setStatusXY(0,0, UNCOVERED);
        assertEquals("the marked count should be the same after uncovering an covered tile", oldMarkedCount, board.getMarkedSpacesCount());
        assertEquals("the status should be uncoverd after uncovering an covered tile",UNCOVERED, board.getStatusXY(0,0) );
        // U -> U
        board.setStatusXY(0,0, UNCOVERED);
        assertEquals("the marked count should be the same after uncovering an uncovered tile", oldMarkedCount, board.getMarkedSpacesCount());
        assertEquals("the status should be uncovered after uncovering an uncovered tile",UNCOVERED , board.getStatusXY(0,0));
        // U -> C
        board.setStatusXY(0,0, COVERED);
        assertEquals("the marked count should be the same after trying to cover an uncovered tile", oldMarkedCount, board.getMarkedSpacesCount());
        assertEquals("the status should be uncovered after trying to cover an uncovered tile",UNCOVERED, board.getStatusXY(0,0) );
        // U -> M
        board.setStatusXY(0,0, MARKED);
        assertEquals("the marked count should be the same after trying to mark an uncovered tile", oldMarkedCount, board.getMarkedSpacesCount());
        assertEquals("the status should be uncovered after trying to mark an uncovered tile",UNCOVERED , board.getStatusXY(0,0));

    }

    // ---------- alempana testejä ei randomilla boardilla    ----------
    @Test
    public void configBoardTest(){
        board.setupBoard(makeTestBoard(), 3);
        assertEquals("all the numbers ar not correct", board.getBoardData(), makeTestBoardWhitNumbers());

    }

    @Test
    public void expandIf0Test(){
        board.setupBoard(makeTestBoard(), 3);
        board.setStatusXY(2, 2, UNCOVERED);
        assertEquals("should expand", UNCOVERED, board.getStatusXY(2, 2));
        assertEquals("should expand", UNCOVERED, board.getStatusXY(2, 1));
        assertEquals("should expand", UNCOVERED, board.getStatusXY(1, 2));
        assertEquals("should expand",UNCOVERED,     board.getStatusXY(1,1) );

    }

    @Test
    public void checkBoardTest(){
        board.setupBoard(makeTestBoard(), 3);
        assertFalse("Board should not be right before any marking", board.checkBoard());
        board.setStatusXY(0,0, MARKED);
        board.setStatusXY(2, 0, MARKED);
        board.setStatusXY(0,1, MARKED);
        assertTrue("Board should be right marked", board.checkBoard());

    }

    @Test
    public void getTileStatusTest(){
        board.setupBoard(makeTestBoard(), 3);
        assertEquals("", TileAppearance.GRASS, board.getPlayingTileAppearance(0, 0));
        board.setStatusXY(0,0, MARKED );
        assertEquals("", TileAppearance.FLAG, board.getPlayingTileAppearance(0, 0));
        board.setStatusXY(2,0, UNCOVERED);
        assertEquals("", TileAppearance.EXPLODEDBOMB, board.getPlayingTileAppearance(2, 0));
        board.setStatusXY(1,1, UNCOVERED);
        assertEquals("", TileAppearance.NUMBER3, board.getPlayingTileAppearance(1, 1));
    }





}