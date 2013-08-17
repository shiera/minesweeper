package UI;



import UI.screens.*;
import minesweeper.GameLogic;

import javax.swing.*;
import java.awt.*;

/**
 * A Swing JFrame with sensible default settings.
 */
public class BaseFrame extends JFrame {

    private Screen menu;
    private Screen gameBoard;
    private Screen options;
    private final GameLogic logic;





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








    public void changeToGame(){
        logic.newGame();
        changeScreen(gameBoard);

    }

    public void changeToMenu(){

        changeScreen(menu);
    }

    public void changeToOptions(){
        changeScreen(options);
    }

    private void changeScreen(Screen pane) {
        setPreferredSize(new Dimension(pane.getScreenWidth(), pane.getScreenHeight()));
        setContentPane(pane);
        pane.open();
        pack();
        repaint();
    }



    private void makePanels(){
        menu = new MenuScreen(logic, this);

        gameBoard = new BoardScreen(logic, this);
        options = new OptionScreen(logic, this);
    }
}