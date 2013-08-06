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

    private boolean running;
    private boolean playingRound;
    private Scanner reader = new Scanner(System.in);

    public GameLogic(int size, int boardAmount) {
        this.size = size;
        this.bombAmount = boardAmount;
        board = new Board(size, bombAmount);
        running = true;
        playingRound = false;
    }


    /**
* starts th game
*/
    public void play(){
        System.out.println("\n\nwelcome to minesweeper\n\n");
        boolean firstRound = true;
        while (running){
            playRound(firstRound);
            firstRound = false;
            int answ = -1;
            while (answ != 0 && answ != 1){
                System.out.println("\n\nAgain? 0 = no, 1= yes\n\n");
                try {
                    answ = Integer.parseInt(reader.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("not an option");
                }
                if (answ != 0 && answ != 1){
                    System.out.println("not an option");
                }
            }
            if (answ == 0){
                running = false;
            }
        }
    }

    /**
* runs 1 round of the text-game
* @parama firstRound should be true if it is the first round after creating a new board
*/
    private void playRound(boolean firstRound){
       // metodi lyhenee kun graafnen käyttöliittymä valmis
        playingRound = true;
        if (!firstRound) board.setupBoard();
        while (playingRound){
            board.printBoard();
            // TODO game crashes if the input is something else than a number
            System.out.println("give x cordinate (0 - " + (size-1) + "): " );
            int x = 0;
            try {
                x = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("not a number");
                continue;
            }
            if (x < 0 || x >= size ){
                System.out.println("out of board");
                continue;
            }
            System.out.println("give y cordinate (0 - " + (size-1) + "): ");
            int y = 0;
            try {
                y = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("not a number");
                continue;
            }
            if (y < 0 || y >= size ){
                System.out.println("out of board");
                continue;
            }
            System.out.println("What do you want to do? 0 = mark, 1 = uncover, 2 = unMark ");
            int option = 0;
            try {
                option = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("not a number");
                continue;
            }
            if (option == 1){
                uncover(x, y);
            }
            else if (option == 0){
                mark(x, y);
            }
            else if (option == 2){
                board.setStatusXY(x, y, COVERED);

            }
            else{
                System.out.println("not an option");
            }
        }
    }

    /**
* talks whit the board. sets status of given cordinates to uncovered and ends the game if there is a bomb
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
            checkIfReady();
        }
        else board.setStatusXY(x, y, MARKED);

    }

    /**
* checks if player is ready to check board
*/
    private void checkIfReady(){
        int ready = -1;
        while (ready != 1 && ready != 0) {
            board.printBoard();
            System.out.println("All marks used are you ready to check?\n 0 = no, 1 = yes");
            try {
                ready = Integer.parseInt(reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("not a number");
                continue;
            }
            if (ready != 1 && ready != 0){
                System.out.println("thats not an option");
            }
        }
        if (ready == 1){
            if (board.checkBoard()) won();
            else lost();


        }
    }



    private void lost(){
        System.out.println("\n\nARRGGH a BOMB\n\n");
        playingRound = false;
        board.printShownBoard();
    }


    private void won(){
        System.out.println("\n\nYay! All Bombs found");
        playingRound = false;
        board.printShownBoard();
    }


}


