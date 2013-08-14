package UI;

/**
 * Author: Shiera
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.sun.webpane.platform.graphics.RenderTheme;
import minesweeper.Board;
import minesweeper.BoardStatus;
import minesweeper.GameLogic;
import minesweeper.TileAppearence;



public class BoardPanel extends JPanel{


    public static final int LEFTBUTTON = MouseEvent.BUTTON1;
    public static final int RIGHTBUTTON= MouseEvent.BUTTON3;
    private final Board board;
    private int tileSize = 32;
    private int xBoardOrigoCord = 0;
    private int yBoardOrigoCord = 0;

    public BoardPanel(final Board board, final GameLogic game) {
        this.board = board;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = (e.getX()-xBoardOrigoCord)/tileSize;
                int y = (e.getY()-yBoardOrigoCord)/tileSize;
                int button = e.getButton();
                System.out.println("clicked x = " + x + " y = " + y + " button " + e.getButton());
                // todo gamelogicia kutsutaan ja  vain gamelogic käskee boardia
                // frame päättää paneelien välillä vaihtamisen
                if (button == LEFTBUTTON){
                    board.setStatusXY(x, y, BoardStatus.UNCOVERED);
                }
                if (button == RIGHTBUTTON){
                    board.setStatusXY(x, y, BoardStatus.MARKED);
                }
                repaint();

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
        // draw woard
        for (int y = 0; y < board.getBoardSize(); y++) {
            for (int x = 0; x <board.getBoardSize() ; x++) {
                board.getTileAppearance(x,y).drawImage(x,y,g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
            }
        }
    }

}