package UI.screens;

import UI.*;
import UI.Button;
import minesweeper.GameLogic;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Author: Shiera
 * extends Screen
 * shows menu
 */
public class MenuScreen extends Screen {


    private Button startButton;
    private Button optionButton;
    private Button helpButton;

    Picture logo = new Picture("logo.png");
    Picture menuLogo = new Picture("menuLogo.png");



    /**
     * Constructor that calls the default constructor of screen
     * @param game used GameLogic
     * @param frame  used frame
     */
    public MenuScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
    }

    /**
     * @return returns the default width of the screen
     */
    @Override
    public int getScreenWidth() {
        return 288;
    }

    /**
     * @return returns the default height of the screen
     */
    @Override
    public int getScreenHeight() {
        return 400;
    }

    /**
     * checks if buttons was clicked when mouse clicked
     * @param e
     */
    @Override
    protected void whenClicked(MouseEvent e) {
        int  posX = e.getX();
        int posY = e.getY();
        boolean buttonClicked = false;
        if (startButton.ifClicked(posX, posY)) buttonClicked = true;
        if (optionButton.ifClicked(posX, posY)) buttonClicked = true;
        if (helpButton.ifClicked(posX, posY)) buttonClicked = true;
        if (soundButton.ifClicked(posX, posY)) buttonClicked = true;
        playSound(buttonClicked);

    }

    public void playSound(boolean buttonClicked){
        if (buttonClicked && frame.isSoundON()) selectButton.play();
    }

    /**
     * paints menuScreen
     * @param g2 Graphics2D
     */
    protected void paintScreen(Graphics2D g2) {
        paintBackground(g2);
        logo.draw(g2, 64, 0);
        menuLogo.draw(g2, 64, 64);
        startButton.draw(g2);
        optionButton.draw(g2);
        helpButton.draw(g2);
        soundButton.draw(g2);
    }


    /**
     * makes all the buttons in the game
     * @param frame  used BaseFrame
     */
    @Override
    protected void makeButtons(BaseFrame frame){
        super.makeButtons(frame);
        startButton = new Button(frame, "start.png", 96, 160, new ButtonHandler() {
               @Override
               public void onButtonClick(BaseFrame frame) {
                     frame.changeToGame();
               }
           });
        optionButton = new Button(frame, "options.png", 96, 224, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                frame.changeToOptions();
            }
        });
        helpButton = new Button(frame,"help.png", 96, 288, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                frame.changeToHelp();
            }
        });

    }

}
