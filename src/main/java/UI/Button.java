package UI;

import java.awt.*;

/**
 * Author: Shiera
 * Buttons
 * Can have either 1 or 2 images
 * Button is a toggleButton if if has 2 images   toggleOn/toggle of does nothing if there's only 1 image
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


    /**
     * Constructor used to toggleButton
     * @param frame  the BaseFrame used in the game
     * @param imageName name of the image, image should be in the package src/pictures/buttonPictures
     * @param selectedImageName  name of image used when button is toggled, should be in same package as the other image
     * @param posX x-coordinate (pixels) of the upper left corner of the button
     * @param posY y-coordinate (pixels) of the upper left corner of the button
     * @param handler will be called when the button is clicked
     */
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

    /**
     * Constructor used to not toggleButtons
     * @param frame  the BaseFrame used in the game
     * @param imageName name of the image, image should be in the package src/pictures/buttonPictures
     * @param posX x-coordinate (pixels) of the upper left corner of the button
     * @param posY y-coordinate (pixels) of the upper left corner of the button
     * @param handler will be called when the button is clicked
     */
    public Button(BaseFrame frame, String imageName, int posX, int posY, ButtonHandler handler){
        this(frame, imageName, null, posX,posY, handler);
    }

    /**
     * change the position of the button
     * @param posX new x-coordinate (pixels) of the button
     * @param posY new y-coordinate (pixels) of the button
     */
    public void changePos(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * draws the button at it's own coordinates
     * @param g2 Graphics2D needed
     */
    public void draw(Graphics2D g2){
        picture.draw(g2, posX, posY);
    }

    /**
     * toggles on the button, toggles off the buttons given as parameters
     * changes the picture of the button
     * does nothing if button has only 1 picture
     * @param buttonsToToggleOf  0 .. * buttons to toggle of
     */
    public void toggleOn(Button ... buttonsToToggleOf){
        if (selectedButtonPicture != null){
            picture = selectedButtonPicture;
        }
        for (Button button : buttonsToToggleOf) {
            button.toggleOf();
        }
    }

    /**
     * toggles of the button
     * does nothing if button has only 1 picture
     */
    private  void toggleOf(){
        if (selectedButtonPicture != null){
            picture = unSelectedPicture;
        }
    }

    /**
     * calls handler's onButtonClick if the clickPosition are inside of the button
     * @param clickPosX  x-coordinate (pixels) of the click
     * @param clickPosY  y-coordinate (pixels) of the click
     */
    public void ifClicked(int clickPosX, int clickPosY){
         if (clickPosX >= posX && clickPosX < (posX + width) && clickPosY >= posY && clickPosY < (posY +height)){
             handler.onButtonClick(frame);
         }


    }
}
