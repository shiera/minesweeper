package minesweeper;


import UI.BaseFrame;
import UI.BoardPanel;

import static minesweeper.BoardStatus.*;

/**
 *
 * @author shiera
 */
public class Main {

    public static void main(String[] args) {
        GameLogic game = new GameLogic(5, 5);
        game.newGame();
        BoardPanel panel = new BoardPanel(game);
        new BaseFrame("minesweeper", panel );



    }
}
