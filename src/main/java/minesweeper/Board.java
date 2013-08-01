package minesweeper;

import java.util.Random;

import static minesweeper.BoardStatus.*;

/**
 * @author shiera
 */

public class Board {

    public static final int BOMB = -1;
    private int size;
    private int bombAmount;
    private int markedSpacesCount = 0;
    // the board whit -1 for bomb and numbers 0-9 for bombs close
    private int[][] boardData;
    // the statusBoard whit 0 for uncovered, -1 for marked bomb and 1 for uncovered
    private BoardStatus[][] boardStatus;

    public Board(int size, int bombAmount) {
        this.size = size;
        this.bombAmount = bombAmount;
        // TODO bombamount can't be bigger than  size*size/4
        boardData = new int[size][size];
        boardStatus = new BoardStatus[size][size];
        setupBoard();

    }



    /**
     *
     * @return the boardStatus in form of int[][] whit 0 for uncovered,
     *          -1 for marked bomb and 1 for uncovered
     */
    public BoardStatus[][] getBoardStatus() {
        return boardStatus;
    }

    /**
     *
     * @return the amount of bombs, that will be at the board (afterSetup)
     */
    public int getBombAmount() {
        return bombAmount;
    }

    /**
     *
     * @return  the board in form of int[][] whit -1 for bomb and numbers 0-9 for bombs close
     */
    public int[][] getBoardData() {
        return boardData;
    }

    /**
     *
     * @return  count of bombs alredy marked
     */
    public int getMarkedSpacesCount() {
        return markedSpacesCount;
    }

    /**
     * sets a new status to 1 cordinate of the statusboard
     * @param x cordinate for the new status
     * @param y cordinate for the new status
     * @param status the new status -1 for marked bomb, 0 for covered, 1 for uncoverd
     */
    public void setStatusXY(int x, int y, BoardStatus status){
        if (cordOnBoard(x, y)){
            if (boardStatus[y][x] == COVERED && status == MARKED){
                markedSpacesCount++;
                boardStatus[y][x] = status;
            }
            else if (boardStatus[y][x] == MARKED && status == COVERED){
                markedSpacesCount--;
                boardStatus[y][x] = status;
            }
            else if (status == UNCOVERED && boardStatus[y][x] != UNCOVERED){
                expandIf0(x, y);
            }
            else if (status == COVERED){
                boardStatus[y][x] = COVERED;
            }
            else{
                System.out.println("Can't do that");
            }
        }
        else{
            System.out.println("cordinates x = " + x + ", y = " + y + " not on board");
        }





    }

    /**
     *
     * @param x  x-cordinate
     * @param y  y-cordinate
     */
    private void expandIf0(int x, int y){
         // dont do if already uncovered
         if  (boardStatus[y][x] == UNCOVERED) return;
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
     * setups a board... Places bombs and number
     */
    public void setupBoard(){
        clearBoard();
        placeBombs();
        placeNumbers();
        coverBoard();
    }


    /**
     * covers the whole board ( all cords to covered in int[][] boardStatus)
     */
    private void coverBoard(){
        for (int y = 0; y <size ; y++) {
            for (int x = 0; x <size ; x++) {
                boardStatus[y][x] = COVERED;
            }
        }
    }

    /**
     * clears board (sets all cordinates in boardData[][] to 0
     */
    protected void clearBoard(){
        for (int y = 0; y <size ; y++) {
            for (int x = 0; x <size ; x++) {
                boardData[y][x] = 0;
            }
        }
    }

     /**
     * places the bombs at the board.
     * if the first place it tries to blace the bomb at are occupied
     * it places the bomb at next unoccupied place
     */
     private void placeBombs(){
        Random randomPlace = new Random();
        for (int i = 0; i <bombAmount ; i++) {
             int x = randomPlace.nextInt(size);
             int y = randomPlace.nextInt(size);
             while (boardData[y][x] == BOMB){
                 x ++;
                 if (x >= size){
                     x = 0;
                     y ++;
                 }
                 if (y >= size){
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
        for (int y = 0; y < size ; y++) {
            for (int x = 0; x <size ; x++) {
                if (boardData[y][x] != BOMB){
                    boardData[y][x] =  bombsClose(x,y);
                }
            }
        }
    }

    /**
     * Checks how many bombs there are close to the given cordinates
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
     *
     * @param x x-cordinate
     * @param y y-cordinate
     * @return   true if cord is on board, othervise false
     */
    protected boolean cordOnBoard(int x, int y){
        if (y >= 0 && x >= 0 && x < size && y < size) return true;
        return false;
    }


    /**
     *
     * @param x  x-cordinate
     * @param y  y-cordinate
     * @return  true if there's a bomb in the given cordinates
     */
    private boolean isBombAt(int x, int y){
        if (cordOnBoard(x,y) && boardData[y][x] == BOMB){
            return true;
        }
        return false;
    }






    /**
     * Prints the board not hidden
     */
    public void printShownBoard(){
        for (int y = 0; y < size ; y++) {
            for (int x = 0; x < size ; x++) {
                if (boardData[y][x] == BOMB)System.out.print("* ");
                else System.out.print(boardData[y][x] + " ");
            }
            System.out.println();
        }
    }

    /**
     * prints the board, using statusInfoa from boardStatus[][] (* if bomb, X if marked, _ if uncovered )
     */
    public void printBoard(){
        for (int y = 0; y < size ; y++) {
            for (int x = 0; x < size ; x++) {
                switch (boardStatus[y][x]) {

                    case UNCOVERED:
                        if (boardData[y][x] == BOMB)System.out.print("* ");
                        else System.out.print(boardData[y][x] + " ");
                        break;
                    case MARKED:
                        System.out.print("x ");
                        break;
                    case COVERED:
                        System.out.print("_ ");
                        break;
                }
            }
            System.out.println();
        }
    }

    /**
     *
     * @return  true if all bombs are marked correctly
     */
    public boolean checkBoard(){
        if (markedSpacesCount < bombAmount){
            return false;
        }
        for (int y = 0; y <size ; y++) {
            for (int x = 0; x <size ; x++) {
                if (boardData[y][x] == BOMB && (boardStatus[y][x] != MARKED )){
                    return false;
                }
            }
        }
        return true;
    }

}
