package minesweeper;

import UI.TileAppearance;

import static minesweeper.BoardStatus.*;

/**
 * Handles game logic
 * all other classes uses board only through gameLogic
 * gameLogic makes a new Board when the boards size is changed
 * GameLogic is same through the whole gameSession
 * @author shiera
 */
public class GameLogic {


    private Board board;
    private boolean playing;
    private boolean hasWon;


    /**
     * Constructor used if width and height are the same
     * @param size width and height (tiles vertically and horizontally) of the board
     * @param bombAmountPercent percent of tiles with bombs at the board (board constructor maximum 33.33%)
     */
    public GameLogic(int size, double bombAmountPercent) {
        this(size, size,bombAmountPercent );
    }

    /**
     * Constructor used if width and height of the board are different
     * @param boardWidth  width (tiles vertically) of the board
     * @param boardHeight  height (tiles horizontally) of the board
     * @param bombAmountPercent percent of tiles with bombs at the board (board constructor maximum 33.33%)
     */
    public GameLogic( int boardWidth, int boardHeight,double bombAmountPercent) {
        board = new Board(boardWidth,boardHeight, bombAmountPercent);
        newGame();
    }


    /**
     * used in testing
     * @param board    board used in test
     */
    protected GameLogic( Board board ){

        this.board = board;
        playing = true;
    }

    /**
     * @return percent of tiles with bombs at the board
     */
    protected double getBombAmountPercent() {
        return board.getBombAmountPercent();
    }

    /**
     * @return amount of the bombs at the board (derived from size and bombAmountPercent)
     */
    public int getBombAmount(){
        return  board.getBombAmount();
    }

    /**
     * @return true if one round of the game are running
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * @return true if the game is won, game is lost if hasWon and isPlaying are both false
     */
    public boolean hasWon() {
        return hasWon;
    }

    /**
     * @return  current used board in game
     */
    public Board getBoard(){
        return board;
    }

    /**
     * @return flags left unused, the amount of flags in the beginning are the same as hidden bombs
     */
    public int flagsLeft(){
        return getBombAmount()-board.getMarkedSpacesCount();
    }



    /**
     * setup board for new game
     */
    public void newGame(){
        board.setupBoard();
        playing = true;
        hasWon = false;
        // so the board wouldn't look the same every game
        TileAppearance.setRandomGameSeed();
    }


    /**
     * change size of board, takes different width than height
     * @param boardWidth  new width of the board
     * @param boardHeight new height of the board
     */
    public void setSize(int boardWidth, int boardHeight){
        board = new Board(boardWidth,boardHeight, getBombAmountPercent());

    }

    /**
     * change the percentage of tiles with bombs in the board
     * @param bombAmountPercent new bombAmountPercent of the board
     */
    public void setBombAmountPercent(double bombAmountPercent) {
        board = new Board(board.getWidth(), board.getHeight(), bombAmountPercent);

    }

    /**
     * do next move on board
     * @param x x-coordinate of tile on board
     * @param y y-coordinate of tile on board
     * @param status  new status to the given tile, changes if legal move
     * @return returns false and does nothing if isPlaying are false (no game running) or if illegal move
     */
    public boolean doMove(int x, int y, BoardStatus status){
            if (!playing){
               return false;
            }
            if (x >= 0 && x < board.getWidth() &&
                y >= 0 && y < board.getHeight()){
                if (status == UNCOVERED){
                    return uncover(x, y);
                }
                else if (status == MARKED){
                    return mark(x, y);
                }
                else if (status == COVERED){
                    return board.setStatusXY(x, y, COVERED);
                }
            }
            return false;
    }

    /**
     * ask board to set status of given tile to uncovered and ends the game if there was a bomb
     * @param x  x-coordinate of the tile
     * @param y  y-coordinate of the tile
     */
    private boolean uncover(int x, int y){
        boolean legal = board.setStatusXY(x, y, UNCOVERED );
        if (legal && board.getBoardData()[y][x] == Board.BOMB){
            lost();

        }
        return legal;
    }

    /**
     * marks the given tile if there are flags left
     * @param x x-coordinate  of the tile
     * @param y y-coordinate of the tile
     */
    private boolean mark(int x, int y){
        return board.getMarkedSpacesCount() < getBombAmount() && board.setStatusXY(x, y, MARKED);
    }

    /**
     *  check if board are marked right (are game won or lost)
     */
    public void checkTheBoard(){
        if (board.checkBoard()) {
            won();
        }
        else {
            lost();
        }
    }

    /**
     * updates booleans when lost
     */
    private void lost(){
        playing = false;
        hasWon = false;
    }

    /**
     * updates booleans when won
     */
    private void won(){
        playing = false;
        hasWon = true;
    }


    /**
     * returns the tiles on the tileAppearance of the board depending on the state of the game
     * @param x x-coordinate (tileCoordinate) of the wanted tile
     * @param y y-coordinate (tileCoordinate) of the wanted tile
     * @return   enum painting tile in UI
     */
    public TileAppearance getTileAppearance(int x, int y){
        if (playing){
            return board.getPlayingTileAppearance(x, y);
        }
        else if (hasWon){
            return board.getWonTileAppearance(x, y);
        }
        return board.getLostTileAppearance(x, y);
    }



}


