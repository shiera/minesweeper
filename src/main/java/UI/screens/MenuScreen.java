package UI.screens;

import UI.*;
import UI.Button;
import minesweeper.GameLogic;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Author: Shiera
 */
public class MenuScreen extends Screen {


    private Button startButton;
    private Button optionButton;
    private Button highScoreButton;

    Picture logo = new Picture("logo.png");
    Picture menuLogo = new Picture("menuLogo.png");

    public MenuScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
    }


    @Override
    public int getScreenWidth() {
        return 288;
    }

    @Override
    public int getScreenHeight() {
        return 400;
    }

    @Override
    protected void whenClicked(MouseEvent e) {
        int  posX = e.getX();
        int posY = e.getY();

        startButton.ifClicked(posX, posY);
        optionButton.ifClicked(posX, posY);
        highScoreButton.ifClicked(posX, posY);

    }


    protected void paintScreen(Graphics2D g2) {

        paintBackground(g2);
        logo.draw(g2, 64, 0);
        menuLogo.draw(g2, 64, 64);
        startButton.draw(g2);
        optionButton.draw(g2);
        highScoreButton.draw(g2);
    }




    @Override
    protected void makeButtons(BaseFrame frame){
        startButton = new Button(frame, "start.png", 96, 160, new ButtonHandler() {
               @Override
               public void onButtonClick(BaseFrame frame) {
                     frame.changeToGame();
                   System.out.println("change");
               }
           });
        optionButton = new Button(frame, "options.png", 96, 224, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                frame.changeToOptions();
                System.out.println("change");
            }
        });
        highScoreButton = new Button(frame,"highScore.png", 96, 288, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                //TODO HIGHSCORE
            }
        });
    }

}
