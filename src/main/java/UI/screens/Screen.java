package UI.screens;

import UI.BaseFrame;
import minesweeper.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Author: Shiera
 */
abstract public class Screen extends JPanel {
    public static final int LEFTBUTTON = MouseEvent.BUTTON1;
    public static final int RIGHTBUTTON= MouseEvent.BUTTON3;

    protected GameLogic gameLogic;

    public Screen(final GameLogic game, BaseFrame frame) {

        this.gameLogic = game;

        makeButtons(frame);
        addMouse(game);

    }

    public int getScreenWidth(){
        return 800;
    }

    public int getScreenHeight(){
        return 800;
    }

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

    protected abstract void paintScreen(Graphics2D g2);

    /**
     * do this if mouse was clicked
     * @param e
     * @param game  used GameLogic
     */
    protected void whenClicked(MouseEvent e, GameLogic game) {
    }

    /**
     * mouse moves not tracked on default
     * @param e
     * @param game  used GameLogic instant
     * @return  false on default, repaints after mouseMove if return is true
     */
    protected boolean mouseMove(MouseEvent e, GameLogic game){
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
                if (mouseMove(e, game)){
                    repaint();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mouseMove(e, game)){
                    repaint();
                }

            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                whenClicked(e, game);
                repaint();

            }


        });
    }



}
