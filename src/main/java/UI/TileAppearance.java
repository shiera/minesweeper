package UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * enum whit all the possible tiles used in minesweeper
 * can take 0 .. * picture names, and draws a random picture of right enum in expected tile
 * can draw multiple tile (images) on top on each other (for example FLAG enum draws a GRASS tile under the flag)
 */
public enum TileAppearance {

    GRASS(false,false, "grass1.png", "grass2.png", "grass3.png", "grass4.png", "grass5.png"),
    FLAG(false,false, "flag1.png", "flag2.png"),
    FOUNDBOMB(false,false, "foundBomb.png"),
    BURNEDFLAG(false,false, "burningFlag.png"),
    WRONGMARKEDFLAG(false, false),
    FIRE(true,false, "fire.png"),
    //todo is number to false when new picture
    EXPLODEDBOMB(true,false, "explodedBomb.png"),
    EXPLODEDMARKEDFLAG(false, false),
    OUTSIDE(false,false, "outside.png"),
    DIRT(false,false, "dirt1.png", "dirt2.png"),
    NUMBER1(true,true, "number1.png"),
    NUMBER2(true,true, "number2.png"),
    NUMBER3(true,true, "number3.png"),
    NUMBER4(true,true, "number4.png"),
    NUMBER5(true,true, "number5.png"),
    NUMBER6(true,true, "number6.png"),
    NUMBER7(true,true, "number7.png"),
    NUMBER8(true,true, "number8.png"),
    NUMBER9(true,true, "number9.png");


    private boolean dirtUnder;
    private boolean isNumber;
    private ArrayList<Picture> pictures = new ArrayList<Picture>();
    private static Random random = new Random();
    private static int randomGameSeed = 0;




    /**
     * @param  dirtUnder true if the Enum wants a dirt tile drawn under it
     * @param isNumber  true if the Enum is a number
     * @param imageNames 0 .. * images for the enum
     */
    private TileAppearance(boolean dirtUnder, boolean isNumber, String... imageNames) {
        this.dirtUnder = dirtUnder;
        this.isNumber = isNumber;
        // loading images
        for (String imageName : imageNames) {
            pictures.add(new Picture("tilePictures//" + imageName));
        }



    }

    /**
     * makes an new component to the random seed used to draw random image
     */
    public static void setRandomGameSeed() {
        random.setSeed(System.currentTimeMillis());
        randomGameSeed = random.nextInt();
    }


    /**
     * Draws the EnumGraphics to the given tile
     * @param tileX  x-coordinate of the tile
     * @param tileY  y-coordinate of the tile
     * @param g2 graphics for painting
     */
    public void drawImage(int tileX, int tileY, Graphics2D g2, int tileSize, int boardOrigoX, int boardOrigoY){
        random.setSeed(tileX*2870103+tileY*197137+ randomGameSeed+ this.hashCode());
        Picture picture = null;
        if (!pictures.isEmpty()){
            picture = pictures.get(random.nextInt(pictures.size()));
        }
        // Flag want garss under it
        if (this ==FLAG || this == FOUNDBOMB || this == WRONGMARKEDFLAG){
            GRASS.drawImage(tileX, tileY,  g2, tileSize,  boardOrigoX,  boardOrigoY);
        }
        if (dirtUnder){
            DIRT.drawImage(tileX, tileY,  g2, tileSize,  boardOrigoX,  boardOrigoY);
        }
        // numbers have different picture size
        if (isNumber){
            picture.draw(g2,tileX * tileSize + boardOrigoX, tileY * tileSize + boardOrigoY);
        }
        // exceptions
        else if (this == EXPLODEDMARKEDFLAG){
            EXPLODEDBOMB.drawImage(tileX, tileY,  g2, tileSize,  boardOrigoX,  boardOrigoY);
            BURNEDFLAG.drawImage(tileX, tileY,  g2, tileSize,  boardOrigoX,  boardOrigoY);
        }
        else if(this == WRONGMARKEDFLAG){
             BURNEDFLAG.drawImage(tileX, tileY,  g2, tileSize,  boardOrigoX,  boardOrigoY);
        }
        //normal
        else{
            picture.draw(g2, tileX * tileSize + boardOrigoX - (tileSize/2), tileY * tileSize + boardOrigoY -(tileSize/2));
        }


    }




}
