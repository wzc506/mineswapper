package Library;
import UserInterface.GameBoard;

public abstract class Bomb
{
    protected GameBoard board;

    protected int boardHeight;

    protected int boardWidth;

    public Bomb(GameBoard board)
    {
        this.board = board;
        // Both height and width of the board should remove its padding values.
        boardHeight = (board.getHeight() - 20) / 20;
        boardWidth = (board.getWidth() - 20) / 20;
    }
    
    protected abstract void reproduceBomb();

}
