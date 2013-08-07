package minesweeper;

import static minesweeper.BoardStatus.COVERED;


/*
*  sisältää testien apumetodeja
 */

public final class TestUtils {

    private TestUtils(){}

    public static int testBoardSize = 3;
    public static int bombsInTest = 3;

    public static int amountOfbombsFound(Board board){
        int bombsFound = 0;
        int[][] boardData = board.getBoardData();
        for (int y = 0; y <boardData.length ; y++) {
            for (int x = 0; x <boardData[y].length ; x++) {
                if (boardData[y][x] == board.BOMB) bombsFound ++;
            }
        }
        return bombsFound;
    }

    public static boolean atLeastOneBomb(Board board){
        int[][] boardData = board.getBoardData();
        for (int y = 0; y <boardData.length ; y++) {
            for (int x = 0; x <boardData[y].length ; x++) {
                if (boardData[y][x] == board.BOMB) return true;
            }
        }
        return false;
    }

    public static boolean isBoardCovered(Board board){
        BoardStatus[][] boardStatus = board.getBoardStatus() ;
        for (int y = 0; y <boardStatus.length ; y++) {
            for (int x = 0; x <boardStatus[y].length ; x++) {
                if (boardStatus[y][x] != COVERED) return false;
            }
        }
        return true;
    }

    /**
     *
     * @param board testgameboard
     * @return  true if board is clean
     */
    public static boolean isBoardClean(Board board){
        int[][] boardData = board.getBoardData();
        for (int y = 0; y <boardData.length ; y++) {
            for (int x = 0; x <boardData[y].length ; x++) {
                if (boardData[y][x] != 0) return false;
            }
        }
        return true;
    }

    public static int[][] makeTestBoard(){
        int[][] testBoard = new int[testBoardSize][bombsInTest];
        testBoard[0][0] = -1;
        testBoard[0][2] = -1;
        testBoard[1][0] = -1;
        return testBoard;
    }

    public  static int[][] makeTestBoardWhitNumbers(){
        int[][] testBoard = makeTestBoard();
        testBoard[0][1] = 3;
        testBoard[1][1] = 3;
        testBoard[1][2] = 1;
        testBoard[2][0] = 1;
        testBoard[2][1] = 1;
        return testBoard;
    }
     /*   testiboardi
    -1 3 -1
    -1 3  1
     1 1  0
      */

}
