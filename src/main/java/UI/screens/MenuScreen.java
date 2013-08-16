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
public class MenuScreen extends Screen {


    private UI.Button startButton;
    private UI.Button optionButton;

    public MenuScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
    }


    @Override
    protected void whenClicked(MouseEvent e, GameLogic game) {
        int  posX = e.getX();
        int posY = e.getY();


        int mouseButton = e.getButton();
        startButton.ifClicked(posX, posY);
        optionButton.ifClicked(posX, posY);
        // TODO all clickstuff
    }


    protected void paintScreen(Graphics2D g2) {
        // draw
        startButton.draw(g2);
        optionButton.draw(g2);

    }




    @Override
    protected void makeButtons(BaseFrame frame){
           startButton = new UI.Button(frame, "test.jpg", 10, 10, new ButtonHandler() {
               @Override
               public void onButtonClick(BaseFrame frame) {
                     frame.changeToGame();
                   System.out.println("change");
               }
           });
        optionButton = new UI.Button(frame, "options.png", 100, 10, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                frame.changeToOptions();
                System.out.println("change");
            }
        });
    }

}
