package minesweeper;

/**
 * Author: Shiera
 */
public enum DifficultyOptions {
    EASY(12),
    HARD(18),
    NIGHTMARE(25);

    private double bombAmountPercentage;

    private DifficultyOptions(int bombAmountPercentage) {
        this.bombAmountPercentage = bombAmountPercentage;
    }

    public double getBombAmountPercentage() {
        return bombAmountPercentage;
    }
}
