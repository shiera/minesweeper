package UI.screens;

import UI.BaseFrame;
import minesweeper.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static UI.TileAppearance.OUTSIDE;

/**
 * Author: Shiera
 * Abstract superclass of the screen, does generic stuff done in all screens
 * Extend JPanel
 */
abstract public class Screen extends JPanel {
    public static final int LEFTBUTTON = MouseEvent.BUTTON1;
    public static final int RIGHTBUTTON= MouseEvent.BUTTON3;
    protected int tileSize = 32;
    protected final GameLogic gameLogic;


    /**
     * Generic constructor for screens
     * @param game  GameLogic used in game
     * @param frame  Frame used in game
     */
    public Screen(final GameLogic game, BaseFrame frame) {
        this.gameLogic = game;
        makeButtons(frame);
        addMouse();
    }

    /**
     * does what should be done when opening a screen
     * does nothing on default
     */
    public void open(){
    }

    /**
     * @return should return the default width of the screen
     */
    abstract public int getScreenWidth();

    /**
     * @return should return the default height of the screen
     */
    abstract public int getScreenHeight();

    /**
     * make buttons here if frame uses buttons
     * does nothing on default
     * @param frame  used BaseFrame
     */
    protected void makeButtons(BaseFrame frame){
    }

    /**
     * changes Graphics to Graphics2D
     * calls method that does the painting
     * @param g Graphics
     */
    @Override
    final protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        paintScreen(g2);
    }

    /**
     * paints the whole screen whit TileAppearance.OUTSIDE
     * @param g2 Graphics2D
     */
    protected void paintBackground(Graphics2D g2){
        for (int tileY = 0; tileY <= getHeight()/tileSize; tileY++) {
            for (int tileX = 0; tileX <= (getWidth()/tileSize) ; tileX++) {
                OUTSIDE.drawImage(tileX, tileY, g2, tileSize, 0, 0);
            }
        }
    }

    /**
     * all painting of the screen happens here
     * @param g2 Graphics2D
     */
    protected abstract void paintScreen(Graphics2D g2);

    /**
     * do this when mouse was clicked
     * does nothing on default
     * @param e
     */
    protected void whenClicked(MouseEvent e) {
    }

    /**
     * Will be called whenever the mouse moves or drags
     * override if something should happen
     * @param e
     * @return  returns false on default, return true when overriding if
     *          screen should repaint after move/drag
     */
    protected boolean mouseMove(MouseEvent e){
        return false;
    }

    /**
     * Adds mouseAdapter and MouseMotionListener to screen
     * Should not be overridden
     * calls mouseMove()/whenClicked() if mouse is moved/dragged or clicked
     */
    private final void addMouse() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseMove(e)){
                    repaint();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mouseMove(e)){
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                whenClicked(e);
              /**
     * make buttons here if frame uses buttons
     * does nothing on default
     * @param frame  used BaseFrame
     */
      repaint();
            }
        });
    }


}
