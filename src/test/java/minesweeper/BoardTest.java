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

    @Before
    public void setup(){
        board = new Board(3, 3);

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
    public void setupBoardTest() {
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

    //TODO
    public void setStatusXYTest(){
       // etukäteen tehty ja manuaalisesti testattu metodi... pitäisikö tehdä junit testi
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


}