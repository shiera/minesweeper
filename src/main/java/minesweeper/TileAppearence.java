package minesweeper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum TileAppearence {

    // tällä hetkellä kaikilla sama testikuva
    GRASS("grass.jpg", false),
    FLAG("flag.png", false),
    BOMBFIELD("bomb.jpg", true),
    NUMBER0("dirt.jpg", true),
    NUMBER1("number1.png", true),
    NUMBER2("number2.png", true),
    NUMBER3("number3.png", true),
    NUMBER4("number4.png", true),
    NUMBER5("number5.png", true),
    NUMBER6("number6.png", true),
    NUMBER7("number7.png", true),
    NUMBER8("number8.png", true),
    NUMBER9("number9.png", true);

    private String imageName;
    private boolean isANumber;
    private BufferedImage image;
    private BufferedImage dirt1;
    private BufferedImage grass1;


    private TileAppearence(String imageName, boolean aNumberOrBomb) {
        this.imageName = imageName;
        isANumber = aNumberOrBomb;
        // loading images
        image = loadImage(imageName);
        dirt1 = loadImage("dirt.jpg");
        grass1 = loadImage("grass.jpg");


    }

    private BufferedImage loadImage(String imageName){
        BufferedImage loadedImage = null;
        try {
            loadedImage = ImageIO.read(new File("src//pictures//" + imageName ));

        } catch (IOException ex) {
            System.out.println(imageName + " not found");

        }

        return loadedImage;
    }

    // todo random kahden kuvan välillä
    /**
     * Draws the grapics to the givan tile
     * @param x  tile x cordinate
     * @param y  tile y cordinate
     * @param g2 graphics for painting
     */
    public void drawImage(int x, int y, Graphics2D g2, int tileSize, int boardOrigoX, int boardOrigoY){
        if (isANumber  ){
            g2.drawImage(dirt1, x*tileSize + boardOrigoX, y*tileSize + boardOrigoY, null);
        }

        if (this ==FLAG){
            g2.drawImage(grass1,x*tileSize + boardOrigoX, y*tileSize + boardOrigoY, null);
        }
        g2.drawImage(image,x*tileSize + boardOrigoX, y*tileSize + boardOrigoY, null);
    }




}
