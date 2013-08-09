package UI;

/**
 * Created with IntelliJ IDEA.
 * User: cec
 * Date: 8/5/13
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */

import minesweeper.Board;
import minesweeper.BoardStatus;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Board board = new Board(3,3);
        board.setupBoard();
        ImagePanel kuvapaneeli = new ImagePanel(board);
        new SimpleFrame("mun peli!1", kuvapaneeli );
        System.out.println();
        Thread.sleep(1000);
        board.setStatusXY(0,0, BoardStatus.UNCOVERED);
        kuvapaneeli.repaint();


    }
}
