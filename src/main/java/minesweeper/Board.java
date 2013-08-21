package minesweeper;

import UI.TileAppearance;

import java.util.Random;

import static minesweeper.BoardStatus.*;
import static UI.TileAppearance.*;

/**
 * Keeps track of the status of the gameBoard
 * has 2 int[][] arrays, 1 for tile status and 1 for boardData
 * keeps count of bombAmount, Size and used flags
 * @author shiera
 */
public class Board {

    public static final int BOMB = -1;

    private int boardWidth;
    private int boardHeight;
    private int bombAmount;
    private int markedSpacesCount = 0;
    // the board whit -1 for bomb and numbers 0-9 for bombs close
    private int[][] boardData;
    // the statusBoard whit 0 for uncovered, -1 for marked bomb and 1 for uncovered
    private BoardStatus[][] boardStatus;

    // testatessa true
    private boolean testingWhitGivenBoard;
    private int[][] testingBombs = null;

    /**
     * normal constructor
     * @param boardWidth  width (tile amount vertically) of board
     * @param boardHeight  height (tile amount horizontally) of board
     * @param bombAmount   amount of bombs in the board max 1/3 of the tiles at the board
     */
    Board(int boardWidth, int boardHeight, int bombAmount) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.bombAmount = bombAmount;
        if (bombAmount > (boardHeight*boardWidth)/3){
            this.bombAmount = (boardHeight*boardWidth)/3;
        }
        boardData = new int[boardHeight][boardWidth];
        boardStatus = new BoardStatus[boardHeight][boardWidth];
        setupBoard();
        testingWhitGivenBoard = false;

    }

    /**
     * constructor for test
     * @param boardSize  width and height of the board
     * @param bombAmount  amount of bombs on the board
     */
    protected Board(int boardSize, int bombAmount){
         this(boardSize, boardSize, bombAmount);
    }

    /**
     * constructor for test whit given board
     * @param boardSize  width and height of the board
     * @param bombAmount amount of bombs on the board
     * @param bombs  board whit bombs for the testBoard
     */
    protected Board(int boardSize, int bombAmount, int[][] bombs){
        this(boardSize, boardSize, bombAmount);
        testingWhitGivenBoard = true;
        testingBombs = bombs;
        setupBoard();
    }

    /**
     * @return height (tile amount vertically) of the board
     */
    public int getBoardHeight() {
        return boardHeight;
    }

    /**
     * @return width (tile amount horizontally) of the board
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * @return the boardStatus in form of int[][] whit 0 for uncovered,
     *          -1 for marked bomb and 1 for uncovered
     */
    protected BoardStatus[][] getBoardStatus() {
        return boardStatus;
    }

    /**
     * @return the amount of bombs, that will be/are at the board
     */
    public int getBombAmount() {
        return bombAmount;
    }

    /**
     * @return  the board in form of int[][] whit -1 for bomb and numbers 0-9 for bombs close
     */
    public int[][] getBoardData() {
        return boardData;
    }

    /**
     * @return  count of marked tiles on the board
     */
    public int getMarkedSpacesCount() {
        return markedSpacesCount;
    }


    /**
     * sets a new status to given coordinate of the statusBoard
     * @param x coordinate for the new status
     * @param y coordinate for the new status
     * @param status the new status (BoardStatus)
     * @return true if legal move
     */
    public boolean setStatusXY(int x, int y, BoardStatus status){
        if (cordOnBoard(x, y)){
            if (boardStatus[y][x] == COVERED && status == MARKED){
                markedSpacesCount++;
                boardStatus[y][x] = status;
                return true;
            }
            else if (boardStatus[y][x] == MARKED && status == COVERED){
                markedSpacesCount--;
                boardStatus[y][x] = status;
                return true;
            }
            else if (status == UNCOVERED && boardStatus[y][x] == COVERED){
                expandIf0(x, y);
                return true;
            }
        }
        return false;


    }

    /**
     * @param x x-coordinate of the wanted tile
     * @param y y-coordinate of the wanted tile
     * @return  status of tile at the given given x, y coordinates
     */
    public BoardStatus getStatusXY(int x, int y){
        return boardStatus[y][x];
    }

    /**
     * @param x x-coordinate of the wanted tile
     * @param y y-coordinate of the wanted tile
     * @return  data (number) of the wanted tile  (-1 = bomb)
     */
    public int getDataXY(int x, int y){
        return boardData[y][x];
    }

    /**
     * uncovers tile(x, y), and all around it, if data in the coordinate was 0
     * @param x  x-coordinate
     * @param y  y-coordinate
     */
    private void expandIf0(int x, int y){
         // dont do if already uncovered
         if  (boardStatus[y][x] == UNCOVERED) return;
         if  (boardStatus[y][x] == MARKED) markedSpacesCount--;
         boardStatus[y][x] = UNCOVERED;
         if (boardData[y][x] == 0 ){
             if (cordOnBoard(x-1, y-1)) expandIf0(x - 1, y - 1);
             if (cordOnBoard(x  , y-1)) expandIf0(x, y - 1);
             if (cordOnBoard(x+1, y-1)) expandIf0(x + 1, y - 1);
             if (cordOnBoard(x-1, y  )) expandIf0(x - 1, y);
             if (cordOnBoard(x+1, y  )) expandIf0(x + 1, y);
             if (cordOnBoard(x-1, y+1)) expandIf0(x - 1, y + 1);
             if (cordOnBoard(x  , y+1)) expandIf0(x, y + 1);
             if (cordOnBoard(x+1, y+1)) expandIf0(x + 1, y + 1);

         }
    }

    /**
     * setups board (new game) == Places random bombs and numbers around the bombs
     */
    public void setupBoard(){
        clearBoard();
        placeBombs();
        placeNumbers();
        coverBoard();
        // testin takia
        if (testingWhitGivenBoard){
            setupBoard(testingBombs,bombAmount);
        }
        markedSpacesCount = 0;
    }


    /**
     * setupBoard for tests whit given bombs
     * will crash if bombs[][] are wrong size or bombAmount wrong
     * @param bombs array bombs[][] whit given bombs for testing
     * @param bombAmount  amount of bombs in bombs[][]
     */
    protected void setupBoard(int[][] bombs, int bombAmount){
        clearBoard();
        this.bombAmount = bombAmount;
        boardData = bombs;
        placeNumbers();
        coverBoard();
    }


    /**
     * covers the whole board (all tiles get BoardStatus.COVERED)
     */
    private void coverBoard(){
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                boardStatus[y][x] = COVERED;
            }
        }
    }

    /**
     * uncovers whole board
     */
    void uncoverBoard(){
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                boardStatus[y][x] = UNCOVERED;
            }
        }
    }

    /**
     * clears board (sets all coordinates in boardData[][] to 0
     */
    protected void clearBoard(){
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                boardData[y][x] = 0;
            }
        }
    }

     /**
     * places the bombs at the board.
     * if the first place it tries to place the bomb at are occupied
     * it places the bomb at next unoccupied place (to the right)
     */
     private void placeBombs(){
        Random randomPlace = new Random();
        for (int i = 0; i <bombAmount ; i++) {
             int x = randomPlace.nextInt(boardWidth);
             int y = randomPlace.nextInt(boardHeight);
             while (boardData[y][x] == BOMB){
                 x ++;
                 if (x >= boardWidth){
                     x = 0;
                     y ++;
                 }
                 if (y >= boardHeight){
                     y = 0;
                 }
             }
            boardData[y][x] = BOMB;

        }
    }



    /**
     *places the numbers on the board
     */
    private void placeNumbers(){
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if (boardData[y][x] != BOMB){
                    boardData[y][x] =  bombsClose(x,y);
                }
            }
        }
    }

    /**
     * Checks how many bombs there are close to the given tile
     * @param x  x-coordinate of tile
     * @param y  y- coordinate of tile
     * @return  amount of bombs beside the given tile
     */
    private int bombsClose(int x, int y){
        int bombsClose = 0;
        if (isBombAt(x-1, y-1)) bombsClose ++;
        if (isBombAt(x, y-1))   bombsClose ++;
        if (isBombAt(x+1, y-1)) bombsClose ++;
        if (isBombAt(x-1, y))   bombsClose ++;
        if (isBombAt(x+1, y))   bombsClose ++;
        if (isBombAt(x-1, y+1)) bombsClose ++;
        if (isBombAt(x, y+1))   bombsClose ++;
        if (isBombAt(x+1, y+1)) bombsClose ++;
        return bombsClose;
    }

    /**
     * @param x x-coordinate
     * @param y y-coordinate
     * @return   true if given coordinates are part of the board
     */
    public boolean cordOnBoard(int x, int y){
        return y >= 0 && x >= 0 && x < boardWidth && y < boardHeight;
    }


    /**
     * @param x  x-cordinate
     * @param y  y-cordinate
     * @return  true if there's a bomb in the given cordinates
     */
    private boolean isBombAt(int x, int y){
        return cordOnBoard(x, y) && boardData[y][x] == BOMB;
    }


    /**
     * checks if board are marked correctly
     * @return  true if all bombs are marked
     */
    public boolean checkBoard(){
        if (markedSpacesCount < bombAmount){
            return false;
        }
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if (boardData[y][x] == BOMB && (boardStatus[y][x] != MARKED )){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * tileAppearance of board while playing round
     * tels what tile should be drawn at a specific coordinate
     * @param x x-coordinate of wanted tile
     * @param y y-coordinate of wanted tile
     * @return  enum painting tile in UI
     */
    protected TileAppearance getPlayingTileAppearance(int x, int y){
        BoardStatus status = getStatusXY(x,y);
        int data = getDataXY(x, y);
        TileAppearance tileStatus = GRASS;
        if (status == COVERED) tileStatus = GRASS;
        else if (status == MARKED)  tileStatus = FLAG;
        else if (data == BOMB) tileStatus = EXPLODEDBOMB;
        else if (data == 1)  tileStatus = NUMBER1;
        else if (data == 2)  tileStatus = NUMBER2;
        else if (data == 3)  tileStatus = NUMBER3;
        else if (data == 4)  tileStatus = NUMBER4;
        else if (data == 5)  tileStatus = NUMBER5;
        else if (data == 6)  tileStatus = NUMBER6;
        else if (data == 7)  tileStatus = NUMBER7;
        else if (data == 8)  tileStatus = NUMBER8;
        else if (data == 9)  tileStatus = NUMBER9;
        else tileStatus = DIRT;
        return tileStatus;
    }

    /**
     * tileAppearance of board when lost
     * tels what tile should be drawn at a specific coordinate
     * @param x x-coordinate of wanted tile
     * @param y y-coordinate of wanted tile
     * @return  enum painting tile in UI
     */
    protected TileAppearance getLostTileAppearance(int x, int y){
        BoardStatus status = getStatusXY(x,y);
        int data = getDataXY(x, y);
        TileAppearance tileStatus = GRASS;
        if (status == MARKED && data == BOMB){
            tileStatus = EXPLODEDMARKEDFLAG;
        }
        else if (data == BOMB){
            tileStatus = EXPLODEDBOMB;
        }
        else if (status == MARKED){
            tileStatus = WRONGMARKEDFLAG;
        }
        else if (data != 0){
            tileStatus = FIRE;
        }
        else if (status == UNCOVERED){
            tileStatus = DIRT;
        }
        return tileStatus;
    }

    /**
     * tileAppearance of board when won
     * tels what tile should be drawn at a specific coordinate
     * @param x x-coordinate of wanted tile
     * @param y y-coordinate of wanted tile
     * @return  enum painting tile in UI
     */
    protected TileAppearance getWonTileAppearance(int x, int y){
        BoardStatus status = getStatusXY(x,y);
        int data = getDataXY(x, y);
        TileAppearance tileStatus = GRASS;
        if (status == COVERED) tileStatus = GRASS;
        else if (status == MARKED)  tileStatus = FOUNDBOMB;
        else if (data == 1)  tileStatus = NUMBER1;
        else if (data == 2)  tileStatus = NUMBER2;
        else if (data == 3)  tileStatus = NUMBER3;
        else if (data == 4)  tileStatus = NUMBER4;
        else if (data == 5)  tileStatus = NUMBER5;
        else if (data == 6)  tileStatus = NUMBER6;
        else if (data == 7)  tileStatus = NUMBER7;
        else if (data == 8)  tileStatus = NUMBER8;
        else if (data == 9)  tileStatus = NUMBER9;
        else tileStatus = DIRT;
        return tileStatus;
    }


}
