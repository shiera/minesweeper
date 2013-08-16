package UI;

import java.awt.*;

/**
 * Author: Shiera
 */
public class Button {
    private Picture picture;
    private int width;
    private int height;
    private int posX;
    private int posY;
    private ButtonHandler handler;
    private BaseFrame frame;



    public Button(BaseFrame frame, String imageName, int posX, int posY, ButtonHandler handler){
        this.handler = handler;
        picture = new Picture(imageName);
        width = picture.getWidth();
        height = picture.getHeight();
        this.posX = posX;
        this.posY = posY;
        this.frame = frame;

    }



    public void draw(Graphics2D g2){
        picture.draw(g2, posX, posY);
    }



    public void ifClicked(int clickPosX, int clickPosY){
         if (clickPosX >= posX && clickPosX < (posX + width) && clickPosY >= posY && clickPosY < (posY +height)){
             handler.onButtonClick(frame);
         }

    }
}
