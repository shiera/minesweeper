package UI.screens;

import UI.BaseFrame;
import minesweeper.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import static UI.TileAppearence.OUTSIDE;

/**
 * Author: Shiera
 */
abstract public class Screen extends JPanel {
    public static final int LEFTBUTTON = MouseEvent.BUTTON1;
    public static final int RIGHTBUTTON= MouseEvent.BUTTON3;
    protected int tileSize = 32;
    protected final GameLogic gameLogic;

    public Screen(final GameLogic game, BaseFrame frame) {
        this.gameLogic = game;

        makeButtons(frame);
        addMouse(game);

    }

    public void open(){

    }



    abstract public int getScreenWidth();

    abstract public int getScreenHeight();


    /**
     * make buttons here if frame uses buttons
     * @param frame  used Baseframe
     */
    protected void makeButtons(BaseFrame frame){
    }

    @Override
    final protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        paintScreen(g2);

    }

    protected void paintBackground(Graphics2D g2){
        for (int tileY = 0; tileY <= getHeight()/tileSize; tileY++) {
            for (int tileX = 0; tileX <= (getWidth()/tileSize) ; tileX++) {
                OUTSIDE.drawImage(tileX, tileY, g2, tileSize, 0, 0);
            }
        }
    }



    protected abstract void paintScreen(Graphics2D g2);

    /**
     * do this if mouse was clicked
     * @param e

     */
    protected void whenClicked(MouseEvent e) {
    }

    /**
     * mouse moves not tracked on default
     * @param e
     * @return  false on default, repaints after mouseMove if return is true
     */
    protected boolean mouseMove(MouseEvent e){
        return false;
    }

    /**
     *
     * @param game   used GameLogic instant
     */
    private void addMouse(final GameLogic game) {
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
                repaint();

            }


        });
    }



}
