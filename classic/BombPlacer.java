import javax.swing.*;
import java.util.Random;

public class BombPlacer {
    private GameBoard board;
    private int boardHeight;
    private int boardWidth;
    private int numberOfBombs;

    public BombPlacer(GameBoard board, int number) {
        this.board = board;
        this.boardHeight = board.getBoardHeight();
        this.boardWidth = board.getBoardWidth();
        this.numberOfBombs = number;
        placeBombs();
    }

    private void placeBombs() {
        int count = 0;
        Random r = new Random();
        while (count < numberOfBombs) {
            int xLocation = r.nextInt(boardWidth);
            int yLocation = r.nextInt(boardHeight);
            GameSquare square = board.getSquareAt(xLocation, yLocation);
            if (!square.getBombExist()) {
                square.setBombExist(true);
                square.setTraverse(true); // This seems to be a bug, bombs should not be traversed initially
                count++;
            }
        }
    }
}
