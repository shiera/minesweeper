package UI;

/**
 * Author: Shiera
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import minesweeper.Board;
import minesweeper.TileAppearence;



public class ImagePanel extends JPanel{


    private Board board;

    public ImagePanel(Board board) {
        this.board = board;
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
                board.getTileAppearance(x,y).drawImage(x,y,g2);
            }
        }
    }

}