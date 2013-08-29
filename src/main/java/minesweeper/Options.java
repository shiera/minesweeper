package minesweeper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static minesweeper.BoardSizeOptions.*;
import static minesweeper.DifficultyOptions.*;

/**
 * Author: Shiera
 * Saves and loads the board settings
 */
public class Options {

    private BoardSizeOptions size = SMALL;
    private DifficultyOptions difficulty = EASY;
    private boolean soundON = true;
    private File config;




    /**
     * Loads the settings if there's a config file
      */
    public Options(){
        config = new File("config.txt");
        try {
            if (config.exists()) {
                Scanner fileReader = new Scanner(config);
                size = BoardSizeOptions.valueOf(fileReader.nextLine());
                difficulty = DifficultyOptions.valueOf(fileReader.nextLine());
                fileReader.close();

            }
        } catch (Exception e){
            System.out.println("invalid config file, using default options");
            writeFile();
        }
    }

    /**
     * @return size setting enum. get width and height whit size.getWidth(), size.getHeight()
     */
    public BoardSizeOptions getSize() {
        return size;
    }

    /**
     * saves new settings
     * @param size new boardSize settings
     */
    public void setSize(BoardSizeOptions size) {
        this.size = size;
        writeFile();
    }

    /**
     * @return  returns difficulty options use difficulty.getBombAmountPercentage()
     */
    public DifficultyOptions getDifficulty() {
        return difficulty;
    }

    /**
     * saves new settings
     * @param difficulty new difficulty settings
     */
    public void setDifficulty(DifficultyOptions difficulty) {
        this.difficulty = difficulty;
        writeFile();
    }

    public boolean isSoundON() {
        return soundON;
    }

    /**
     * toggles sound from on to off
     */
    public void changeSoundOptions() {
        soundON = !soundON;
    }

    /**
     * saves new settings in config file
     */
    private void writeFile(){
        try {
            FileWriter writer = new FileWriter(config);
            writer.write(size.toString() + "\n");
            writer.write(difficulty.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("could not save settings: \n" + e.getMessage());
            e.printStackTrace();
        }

    }
}
