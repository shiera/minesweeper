package minesweeper;

public enum TileAppearence {

    // tällä hetkellä kaikilla sama testikuva
    GRASS("//home//cec//projects//minesweeper//src//pictures//grass.jpg"),
    FLAG("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    BOMBFIELD("//home//cec//projects//minesweeper//src//pictures//bomb.jpg"),
    NUMBER0("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER1("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER2("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER3("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER4("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER5("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER6("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER7("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER8("//home//cec//projects//minesweeper//src//pictures//dirt.jpg"),
    NUMBER9("//home//cec//projects//minesweeper//src//pictures//dirt.jpg");

    private String imageName;

    private TileAppearence(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }
}
