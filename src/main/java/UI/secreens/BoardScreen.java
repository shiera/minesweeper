package UI.secreens;

/**
 * Author: Shiera
 */
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import UI.*;
import minesweeper.GameLogic;

import static minesweeper.BoardStatus.*;


public class BoardScreen extends JPanel{


    public static final int LEFTBUTTON = MouseEvent.BUTTON1;
    public static final int RIGHTBUTTON= MouseEvent.BUTTON3;

    private boolean lost;

    private GameLogic game;

    private int tileSize = 32;

    private int xBoardOrigoCord = 32;
    private int yBoardOrigoCord = 32;



    private UI.Button checkButton;
    private UI.Button menuButton;


    public BoardScreen(final GameLogic game, BaseFrame frame) {
        System.out.println("start");

        this.game = game;



        makeButtons(frame);
        addMouseListener(game);

    }

    private void addMouseListener(final GameLogic game) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int posX = e.getX();
                int posY = e.getY();
                int tileX = (posX - xBoardOrigoCord) / tileSize;
                int tileY = (posY - yBoardOrigoCord) / tileSize;
                if (posX < xBoardOrigoCord || posY < yBoardOrigoCord) {
                    tileX = -1;
                    tileY = -1;

                }


                int mouseButton = e.getButton();


                // frame päättää paneelien välillä vaihtamisen
                if (mouseButton == LEFTBUTTON) {
                    game.doMove(tileX, tileY, UNCOVERED);
                }
                if (mouseButton == RIGHTBUTTON) {
                    if (game.getBoard().getStatusXY(tileX, tileY) == MARKED) {
                        game.doMove(tileX, tileY, COVERED);
                    } else game.doMove(tileX, tileY, MARKED);
                }

                // jos painettiin valmisnappia
                if (mouseButton == LEFTBUTTON && game.flagsLeft() == 0) {
                    checkButton.ifClicked(posX, posY);
                }

                menuButton.ifClicked(posX, posY);


                if (!game.isPlaying()) {
                    if (game.isHasWon()) {
                        // TODO print winscreen
                    } else {
                        // TODO game is lost
                        lost = true;
                    }
                }
                repaint();

            }
        });
    }


    /**
     * draws grapics
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        int lastCordinateOfBoardX = (game.getBoard().getBoardSize()*tileSize)+ xBoardOrigoCord;
        int lastCordinateOfBoardY = (game.getBoard().getBoardSize()*tileSize)+ yBoardOrigoCord;
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        // draw board
        for (int y = 0; y < game.getBoard().getBoardSize(); y++) {
            for (int x = 0; x <game.getBoard().getBoardSize() ; x++) {
                game.getBoard().getTileAppearance(x, y).drawImage(x,y,g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
            }
        }
        g2.drawString("Flags left: " + game.flagsLeft() , lastCordinateOfBoardX/2 , lastCordinateOfBoardY + tileSize);
        // draw if flags used
        if (  game.flagsLeft() == 0){
            g2.drawString("No flags left to put, press button to check" , tileSize , lastCordinateOfBoardY + (2*tileSize));
            checkButton.draw(g2);
        }
        menuButton.draw(g2);

    }





    private void makeButtons(BaseFrame frame){
        checkButton = new UI.Button( frame,"grass.jpg", game.getBoard().getBoardSize()/2*tileSize, game.getBoard().getBoardSize()*tileSize+ 2*tileSize, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                game.checkTheBoard();
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