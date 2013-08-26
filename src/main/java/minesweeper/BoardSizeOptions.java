package minesweeper;

/**
 * @author  Shiera
 * enum with size settings
 */
public enum BoardSizeOptions {
    SMALL(5,5),
    MEDIUM(12,12),
    LARGE(20,19),
    HUGE(35,20);


    private int width;
    private int height;

    private BoardSizeOptions(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }





}
