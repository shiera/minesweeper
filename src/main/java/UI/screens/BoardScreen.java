package UI.screens;

/**
 * Author: Shiera
 */
import java.awt.*;
import java.awt.event.MouseEvent;

import UI.*;
import UI.Button;
import minesweeper.GameLogic;

import static UI.TileAppearence.*;
import static minesweeper.BoardStatus.*;


public class BoardScreen extends Screen{







    private int xBoardOrigoCord = 64;
    private int yBoardOrigoCord = 32;

    private int choosedTileX = -1;
    private int choosedTileY = -1;
    private Picture choosedTile = new Picture("choosedTile.png");



    private UI.Button checkButton;
    private UI.Button menuButton;
    private UI.Button newGameButton;

    public BoardScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
    }

    public boolean isGameRrunning() {
        return gameLogic.isPlaying();
    }

    public boolean isGameWon(){
        return gameLogic.isHasWon();
    }



    @Override
    public int getScreenWidth() {
        return (gameLogic.getBoard().getBoardWidth()*tileSize) + 5*tileSize;
    }

    @Override
    public int getScreenHeight() {
        return (gameLogic.getBoard().getBoardHeight()*tileSize) + 6*tileSize;
    }

    /**
     * do when mouse was clicked
     * @param e

     */
    @Override
    protected void whenClicked(MouseEvent e) {
        int posX = e.getX();
        int posY = e.getY();
        int tileX = (posX - xBoardOrigoCord) / tileSize;
        int tileY = (posY - yBoardOrigoCord) / tileSize;
        if (posX < xBoardOrigoCord || posY < yBoardOrigoCord) {
            tileX = -1;
            tileY = -1;

        }
        if  (   tileX >= 0 && tileX < gameLogic.getBoard().getBoardWidth()  &&
                tileY >= 0 && tileY < gameLogic.getBoard().getBoardHeight())   {

            int mouseButton = e.getButton();
            // frame päättää paneelien välillä vaihtamisen
            if (mouseButton == LEFTBUTTON) {
                gameLogic.doMove(tileX, tileY, UNCOVERED);
            }
            if (mouseButton == RIGHTBUTTON) {
                if (gameLogic.getBoard().getStatusXY(tileX, tileY) == MARKED) {
                    gameLogic.doMove(tileX, tileY, COVERED);
                } else gameLogic.doMove(tileX, tileY, MARKED);
            }
        }
        // jos painettiin nappeja
        if ( gameLogic.flagsLeft() == 0 && isGameRrunning()) {
            checkButton.ifClicked(posX, posY);
        }
        else if ( !isGameRrunning()){
            newGameButton.ifClicked(posX, posY);
        }

        menuButton.ifClicked(posX, posY);



    }

    /**
     * do wtileSizehen mouse was mooved
     * @param e

     */
    @Override
    protected boolean mouseMove(MouseEvent e) {
        choosedTileX = (e.getX() - xBoardOrigoCord) / tileSize;
        choosedTileY = (e.getY() - yBoardOrigoCord) / tileSize;
        if (    e.getX() < xBoardOrigoCord ||
                e.getY() < yBoardOrigoCord ||
                choosedTileX > gameLogic.getBoard().getBoardWidth()-1 ||
                choosedTileY> gameLogic.getBoard().getBoardHeight()-1) {

            choosedTileX = -1;
            choosedTileY = -1;


        }
        return true;

    }

    @Override
    public void open() {
        newGameButton.changePos(getScreenWidth()/2-48, getScreenHeight()-64);
        checkButton.changePos(getScreenWidth()/2-48, getScreenHeight()-64);
    }

    /**
     * draws grapics
     * @param g2
     */
    @Override
    protected void paintScreen(Graphics2D g2) {
        int lastCoordinateOfBoardX = (gameLogic.getBoard().getBoardWidth()*tileSize)+ xBoardOrigoCord;
        int lastCoordinateOfBoardY = (gameLogic.getBoard().getBoardHeight()*tileSize)+ yBoardOrigoCord;
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

        // ----------  draw text and buttons
        g2.setColor(Color.WHITE);
        g2.drawString("Flags left: " + gameLogic.flagsLeft(), lastCoordinateOfBoardX / 2, lastCoordinateOfBoardY + tileSize);
        // draw if flags used
        if (  gameLogic.flagsLeft() == 0 && isGameRrunning()){
            g2.drawString("No flags left to put, press button to check" , tileSize , lastCoordinateOfBoardY + (2*tileSize));
            checkButton.draw(g2);
        }
        if (!isGameRrunning()){
            newGameButton.draw(g2);
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
        checkButton = new UI.Button( frame,"checkBoard.png", getScreenWidth()/2-48, getScreenHeight()-64, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.checkTheBoard();
            }
        });
        menuButton = new UI.Button( frame, "menu.png", 0, 0, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                 frame.changeToMenu();
            }
        });
        newGameButton = new UI.Button( frame,"newGame.png",getScreenWidth()/2-48, getScreenHeight()-64, new ButtonHandler() {
            @Override
            public void onButtonClick(BaseFrame frame) {
                gameLogic.newGame();
            }
        });
    }





}