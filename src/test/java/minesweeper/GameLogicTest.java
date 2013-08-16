package minesweeper;

import org.junit.Before;
import org.junit.Test;

import static minesweeper.BoardStatus.*;
import static org.junit.Assert.*;


import static minesweeper.TestUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: Shiera
 * Date: 8/6/13
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameLogicTest {
    Board board;
    GameLogic gameLogic;



    @Before
    public void setup(){
        board = new Board(TestUtils.testBoardSize, bombsInTest);
        // vain etukäteen määritelty lauta
        board.setupBoard(makeTestBoard(), bombsInTest);
        gameLogic = new GameLogic(testBoardSize, bombsInTest, board );
    }

    @Test
    public void doMoveTets1(){
        gameLogic.doMove(10,10, UNCOVERED);
        gameLogic.doMove(-1,-1, MARKED);
        gameLogic.doMove(0, 4, MARKED);
        gameLogic.doMove(4,0, UNCOVERED);
        assertTrue("board should not be changed after trying illegal moves", TestUtils.isBoardCovered(gameLogic.getBoard()));
    }

    @Test
    public  void doMoveTest2(){
        assertTrue("should be able to play before win/loose",gameLogic.doMove(0,0, MARKED));
        gameLogic.doMove(2, 0, MARKED);
        gameLogic.doMove(0,1, MARKED);
        gameLogic.checkTheBoard();
        assertFalse("gameLogic should be won and no more moves should be made", gameLogic.doMove(0,0,UNCOVERED));
    }







}
