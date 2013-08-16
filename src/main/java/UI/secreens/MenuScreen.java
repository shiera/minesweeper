package UI.secreens;

import UI.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Author: Shiera
 */
public class MenuScreen extends JPanel {
    public static final int LEFTBUTTON = MouseEvent.BUTTON1;
    public static final int RIGHTBUTTON= MouseEvent.BUTTON3;

    private UI.Button startButton;
    private UI.Button optionButton;


    public MenuScreen(BaseFrame frame){
         makeButtons(frame);
         addMouseListener();

    }

    private void addMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int  posX = e.getX();
                int posY = e.getY();


                int mouseButton = e.getButton();
                startButton.ifClicked(posX, posY);
                optionButton.ifClicked(posX, posY);
                // TODO all clickstuff

                repaint();

            }
        });
    }


    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        // draw
        startButton.draw(g2);
        optionButton.draw(g2);

    }




    private void makeButtons(BaseFrame frame){
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
