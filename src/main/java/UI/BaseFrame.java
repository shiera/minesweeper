package UI;



import UI.screens.*;
import minesweeper.BoardSizeOptions;
import minesweeper.DifficultyOptions;
import minesweeper.GameLogic;
import minesweeper.Options;

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
    private Screen help;
    private Screen optionsScreen;
    private final GameLogic logic;
    private Options options;


    /**
     *
     * @param title title of the frame
     */
    public BaseFrame(String title) {
        options = new Options();
        logic =  new GameLogic(options.getSize().getWidth(),
                               options.getSize().getHeight(),
                               options.getDifficulty().getBombAmountPercentage());
        setTitle(title);
        makeScreens();
        int initialWidth = menu.getScreenWidth();
        int initialHeight = menu.getScreenHeight();
        JComponent content = menu;
        setContentPane(content);
        setPreferredSize(new Dimension(initialWidth, initialHeight));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();
        setVisible(true);
    }

    public void setSizeOptions(BoardSizeOptions boardSize){
       options.setSize(boardSize);
    }

    public BoardSizeOptions getSizeOptions(){
         return options.getSize();
    }

    public void setDifficulty(DifficultyOptions bombAmountPercentage){
        options.setDifficulty(bombAmountPercentage);
    }

    public DifficultyOptions getDifficultyOption(){
        return   options.getDifficulty();
    }

    public boolean isSoundON() {
        return options.isSoundON();
    }

    /**
     * used to mute/ unmute the game
     */
    public void changeSoundOption() {
        options.changeSoundOptions();
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
        changeScreen(optionsScreen);
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
    private void makeScreens(){
        menu = new MenuScreen(logic, this);
        gameScreen = new BoardScreen(logic, this);
        optionsScreen = new OptionScreen(logic, this);
        help = new HelpScreen(logic, this);
    }
}