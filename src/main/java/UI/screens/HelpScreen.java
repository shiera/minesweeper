package UI.screens;

import UI.*;
import UI.Button;
import minesweeper.GameLogic;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Author: Shiera
 */
public class HelpScreen extends Screen {


    private UI.Button menuButton;
    private Button startButton;

    //  help pictures
    private Picture leftButton = new Picture("leftMouse.png");
    private Picture rightButton = new Picture("rightMouse.png");
    private Picture numberHelp = new Picture("numberHelp.png");
    private Picture exploded = new Picture("exploded.png");


    /**
     * Calls default constructor of screen
     * @param game  GameLogic used in game
     * @param frame Frame used in game
     */
    public HelpScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
    }




    @Override
    public int getScreenWidth() {
        return 800;
    }

    @Override
    public int getScreenHeight() {
        return 600;
    }

    @Override
    protected void whenClicked(MouseEvent e) {
        int posX = e.getX();
        int posY = e.getY();
        boolean buttonClicked = false;
        if (menuButton.ifClicked(posX, posY)) buttonClicked = true;
        if (startButton.ifClicked(posX, posY)) buttonClicked = true;
        if (soundButton.ifClicked(posX, posY)) buttonClicked = true;
        playButtonSound(buttonClicked);
    }



    @Override
    protected void paintScreen(Graphics2D g2) {
        paintBackground(g2);
        drawHelpText(g2);
        menuButton.draw(g2);
        startButton.draw(g2);
        soundButton.draw(g2);
        drawHelpPic(g2);
    }

    /**
     *  draws icons to helpScreen
     * @param g2 Graphics 2D
     */
    private void drawHelpPic(Graphics2D g2){

        leftButton.draw(g2, 320, 140);
        rightButton.draw(g2, 650, 200);
        numberHelp.draw(g2, 483,80);
        exploded.draw(g2, 400, 260);
    }

    /**
     * draws helptext
     * @param g2 Graphics2D
     */
    private void drawHelpText(Graphics2D g2){
        g2.setColor(Color.WHITE);
        g2.setFont(FONT);
        g2.drawString("Welcome to minesweeper!",100,64);
        g2.drawString("Find out where all the bombs are", 100, 128);
        g2.drawString("Dig with left mouse button. ", 100, 160);
        g2.drawString("The numbers tell how many bombs are close by.", 100, 192);
        g2.drawString("Put flags where you think there's a bomb with the right mouse button.",100, 224);
        g2.drawString("When all flags are placed, press the check button.",100 ,256);
        g2.drawString("You will loose if you digg up a bomb ",100 ,288);
        g2.drawString("or if the flags are placed wrongly when you check",100 ,320);


    }

    @Override
    protected void makeSounds() {
        super.makeSounds();
    }

    @Override
    protected void makeButtons(BaseFrame frame) {
        super.makeButtons(frame);
        menuButton = new UI.Button( frame, "menu.png", 0, 0, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                frame.changeToMenu();
            }
        });
        startButton = new Button(frame, "start.png", 64, 0, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                frame.changeToGame();
            }
        });
    }
}
