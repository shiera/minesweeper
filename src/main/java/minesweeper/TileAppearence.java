package minesweeper;

import UI.Picture;

import java.awt.*;

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


    private boolean isANumber;
    private Picture picture;
    private Picture dirt1;
    private Picture grass1;


    private TileAppearence(String imageName, boolean aNumberOrBomb) {
        isANumber = aNumberOrBomb;
        // loading images
        picture = new Picture(imageName);
        dirt1 = new Picture("dirt.jpg");
        grass1 = new Picture("grass.jpg");


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
            dirt1.draw(g2, x * tileSize + boardOrigoX, y * tileSize + boardOrigoY);
        }

        if (this ==FLAG){
            grass1.draw(g2, x * tileSize + boardOrigoX, y * tileSize + boardOrigoY);
        }
        picture.draw(g2, x * tileSize + boardOrigoX, y * tileSize + boardOrigoY);
    }




}
