package UI;

import java.awt.*;

/**
 * Author: Shiera
 */
public class Button {
    private Picture unSelectedPicture;
    private Picture selectedButtonPicture;
    // the picture used right now
    private Picture picture;
    private int width;
    private int height;
    private int posX;
    private int posY;
    private ButtonHandler handler;
    private BaseFrame frame;



    public Button(BaseFrame frame, String imageName, String selectedImageName, int posX, int posY, ButtonHandler handler){
        if (selectedImageName == null){
            selectedButtonPicture = null;
        }
        else{
            selectedButtonPicture = new Picture("buttonPictures//" + selectedImageName);
        }
        this.handler = handler;
        unSelectedPicture = new Picture("buttonPictures//" + imageName);
        picture = unSelectedPicture;
        width = unSelectedPicture.getWidth();
        height = unSelectedPicture.getHeight();
        this.posX = posX;
        this.posY = posY;
        this.frame = frame;
    }

    public Button(BaseFrame frame, String imageName, int posX, int posY, ButtonHandler handler){
        this(frame, imageName, null, posX,posY, handler);
    }

    public void changePos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }




    public void draw(Graphics2D g2){
        picture.draw(g2, posX, posY);
    }


    public void toggleOn(Button ... buttonsToToggleOf){
        if (selectedButtonPicture != null){
            picture = selectedButtonPicture;
        }
        for (Button button : buttonsToToggleOf) {
            button.toggleOf();
        }
    }

    private  void toggleOf(){
        if (selectedButtonPicture != null){
            picture = unSelectedPicture;
        }
    }


    public void ifClicked(int clickPosX, int clickPosY){
         if (clickPosX >= posX && clickPosX < (posX + width) && clickPosY >= posY && clickPosY < (posY +height)){
             handler.onButtonClick(frame);
         }


    }
}
