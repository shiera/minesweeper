package UI;


import UI.screens.BoardScreen;
import UI.screens.MenuScreen;
import UI.screens.OptionScreen;
import minesweeper.GameLogic;

import javax.swing.*;
import java.awt.*;

/**
 * A Swing JFrame with sensible default settings.
 */
public class BaseFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;
    private JComponent menu;
    private JComponent gameBoard;
    private JComponent options;
    private GameLogic logic;

    public BaseFrame (String title){
        this(title, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }



    public BaseFrame(String title,  int initialWidth, int initialHeight) {
        setTitle(title);
        makePanels();
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

    private void changeScreen(JComponent pane) {
        setContentPane(pane);
        pack();
        repaint();
    }



    private void makePanels(){
        menu = new MenuScreen(logic, this);
        logic =  new GameLogic(5,5);
        gameBoard = new BoardScreen(logic, this);
        options = new OptionScreen(logic, this);
    }
}