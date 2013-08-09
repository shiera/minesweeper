package minesweeper;

public enum TileAppearence {

    // tällä hetkellä kaikilla sama testikuva
    GRASS("//home//cec//projects//minesweeper//src//pictures//grass.jpg"),
    FLAG("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    BOMBFIELD("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER0("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER1("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER2("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER3("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER4("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER5("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER6("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER7("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER8("//home//cec//projects//minesweeper//src//pictures//test.jpg"),
    NUMBER9("//home//cec//projects//minesweeper//src//pictures//test.jpg");

    private String imageName;

    private TileAppearence(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }
}
