package minesweeper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;



import static minesweeper.TestUtils.*;

import static org.junit.Assert.*;


import static minesweeper.BoardStatus.*;

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
    public void playTest(){
        gameLogic.play();
        // TODO selvitä miten testata jotain joka vaatii inputtia
    }




}
