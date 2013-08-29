package UI.screens;


import java.awt.*;
import java.awt.event.MouseEvent;

import UI.*;
import minesweeper.GameLogic;
import sounds.Sound;

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
    private int yBoardOrigoCord = 64;

    private int choosedTileX = -1;
    private int choosedTileY = -1;

    private boolean alreadyWon = false;
    private boolean buttonsClicked = false;

    // picture indicating which tile is choosed
    private Picture choosedTile = new Picture("choosedTile.png");
    private Picture win = new Picture("win.png");
    private Picture lost = new Picture("lost.png");

    private UI.Button checkButton;
    private UI.Button menuButton;
    private UI.Button newGameButton;



    private Sound illegal;
    private Sound explosion;
    private Sound flag;
    private Sound dig;
    private Sound winSound;





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
    private boolean isGameRunning() {
        return gameLogic.isPlaying();
    }

    /**
     * asks GameLogic if the game is won
     * @return true if round if won, false if round is lost or still running,
     *         use  isGameRunning() to see if game is still running
     */
    private boolean isGameWon(){
        return gameLogic.hasWon();
    }


    /**
     * @return returns the default width of the screen
     */
    @Override
    public int getScreenWidth() {
        return (gameLogic.getBoard().getWidth()*tileSize) + 4*tileSize;
    }

    /**
     * @return returns the default height of the screen
     */
    @Override
    public int getScreenHeight() {
        return (gameLogic.getBoard().getHeight()*tileSize) + 7*tileSize;
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
        boolean legalMove = false;
        // ensures that tileX, tileY are outside of board if clicked at the left or upper side of the board
        if (posX < xBoardOrigoCord || posY < yBoardOrigoCord) {
            tileX = -1;
            tileY = -1;
        }
        int mouseButton = -1;
        // do if coordinates are on board
        if  (   tileX >= 0 && tileX < gameLogic.getBoard().getWidth()  &&
                tileY >= 0 && tileY < gameLogic.getBoard().getHeight())   {
            mouseButton = e.getButton();
            if (mouseButton == LEFTBUTTON) {
                legalMove = gameLogic.doMove(tileX, tileY, UNCOVERED);
            }
            if (mouseButton == RIGHTBUTTON) {
                if (gameLogic.getBoard().getStatusXY(tileX, tileY) == MARKED) {
                    legalMove = gameLogic.doMove(tileX, tileY, COVERED);


                }
                else legalMove = gameLogic.doMove(tileX, tileY, MARKED);
            }
            buttonsClicked = true;

        }
        // if buttons was clicked
        boolean buttonClicked = false;
        if ( gameLogic.flagsLeft() == 0 && isGameRunning()) {
            checkButton.ifClicked(posX, posY);
        }
        else if ( !isGameRunning()){
            if (newGameButton.ifClicked(posX, posY)) buttonClicked = true;
        }
        if (menuButton.ifClicked(posX, posY)) buttonClicked = true;
        if (soundButton.ifClicked(posX, posY)) buttonClicked = true;
        playSound(mouseButton, legalMove,buttonClicked);

    }


    @Override
    protected void makeSounds() {
        super.makeSounds();
        illegal = new Sound("unleagalMove");
        explosion = new Sound("Explosion2");
        flag = new Sound("flag");
        dig = new Sound("dig");
        winSound = new Sound("win");
    }

    /**
     * plays sound for game
     * @param mouseButton
     * @param legalMove
     * @param buttonClicked
     */

    private void playSound(int mouseButton, boolean legalMove, boolean buttonClicked){
        if (frame.isSoundON()) {
            // if game is won or lost
            if (!alreadyWon && !isGameRunning()){
                alreadyWon = true;
                if (isGameWon()){
                    winSound.play();
                }
                else{
                    explosion.play();
                }
            }
            // buttons
            else if (buttonClicked){
                select.play();
            }
            // if making move
            else if (legalMove){
                if (mouseButton == LEFTBUTTON){
                    dig.play();
                }
                else{
                    flag.play();
                }
            }
            else{
                illegal.play();
            }
        }
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
                choosedTileX > gameLogic.getBoard().getWidth()-1 ||
                choosedTileY> gameLogic.getBoard().getHeight()-1) {
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
        super.open();
        newGameButton.changePos(getScreenWidth()/2-48, getScreenHeight()-64);
        checkButton.changePos(getScreenWidth()/2-48, getScreenHeight()-64);
        buttonsClicked = false;

    }

    /**
     * draws all graphics
     * @param g2
     */
    @Override
    protected void paintScreen(Graphics2D g2) {

        int lastCoordinateOfBoardX = (gameLogic.getBoard().getWidth()*tileSize)+ xBoardOrigoCord;
        int lastCoordinateOfBoardY = (gameLogic.getBoard().getHeight()*tileSize)+ yBoardOrigoCord;
        drawBoardAndBackground(g2);
        // ----------  draw text and buttons
        g2.setColor(Color.WHITE);
        // how many flags left
        g2.setFont(FONT);
        g2.drawString("Flags left: " + gameLogic.flagsLeft(), lastCoordinateOfBoardX / 2, lastCoordinateOfBoardY + tileSize);
        // draw if flags used
        if (  gameLogic.flagsLeft() == 0 && isGameRunning()){
            g2.setFont(SMALLFONT);
            g2.drawString("No flags left to put, press button to check" , tileSize/2 , lastCoordinateOfBoardY + (2*tileSize));
            checkButton.draw(g2);
        }
        if (!buttonsClicked){
            g2.setFont(SMALLFONT);
            g2.drawString("Dig with left mouse, flag with right" , tileSize , lastCoordinateOfBoardY + (2*tileSize));
        }
        if (!isGameRunning()){
            newGameButton.draw(g2);
        }
        menuButton.draw(g2);
        soundButton.draw(g2);
        if (choosedTileY != -1){
            choosedTile.draw(g2, (choosedTileX*tileSize)+xBoardOrigoCord, (choosedTileY*tileSize)+yBoardOrigoCord);
        }
        drawWinOrLost(g2);

    }


    private void drawWinOrLost(Graphics2D g2){
        if (isGameWon() && !isGameRunning()) {
            win.draw(g2, (getScreenWidth()/2)-(win.getWidth()/2), (getScreenHeight()/3)-(win.getHeight()/2));
        }
        if (!isGameWon() && !isGameRunning()){
            lost.draw(g2, (getScreenWidth()/2)-(lost.getWidth()/2), (getScreenHeight()/3)-(lost.getHeight()/2));
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
                    gameLogic.getTileAppearance(tileX, tileY).drawImage(tileX,tileY,g2, tileSize, xBoardOrigoCord, yBoardOrigoCord);
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
        super.makeButtons(frame);
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
                alreadyWon = false;
            }
        });

    }





}