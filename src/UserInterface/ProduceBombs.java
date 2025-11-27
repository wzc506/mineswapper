package UserInterface;
import java.util.Random;

public final class ProduceBombs {
    private static final ProduceBombs INSTANCE = new ProduceBombs();
    private final Random random = new Random();

    private ProduceBombs() {}

    public static ProduceBombs getInstance() {
        return INSTANCE;
    }

    public void placeBombs(GameBoard board, int number) {
        int placed = 0;
        while (placed < number) {
            if (placeOne(board)) {
                placed++;
            }
        }
    }

    private boolean placeOne(GameBoard board) {
        int boardWidth = board.getBoardWidth();
        int boardHeight = board.getBoardHeight();

        int x = random.nextInt(boardWidth);
        int y = random.nextInt(boardHeight);
        SmartSquare square = (SmartSquare) board.getSquareAt(x, y);
        if (!square.getBombExist()) {
            square.setBombExist(true);
            square.setTraverse(true);
            return true;
        }
        return false;
    }
}
