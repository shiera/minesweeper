package UI;

/**
 * Author: Shiera
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import minesweeper.Board;
import minesweeper.GameLogic;
import minesweeper.TileAppearence;

import static minesweeper.BoardStatus.*;


public class BoardPanel extends JPanel{


    public static final int LEFTBUTTON = MouseEvent.BUTTON1;
    public static final int RIGHTBUTTON= MouseEvent.BUTTON3;
    private Board board;
    private GameLogic game;
    private int tileSize = 32;
    private int xBoardOrigoCord = 32;
    private int yBoardOrigoCord = 32;
    private int lastCordinateOfBoardX;
    private int lastCordinateOfBoardY;

    public BoardPanel(final GameLogic game) {
        this.board = game.getBoard();
        this.game = game;
        lastCordinateOfBoardX = (board.getBoardSize()*tileSize)+ xBoardOrigoCord;
        lastCordinateOfBoardY = (board.getBoardSize()*tileSize)+ yBoardOrigoCord;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = (e.getX()-xBoardOrigoCord)/tileSize;
                int y = (e.getY()-yBoardOrigoCord)/tileSize;
                int button = e.getButton();
                System.out.println("clicked x = " + x + " y = " + y + " button " + e.getButton());

                // frame päättää paneelien välillä vaihtamisen
                if (button == LEFTBUTTON){

                    game.doMove(x, y, UNCOVERED);
                }
                if (button == RIGHTBUTTON){
                    if (board.getStatusXY(x,y) == MARKED){
                        game.doMove(x, y, COVERED);
                    }
                    else  game.doMove(x, y, MARKED);
                }
                // jos painettiin valmisnappia
                if (button == LEFTBUTTON && game.flagsLeft() == 0 && x == board.getBoardSize()/2 && y == board.getBoardSize() +2 ) {
                    game.checkTheBoard();
                }

                repaint();
                if (!game.isPlaying()){
                    if (game.isHasWon()){
                        // TODO print winscreen
                    }
                    else{
                        // TODO game is lost
                    }
                }

            }
        });
    }

    /**
     * draws grapics
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        // draw board
        for (int y = 0; y < board.getBoardSize(); y++) {
            for (int x = 0; x <board.getBoardSize() ; x++) {
                board.getTileAppearance(x,y).drawImage(x,y,g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
            }
        }

        g2.drawString("Flags left: " + game.flagsLeft() , lastCordinateOfBoardX/2 , lastCordinateOfBoardY + tileSize);
        // draw if flags used
        if (game.flagsLeft() == 0){
            g2.drawString("No flags left to put, press button to check" , tileSize , lastCordinateOfBoardY + (2*tileSize));
           TileAppearence.GRASS.drawImage(board.getBoardSize()/2,board.getBoardSize() +2,g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
        }
    }

}