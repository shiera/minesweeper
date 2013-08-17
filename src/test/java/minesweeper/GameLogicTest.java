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

    @Test
    public void bombAmountTest(){
        assertEquals("translate from bombAmount to bombPercent is wrong bombamountpercent is " +gameLogic.getBombAmountPercent()
                ,bombsInTest, gameLogic.getBombAmount());

    }


    @Test
    public void setSizeTest(){
        double percent = gameLogic.getBombAmountPercent();
        gameLogic.setSize(testBoardSize*2,testBoardSize);
        assertEquals("board width wrong after change size", testBoardSize*2,gameLogic.getBoard().getBoardWidth());
        assertEquals("board height wrong after change size", testBoardSize ,gameLogic.getBoard().getBoardHeight());
        assertEquals("board should have same bombAmountPercent after changing size",  percent, gameLogic.getBombAmountPercent(), 0.1);
        assertEquals("board should have double amount of bombs (from gamelogic)", bombsInTest*2, gameLogic.getBombAmount());
        assertEquals("board should have double amount of bombs (from board)", bombsInTest*2, gameLogic.getBoard().getBombAmount());
    }


    @Test
    public void  setBombAmountPercentTest(){
        gameLogic.setBombAmountPercent(11.12);
        assertEquals("board should have 1 bomb (from gamelogic)", 1, gameLogic.getBombAmount());
        assertEquals("board should have 1 bomb (from board)", 1, gameLogic.getBoard().getBombAmount());
        assertEquals("board should have new bombAmountPercent",  11.12, gameLogic.getBombAmountPercent(), 0.1);
    }







}
