package UI.secreens;

import UI.*;
import minesweeper.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Author: Shiera
 */
public class OptionScreen extends JPanel{


    public static final int LEFTBUTTON = MouseEvent.BUTTON1;
    public static final int RIGHTBUTTON= MouseEvent.BUTTON3;

    private UI.Button bigGame;
    private UI.Button menuButton;

    private GameLogic gameLogic;


    public OptionScreen(BaseFrame frame, GameLogic gameLogic){
        makeButtons(frame);
        addMouseListener();
        this.gameLogic = gameLogic;

    }

    private void addMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int  posX = e.getX();
                int posY = e.getY();


                int mouseButton = e.getButton();

                // TODO all clickstuff

                bigGame.ifClicked(posX, posY);
                menuButton.ifClicked(posX, posY);

                repaint();

            }
        });
    }


    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        // draw

        bigGame.draw(g2);
        menuButton.draw(g2);


    }




    private void makeButtons(BaseFrame frame){
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

