package UI.screens;

import UI.*;
import minesweeper.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Author: Shiera
 */
public class OptionScreen extends Screen{




    private UI.Button bigGame;
    private UI.Button menuButton;



    public OptionScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
    }


    @Override
    protected void whenClicked(MouseEvent e, GameLogic game) {
        int  posX = e.getX();
        int posY = e.getY();


        int mouseButton = e.getButton();

        // TODO all clickstuff

        bigGame.ifClicked(posX, posY);
        menuButton.ifClicked(posX, posY);
    }


    @Override
    protected void paintScreen(Graphics2D g2) {
        bigGame.draw(g2);
        menuButton.draw(g2);

    }




    @Override
    protected void makeButtons(BaseFrame frame){
        bigGame = new UI.Button(frame, "test.jpg", 0, 0, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.changeOptions(20, 20);
                System.out.println("change");
            }
        });
        menuButton = new UI.Button( frame, "menu.png", 100, 0, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                frame.changeToMenu();
            }
        });
    }

}

