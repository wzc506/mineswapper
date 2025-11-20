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
        boardHeight = (board.getHeight() - 20) / 20;
        boardWidth = (board.getWidth() - 20) / 20;
    }
    
    protected abstract void reproduceBomb();

}
