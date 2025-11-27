public class CheckSquare {
    private GameBoard board;
    private int boardHeight;
    private int boardWidth;

    private static final int[] distantX = {-1, 0, 1};
    private static final int[] distantY = {-1, 0, 1};

    public CheckSquare(GameBoard board) {
        this.board = board;
        this.boardHeight = board.getBoardHeight();
        this.boardWidth = board.getBoardWidth();
    }

    private boolean hasKickedBoundary(int x, int y) {
        return x < 0 || x >= boardWidth || y < 0 || y >= boardHeight;
    }

    protected boolean isSuccess() {
        int count = 0;
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                if (board.getSquareAt(x, y).getTraverse())
                    count++;
            }
        }
        // The win condition is when all non-bomb squares are revealed.
        return count == (boardHeight * boardWidth) - board.getNumberOfBombs();
    }

    protected void showBomb(int currentX, int currentY) {
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                GameSquare square = board.getSquareAt(x, y);
                if (currentX == x && currentY == y) {}
                else if (square.getBombExist()) {
                    square.setImage("images/bomb.png");
                } else if (square.getGuessThisSquareIsBomb()) {
                    square.setImage("images/flagWrong.png");
                }
            }
        }
    }

    protected void countBomb(int currentX, int currentY) {
        if (hasKickedBoundary(currentX, currentY)) return;

        GameSquare currentObject = board.getSquareAt(currentX, currentY);
        if (currentObject.getTraverse()) return;

        currentObject.setTraverse(true);
        int count = 0;

        for (int x : distantX) {
            for (int y : distantY) {
                if (hasKickedBoundary(currentX + x, currentY + y) || (x == 0 && y == 0)) continue;
                
                GameSquare squareObject = board.getSquareAt(currentX + x, currentY + y);
                if (squareObject.getBombExist()) {
                    count++;
                }
            }
        }

        if (count != 0) {
            currentObject.setImage("images/" + count + ".png");
        } else {
            currentObject.setImage("images/0.png");
            for (int x : distantX) {
                for (int y : distantY) {
                    if (x == 0 && y == 0) continue;
                    countBomb(currentX + x, currentY + y);
                }
            }
        }
    }
}
