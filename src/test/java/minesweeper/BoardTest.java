package minesweeper;

/**

 * @Author  Shiera
 * Date: 31.7.2013

 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
        assertFalse(" board should not be clean after setup", isBoardClean(board));
        board.clearBoard();
        assertTrue("cleaned board should be clean", isBoardClean(board));

    }

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