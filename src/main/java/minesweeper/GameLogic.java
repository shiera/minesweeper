package minesweeper;

import UI.TileAppearence;

import static minesweeper.BoardStatus.*;

/**
* @author shiera
*/


public class GameLogic {

    private final int BOMB = Board.BOMB;
    private Board board;
    private double bombAmountPercent;
    private boolean playing;
    private boolean hasWon;
    private int boardWidth;
    private int boardHeight;




    public GameLogic(int size, double bombAmountPercent) {
        this(size, size,bombAmountPercent );
    }

    public GameLogic( int boardWidth, int boardHeight,double bombAmountPercent) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.bombAmountPercent = bombAmountPercent;
        board = new Board(boardWidth,boardHeight, (int)(bombAmountPercent*boardHeight*boardWidth/100));
        newGame();

    }


    /**
     * used in testing
     * @param size  boardsize
     * @param bombAmount  amounts if bombs
     * @param board    board used in test
     */
    protected GameLogic(int size, int bombAmount, Board board ){
        this.boardHeight = size;
        this.boardWidth = size;
        this.bombAmountPercent = (double)100*bombAmount/(boardHeight*boardWidth);
        this.board = board;
        playing = true;
    }

    protected double getBombAmountPercent() {
        return bombAmountPercent;
    }

    public int getBombAmount(){
        return  (int)(bombAmountPercent*boardHeight*boardWidth/100);
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
        return getBombAmount()-board.getMarkedSpacesCount();
    }



    /**
     setup board for new gameLogic
     */
    public void newGame(){
        board.setupBoard();
        playing = true;
        hasWon = false;
        TileAppearence.setRandomGameSeed();
    }


    /**
     * change size and bombAmount OF Board
     * @param size
     */
    public void setSize(int size){
        setSize(size, size);

    }

    /**
     * change size and bombamount of board, takes different width than height
     * @param boardWidth
     * @param boardHeight
     */
    public void setSize(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        board = new Board(boardWidth,boardHeight, getBombAmount());
        newGame();
    }

    public void setBombAmountPercent(double bombAmountPercent) {
        this.bombAmountPercent = bombAmountPercent;
        board = new Board(boardWidth,boardHeight, getBombAmount());
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
            if (x < 0 || x >= boardWidth){
                System.out.println("x cordinate " + x + "is  out of board maxX = " + (boardWidth- 1));

            }
            else if (y < 0 || y >= boardHeight ){
                System.out.println("y cordinate " + y + "is  out of board maxY = " + (boardHeight - 1));
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

        /**                      changeOptions(40, 20, 80);
    * marks the given cordinate if all marcs (flags) are not used
    * @param x
    * @param y
    */
    private void mark(int x, int y){
        if (board.getMarkedSpacesCount() >= getBombAmount()){
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


