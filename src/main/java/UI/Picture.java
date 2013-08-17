package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author: Shiera
 * loads an image file from src/pictures/ and handles its drawing
 */
public class Picture {
    private BufferedImage image;


    /**
     * @param imageName   Name of the image.
     *                    needs also rest of the path to the image if it  are somewhere else than in src/pictures/
     */
    public Picture(String imageName){
         image = load(imageName);
    }



    /**
     * @return  height of the image  (in pixels)
     */
    public int getHeight(){
        if (image != null){
            return image.getHeight();
        }
        return 0;

    }

    /**
     * @return  widt of the image (in pixels)
     */
    public int getWidth(){
        if (image != null){
            return image.getWidth();
        }
        return 0;


    }

    /**
     * draws the picture at the given coordinates
     * @param g2 needs Graphics2D
     * @param posX  x-coordinate (in pixels) for the upper left corner of the picture
     * @param posY  y-coordinate (in pixels) for the upper left corner of the picture
     */
    public void draw(Graphics2D g2, int posX, int posY){
        g2.drawImage(image, posX, posY, null);
    }


    /**
     * loads the image of the picture
     * @param imageName name of image.
     *                  image should be in package src/pictures  or have rest of the path in its name
     * @return  returns the image , return a pink 32, 32 pixel image if the right image can't be found
     */
    private BufferedImage load(String imageName){
        BufferedImage loadedImage = null;
        try {
            loadedImage = ImageIO.read(new File("src//pictures//" + imageName));

        } catch (IOException ex) {
            System.out.println(imageName + " not found");
            loadedImage = new BufferedImage(32,32,BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2 = loadedImage.createGraphics();
            g2.setColor(Color.PINK);
            g2.fillRect(0,0,32,32);
        }
        return loadedImage;
    }
}
