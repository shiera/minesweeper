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

    private BufferedImage dirtImage;
    private BufferedImage bombImage;
    private BufferedImage grassImage;
    private Board board;

    public ImagePanel(Board board) {
        try {
            dirtImage = ImageIO.read(new File("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"));
            grassImage = ImageIO.read(new File("//home//cec//projects//minesweeper//src//pictures//grass.jpg"));
            bombImage = ImageIO.read(new File("//home//cec//projects//minesweeper//src//pictures//bomb.jpg"));
        } catch (IOException ex) {
            // handle exception...
        }
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);

        g2.drawImage(grassImage,32,32, null);
        for (int y = 0; y < board.getBoardSize(); y++) {
            for (int x = 0; x <board.getBoardSize() ; x++) {
                if (board.getTileAppearance(x, y ) == TileAppearence.GRASS)  {
                    g2.drawImage(grassImage, 32*x, 32*y, null);
                }
                else if (board.getTileAppearance(x,y) == TileAppearence.BOMBFIELD){
                    g2.drawImage(bombImage, 32*x, 32*y, null);
                }
                else{
                    g2.drawImage(dirtImage, 32*x, 32*y, null);
                }

            }
        }
    }

}