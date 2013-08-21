package UI;



import UI.screens.*;
import minesweeper.GameLogic;

import javax.swing.*;
import java.awt.*;

/**
 * The frame (JFrame) used in minesweeper
 * initialises the gameLogic and screens
 * handles the changing of screens
 */
public class BaseFrame extends JFrame {

    private Screen menu;
    private Screen gameScreen;
    private Screen options;
    private Screen help;
    private final GameLogic logic;
    private boolean soundON = true;


    /**
     *
     * @param title title of the frame
     */
    public BaseFrame(String title) {
        logic =  new GameLogic(6,12);
        setTitle(title);
        makePanels();
        int initialWidth = menu.getScreenWidth();
        int initialHeight = menu.getScreenHeight();
        JComponent content = menu;
        if (content != null) {
            setContentPane(content);
        }
        setPreferredSize(new Dimension(initialWidth, initialHeight));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();
        setVisible(true);
    }

    public boolean isSoundON() {
        return soundON;
    }

    /**
     * used to mute/ unmute the game
     */
    public void chengeSoundOption() {
        if (soundON) soundON = false;
        else soundON = true;
    }

    /**
     * changes screen to gameScreen
     */
    public void changeToGame(){
        logic.newGame();
        changeScreen(gameScreen);

    }

    /**
     * changes screen to menuScreen
     */
    public void changeToMenu(){

        changeScreen(menu);
    }

    /**
     * changes screen to optionsScreen
     */
    public void changeToOptions(){
        changeScreen(options);
    }

    /**
     * changes screen to helpScreen
     */
    public void changeToHelp(){
        changeScreen(help);
    }

    /**
     * does generic stuff done when a screen is changes
     * @param pane the screen used after changing
     */
    private void changeScreen(Screen pane) {
        setPreferredSize(new Dimension(pane.getScreenWidth(), pane.getScreenHeight()));
        setContentPane(pane);
        pane.open();
        pack();
        repaint();
    }


    /**
     * called from constructor
     * makes all the screens used in minesweeper
     */
    private void makePanels(){
        menu = new MenuScreen(logic, this);
        gameScreen = new BoardScreen(logic, this);
        options = new OptionScreen(logic, this);
        help = new HelpScreen(logic, this);
    }
}