package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author: Shiera
 */

public class Picture {
    private BufferedImage image;

    public Picture(String imageName){
         image = load(imageName);
    }



    /**
     *
     * @return  height of the image
     */
    public int getHeight(){
        if (image != null){
            return image.getHeight();
        }
        return 0;

    }

    public int getWidth(){
        if (image != null){
            return image.getWidth();
        }
        return 0;


    }

    public void draw(Graphics2D g2, int posX, int posY){
        g2.drawImage(image, posX, posY, null);
    }


    /**
     *
     * @param imageName name of image. image should be in the mao src/pictures
     * @return  returns the image
     */
    private BufferedImage load(String imageName){
        BufferedImage loadedImage = null;
        try {
            loadedImage = ImageIO.read(new File("src//pictures//" + imageName));

        } catch (IOException ex) {
            System.out.println(imageName + " not found");

        }

        return loadedImage;
    }
}
