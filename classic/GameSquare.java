import javax.swing.*;

public class GameSquare extends JButton {
    private final int xLocation;
    private final int yLocation;
    private boolean thisSquareHasBomb;
    private boolean guessThisSquareIsBomb;
    private boolean thisSquareHasTraversed;

    public GameSquare(int x, int y) {
        super(new ImageIcon("images/block.png"));
        this.xLocation = x;
        this.yLocation = y;
        this.thisSquareHasBomb = false;
        this.thisSquareHasTraversed = false;
        this.guessThisSquareIsBomb = false;
    }

    public int getXLocation() {
        return xLocation;
    }

    public int getYLocation() {
        return yLocation;
    }

    public void setImage(String filename) {
        this.setIcon(new ImageIcon(filename));
    }

    protected void setBombExist(boolean result) {
        thisSquareHasBomb = result;
    }

    protected boolean getBombExist() {
        return thisSquareHasBomb;
    }

    protected boolean getTraverse() {
        return thisSquareHasTraversed;
    }

    protected void setTraverse(boolean result) {
        thisSquareHasTraversed = result;
    }

    protected boolean getGuessThisSquareIsBomb() {
        return guessThisSquareIsBomb;
    }

    protected void setGuessThisSquareIsBomb(boolean guess) {
        this.guessThisSquareIsBomb = guess;
    }
}
