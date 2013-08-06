package minesweeper;

/**

 * @Author  Shiera
 * Date: 31.7.2013

 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;


import static minesweeper.BoardStatus.*;

@RunWith(JUnit4.class)
public class BoardTest {
    Board board;
    private int testBoardSize = 3;


    @Before
    public void setup(){
        board = new Board(testBoardSize, 3);

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
        assertEquals("all the numbers ar not correct",board.getBoardData(), makeTestBoardWhitNumbers() );

    }

    @Test
    public void expandIf0Test(){
        board.setupBoard(makeTestBoard(), 3);
        board.setStatusXY(2 ,2 ,UNCOVERED);
        assertEquals("should expand",UNCOVERED, board.getStatusXY(2,2) );
        assertEquals("should expand",UNCOVERED,  board.getStatusXY(2,1) );
        assertEquals("should expand",UNCOVERED,  board.getStatusXY(1,2) );
        assertEquals("should expand",UNCOVERED,     board.getStatusXY(1,1) );

    }




    private int amountOfbombsFound(Board board){
        int bombsFound = 0;
        int[][] boardData = board.getBoardData();
        for (int y = 0; y <boardData.length ; y++) {
            for (int x = 0; x <boardData[y].length ; x++) {
                if (boardData[y][x] == board.BOMB) bombsFound ++;
            }
        }
        return bombsFound;
    }

    private boolean atLeastOneBomb(Board board){
        int[][] boardData = board.getBoardData();
        for (int y = 0; y <boardData.length ; y++) {
            for (int x = 0; x <boardData[y].length ; x++) {
                if (boardData[y][x] == board.BOMB) return true;
            }
        }
        return false;
    }

    private boolean isBoardCovered(Board board){
        BoardStatus[][] boardStatus = board.getBoardStatus() ;
        for (int y = 0; y <boardStatus.length ; y++) {
            for (int x = 0; x <boardStatus[y].length ; x++) {
                if (boardStatus[y][x] != COVERED) return false;
            }
        }
        return true;
    }

    /**
     *
     * @param board testgameboard
     * @return  true if board is clean
     */
    private boolean isBoardClean(Board board){
        int[][] boardData = board.getBoardData();
        for (int y = 0; y <boardData.length ; y++) {
            for (int x = 0; x <boardData[y].length ; x++) {
                if (boardData[y][x] != 0) return false;
            }
        }
        return true;
    }

    private int[][] makeTestBoard(){
        int[][] testBoard = new int[testBoardSize][3];
        testBoard[0][0] = -1;
        testBoard[0][2] = -1;
        testBoard[1][0] = -1;
        return testBoard;
    }

    private int[][] makeTestBoardWhitNumbers(){
        int[][] testBoard = makeTestBoard();
        testBoard[0][1] = 3;
        testBoard[1][1] = 3;
        testBoard[1][2] = 1;
        testBoard[2][0] = 1;
        testBoard[2][1] = 1;
        return testBoard;
    }

}