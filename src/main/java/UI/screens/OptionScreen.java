package UI.screens;

import UI.*;
import UI.Button;
import minesweeper.GameLogic;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Author: Shiera
 * extends Screen
 * shows and takes options
 */
public class OptionScreen extends Screen{

    private Button largeGame;
    private Button mediumGame;
    private Button smallGame;
    private Button menuButton;
    private Button hugeGame;
    private Button easyGame;
    private Button hardGame;
    private Button nightmareGame;

    Picture optionsLogo = new Picture("optionsLogo.png");

    private  ArrayList<Button> buttons;

    /**
     * Constructor that calls the default constructor of screen
     * also toggles on the buttons for the default settings
     * @param game used GameLogic
     * @param frame  used frame
     */
    public OptionScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
        smallGame.toggleOn();
        easyGame.toggleOn();
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
        for (Button button : buttons) {
             button.ifClicked(posX, posY);
        }
    }

    /**
     *  paints option screen
     * @param g2 Graphics2D
     */
    @Override
    protected void paintScreen(Graphics2D g2) {
        paintBackground(g2);
        optionsLogo.draw(g2, 64, 32);
        g2.setColor(Color.WHITE);
        g2.drawString("change game Size ", 32, 105);
        g2.drawString("change amount ", 160, 105);
        g2.drawString("of bombs", 160, 120);

        for (Button button : buttons) {
            button.draw(g2);
        }

    }


    /**
     * makes buttons and adds them to ArrayList<Button> buttons
     * @param frame  used BaseFrame
     */
    @Override
    protected void makeButtons(BaseFrame frame){
        buttons = new ArrayList<Button>();
        menuButton = new Button( frame, "menu.png", 0, 0, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                frame.changeToMenu();
            }
        });
        buttons.add(menuButton);
        makeBombPercentButton(frame);
        makeBoardSizeButton(frame);

    }

    /**
     * makes all boardSize toggle Buttons
     * buttons handle changing of board size
     * @param frame
     */
    private void makeBoardSizeButton(BaseFrame frame){
        hugeGame = new Button(frame, "hugeUnToggle.png","hugeToggle.png", 32, 318, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.setSize(40,20);
                hugeGame.toggleOn(smallGame, mediumGame, largeGame);
            }
                                                                                   of board size
        });
        buttons.add(hugeGame);
        largeGame = new Button(frame, "largeUnToggle.png","largeToggle.png", 32, 254, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.setSize(20, 20);
                largeGame.toggleOn(smallGame, mediumGame, hugeGame);
            }

        });
        buttons.add(largeGame);
        mediumGame = new Button(frame, "mediumUnToggle.png", "mediumToggle.png", 32, 192, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.setSize(12, 12);
                mediumGame.toggleOn(smallGame, largeGame, hugeGame);
            }
        });
        buttons.add(mediumGame);
        smallGame = new Button(frame, "smallUnToggle.png", "smallToggle.png", 32, 128, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.setSize(5,5);
                smallGame.toggleOn(mediumGame, largeGame, hugeGame);

            }
        });
        buttons.add(smallGame);
    }


    /**
     * makes all bombAmount toggle buttons
     * buttons handle changing bombAmount(percentage)
     * @param frame
     */
    private void makeBombPercentButton(BaseFrame frame){
        easyGame = new Button(frame, "easyUnToggle.png","easyToggle.png", 160, 128, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.setBombAmountPercent(12);
                easyGame.toggleOn(hardGame, nightmareGame);
            }

        });
        buttons.add(easyGame);
        hardGame = new Button(frame, "hardUnToggle.png", "hardToggle.png", 160, 192, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.setBombAmountPercent(18);
                hardGame.toggleOn(easyGame, nightmareGame);
            }
        });
        buttons.add(hardGame);
        nightmareGame = new Button(frame, "nightmareUnToggle.png", "nightmareToggle.png", 160, 254, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.setBombAmountPercent(25);
                nightmareGame.toggleOn(hardGame, easyGame);

            }
        });
        buttons.add(nightmareGame);

    }

}

