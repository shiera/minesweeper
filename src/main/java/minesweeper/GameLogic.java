package minesweeper;

import java.util.Scanner;

import static minesweeper.BoardStatus.*;

/**
* @author shiera
*/


public class GameLogic {

    private final int BOMB = Board.BOMB;
    private Board board;
    private int size;
    private int bombAmount;
    private boolean playing;
    private boolean hasWon;



    /**
     * @param size  boardsize
     * @param bombAmount  amounts of bombs
    */
    public GameLogic(int size, int bombAmount) {
        this.size = size;
        this.bombAmount = bombAmount;
        board = new Board(size, bombAmount);
        newGame();
    }

    /**
     * used in testing
     * @param size  boardsize
     * @param bombAmount  amounts if bombs
     * @param board    board used in test
     */
    protected GameLogic(int size, int bombAmount, Board board ){
        this.size = size;
        this.bombAmount = bombAmount;
        this.board = board;
        playing = true;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isHasWon() {
        return hasWon;
    }

    /**
     *
     * @return  board used in gameLogic
     */
    public Board getBoard(){
        return board;
    }

    /**
     *
     * @return flags left unused
     */
    public int flagsLeft(){
        return bombAmount-board.getMarkedSpacesCount();
    }



    /**
     setup board for new gameLogic
     */
    public void newGame(){
        board.setupBoard();
        playing = true;
        hasWon = false;
    }


    /**
     * change size and bombAmount OF Board
     * @param size
     * @param bombAmount
     */
    public void changeOptions(int size, int bombAmount){
        this.size = size;
        this. bombAmount = bombAmount;
        board = new Board(size, bombAmount);
        newGame();
    }





    /**
     * do next move on board
     * @param x cordinate of board
     * @param y cordinate of board
     * @param status  status wanted on board
     * @return false if no gameLogic running
     */
    public boolean doMove(int x, int y, BoardStatus status){
            if (!playing){
               System.out.println("no gameLogic running");
               return false;
            }
            // testaukseen että lauta piirtyy oikein
            board.printBoard();
            // testaukseen että x ja y toimii oikein
            if (x < 0 || x >= size ){
                System.out.println("x cordinate " + x + "is  out of board maxX = " + (size - 1));

            }
            else if (y < 0 || y >= size ){
                System.out.println("y cordinate " + y + "is  out of board maxY = " + (size - 1));
            }

            else if (status == UNCOVERED){
                uncover(x, y);
            }
            else if (status == MARKED){
                mark(x, y);
            }
            else if (status == COVERED){
                board.setStatusXY(x, y, COVERED);
            }
            return true;
    }

    /**
    * talks whit the board. sets status of given cordinates to uncovered and ends the gameLogic if there is a bomb
    * @param x
    * @param y
    */
    private void uncover(int x, int y){
        board.setStatusXY(x, y, UNCOVERED );
        if (board.getBoardData()[y][x] == BOMB){
            lost();
        }
    }

        /**
    * marks the given cordinate if all marcs (flags) are not used
    * @param x
    * @param y
    */
    private void mark(int x, int y){
        if (board.getMarkedSpacesCount() >= bombAmount){
            System.out.println("no marks left unmark something");
        }
        else board.setStatusXY(x, y, MARKED);

    }

    /**
    *  check board
    */
    public void checkTheBoard(){
        // for testing
        board.printBoard();
          //----
        if (board.checkBoard()) {
            won();
        }
        else {
            lost();
        }
    }


    private void lost(){
        System.out.println("\n\nARRGGH a BOMB\n\n");

        board.printShownBoard();
        board.uncoverBoard();
        playing = false;
    }


    private void won(){
        System.out.println("\n\nYay! All Bombs found");

        board.uncoverBoard();
        board.printShownBoard();
        playing = false;
        hasWon = true;
    }


}


