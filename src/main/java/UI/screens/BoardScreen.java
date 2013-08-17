package UI.screens;


import java.awt.*;
import java.awt.event.MouseEvent;

import UI.*;
import minesweeper.GameLogic;

import static UI.TileAppearance.*;
import static minesweeper.BoardStatus.*;

/**
 * Author: Shiera
 * extends screen
 * Screen that shows the game
 * board is drawn here
 *
 */
public class BoardScreen extends Screen{


    private int xBoardOrigoCord = 64;
    private int yBoardOrigoCord = 32;

    private int choosedTileX = -1;
    private int choosedTileY = -1;

    // picture indicating wich tile is choosed
    private Picture choosedTile = new Picture("choosedTile.png");

    private UI.Button checkButton;
    private UI.Button menuButton;
    private UI.Button newGameButton;

    /**
     * Constructor that calls the default constructor of screen
     * @param game used GameLogic
     * @param frame  used frame
     */
    public BoardScreen(GameLogic game, BaseFrame frame) {
        super(game, frame);
    }

    /**
     * asks GameLogic if the game is still running
     * @return  true if a round of the game is running
     */
    public boolean isGameRrunning() {
        return gameLogic.playing();
    }

    /**
     * asks GameLogic if the game is won
     * @return true if round if won, false if round is lost or still running,
     *         use  isGameRunning() to see if game is still running
     */
    public boolean isGameWon(){
        return gameLogic.hasWon();
    }


    /**
     * @return returns the default width of the screen
     */
    @Override
    public int getScreenWidth() {
        return (gameLogic.getBoard().getBoardWidth()*tileSize) + 5*tileSize;
    }

    /**
     * @return returns the default height of the screen
     */
    @Override
    public int getScreenHeight() {
        return (gameLogic.getBoard().getBoardHeight()*tileSize) + 6*tileSize;
    }

    /**
     * do when mouse was clicked
     * updates GameLogic's board if board was clicked
     * checks if buttons was clicked (buttons run their code)
     * @param e
     */
    @Override
    protected void whenClicked(MouseEvent e) {
        int posX = e.getX();
        int posY = e.getY();
        int tileX = (posX - xBoardOrigoCord) / tileSize;
        int tileY = (posY - yBoardOrigoCord) / tileSize;
        // ensures that tileX, tileY are outside of board if clicked at the left or upper side of the board
        if (posX < xBoardOrigoCord || posY < yBoardOrigoCord) {
            tileX = -1;
            tileY = -1;
        }
        // do if coordinates are on board
        if  (   tileX >= 0 && tileX < gameLogic.getBoard().getBoardWidth()  &&
                tileY >= 0 && tileY < gameLogic.getBoard().getBoardHeight())   {
            int mouseButton = e.getButton();
            if (mouseButton == LEFTBUTTON) {
                gameLogic.doMove(tileX, tileY, UNCOVERED);
            }
            if (mouseButton == RIGHTBUTTON) {
                if (gameLogic.getBoard().getStatusXY(tileX, tileY) == MARKED) {
                    gameLogic.doMove(tileX, tileY, COVERED);
                } else gameLogic.doMove(tileX, tileY, MARKED);
            }
        }
        // if buttons was clicked
        if ( gameLogic.flagsLeft() == 0 && isGameRrunning()) {
            checkButton.ifClicked(posX, posY);
        }
        else if ( !isGameRrunning()){
            newGameButton.ifClicked(posX, posY);
        }
        menuButton.ifClicked(posX, posY);
    }

    /**
     * changes the coordinates of  the tileChooser when mouse is moved or dragged on the screen
     * sets to -1 if mouse are outside of the board
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

    /**
     * checks the buttons knows the right screenSize when opening game
     */
    @Override
    public void open() {
        newGameButton.changePos(getScreenWidth()/2-48, getScreenHeight()-64);
        checkButton.changePos(getScreenWidth()/2-48, getScreenHeight()-64);
    }

    /**
     * draws all graphics
     * @param g2
     */
    @Override
    protected void paintScreen(Graphics2D g2) {
        int lastCoordinateOfBoardX = (gameLogic.getBoard().getBoardWidth()*tileSize)+ xBoardOrigoCord;
        int lastCoordinateOfBoardY = (gameLogic.getBoard().getBoardHeight()*tileSize)+ yBoardOrigoCord;
        drawBoardAndBackground(g2);
        // ----------  draw text and buttons
        g2.setColor(Color.WHITE);
        // how many flags left
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
     * draws board and background (should be used when game is running)
     * @param g2
     */
    private void drawBoardAndBackground(Graphics2D g2){
        for (int tileY = -yBoardOrigoCord/tileSize; tileY < getHeight()/tileSize; tileY++) {
            for (int tileX = -xBoardOrigoCord/tileSize; tileX < getWidth()/tileSize ; tileX++) {
                if (gameLogic.getBoard().cordOnBoard(tileX, tileY)) {
                    gameLogic.getBoard().getTileAppearance(tileX, tileY).drawImage(tileX,tileY,g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
                } else{
                    OUTSIDE.drawImage(tileX, tileY, g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
                }

            }
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