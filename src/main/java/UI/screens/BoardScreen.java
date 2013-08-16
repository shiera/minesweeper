package UI.screens;

/**
 * Author: Shiera
 */
import java.awt.*;
import java.awt.event.MouseEvent;

import UI.*;
import minesweeper.GameLogic;

import static UI.TileAppearence.*;
import static minesweeper.BoardStatus.*;


public class BoardScreen extends Screen{




    private boolean lost;


    private int tileSize = 32;

    private int xBoardOrigoCord = 32;
    private int yBoardOrigoCord = 32;

    private int choosedTileX = -1;
    private int choosedTileY = -1;
    private Picture choosedTile = new Picture("choosedTile.png");



    private UI.Button checkButton;
    private UI.Button menuButton;

    public BoardScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
    }

    @Override
    public int getScreenWidth() {
        return (gameLogic.getBoard().getBoardSize()*tileSize) + 5*tileSize;
    }

    @Override
    public int getScreenHeight() {
        return (gameLogic.getBoard().getBoardSize()*tileSize) + 5*tileSize;
    }

    /**
     * do when mouse was clicked
     * @param e
     * @param game
     */
    @Override
    protected void whenClicked(MouseEvent e, GameLogic game) {
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
                // TODO gameLogic is lost
                lost = true;
            }
        }
    }

    /**
     * do when mouse was mooved
     * @param e
     * @param game
     */
    @Override
    protected boolean mouseMove(MouseEvent e, GameLogic game) {
        choosedTileX = (e.getX() - xBoardOrigoCord) / tileSize;
        choosedTileY = (e.getY() - yBoardOrigoCord) / tileSize;
        if (    e.getX() < xBoardOrigoCord ||
                e.getY() < yBoardOrigoCord ||
                choosedTileX > game.getBoard().getBoardSize()-1 ||
                choosedTileY> game.getBoard().getBoardSize()-1) {

            choosedTileX = -1;
            choosedTileY = -1;

        }
        return true;

    }


    /**
     * draws grapics
     * @param g2
     */
    @Override
    protected void paintScreen(Graphics2D g2) {
        int lastCoordinateOfBoardX = (gameLogic.getBoard().getBoardSize()*tileSize)+ xBoardOrigoCord;
        int lastCoordinateOfBoardY = (gameLogic.getBoard().getBoardSize()*tileSize)+ yBoardOrigoCord;
        // draw board
        for (int tileY = -yBoardOrigoCord/tileSize; tileY < getHeight()/tileSize; tileY++) {
            for (int tileX = -xBoardOrigoCord/tileSize; tileX < getWidth()/tileSize ; tileX++) {
                if (gameLogic.getBoard().cordOnBoard(tileX, tileY)) {
                    gameLogic.getBoard().getTileAppearance(tileX, tileY).drawImage(tileX,tileY,g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
                } else{
                    OUTSIDE.drawImage(tileX, tileY, g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
                }

            }
        }

        // ----------
        g2.setColor(Color.WHITE);
        g2.drawString("Flags left: " + gameLogic.flagsLeft(), lastCoordinateOfBoardX / 2, lastCoordinateOfBoardY + tileSize);
        // draw if flags used
        if (  gameLogic.flagsLeft() == 0){
            g2.drawString("No flags left to put, press button to check" , tileSize , lastCoordinateOfBoardY + (2*tileSize));
            checkButton.draw(g2);
        }
        menuButton.draw(g2);
        if (choosedTileY != -1){
            choosedTile.draw(g2, (choosedTileX*tileSize)+xBoardOrigoCord, (choosedTileY*tileSize)+yBoardOrigoCord);
        }

    }



    /**
     * make all the buttons in the gameScreen (not the tiles)
     * @param frame
     */
    @Override
    protected void makeButtons(BaseFrame frame){
        checkButton = new UI.Button( frame,"grass1.png", gameLogic.getBoard().getBoardSize()/2*tileSize, gameLogic.getBoard().getBoardSize()*tileSize+ 2*tileSize, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.checkTheBoard();
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