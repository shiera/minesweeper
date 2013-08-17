package UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public enum TileAppearence {

    // tällä hetkellä kaikilla sama testikuva
    GRASS(false, "grass1.png", "grass2.png", "grass3.png", "grass4.png", "grass5.png"),
    FLAG(false, "flag1.png", "flag2.png"),
    BOMBFIELD(true, "bomb.jpg"),
    OUTSIDE(false, "outside.png"),
    DIRT(false, "dirt1.png", "dirt2.png"),
    NUMBER0(true, ""),
    NUMBER1(true, "number1.png"),
    NUMBER2(true, "number2.png"),
    NUMBER3(true, "number3.png"),
    NUMBER4(true, "number4.png"),
    NUMBER5(true, "number5.png"),
    NUMBER6(true, "number6.png"),
    NUMBER7(true, "number7.png"),
    NUMBER8(true, "number8.png"),
    NUMBER9(true, "number9.png");



    private boolean isANumber;
    private ArrayList<Picture> pictures = new ArrayList<Picture>();
    private static Random random = new Random();
    private static int randomGameSeed = 0;



    private TileAppearence(boolean aNumberOrBomb, String ... imageNames) {
        isANumber = aNumberOrBomb;
        // loading images
        for (String imageName : imageNames) {
             pictures.add(new Picture("tilePictures//" + imageName));
        }



    }

    public static void setRandomGameSeed() {
        random.setSeed(System.currentTimeMillis());
        randomGameSeed = random.nextInt();
    }

    // todo random kahden kuvan välillä
    /**
     * Draws the grapics to the givan tile
     * @param tileX  tile x cordinate
     * @param tileY  tile y cordinate
     * @param g2 graphics for painting
     */
    public void drawImage(int tileX, int tileY, Graphics2D g2, int tileSize, int boardOrigoX, int boardOrigoY){
        random.setSeed(tileX*2870103+tileY*197137+ randomGameSeed+ this.hashCode());
        Picture picture = pictures.get(random.nextInt(pictures.size()));
        if (this ==FLAG){
            GRASS.drawImage(tileX, tileY,  g2, tileSize,  boardOrigoX,  boardOrigoY);
        }
        if (isANumber  ){
            DIRT.drawImage(tileX, tileY,  g2, tileSize,  boardOrigoX,  boardOrigoY);
            // yhteen kun numeroista uudet kuvat
            picture.draw(g2,tileX * tileSize + boardOrigoX, tileY * tileSize + boardOrigoY);
        }
        else{
            picture.draw(g2, tileX * tileSize + boardOrigoX - (tileSize/2), tileY * tileSize + boardOrigoY -(tileSize/2));
        }


    }




}
