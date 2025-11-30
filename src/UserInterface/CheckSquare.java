package UserInterface;

import Library.ThemeManager;

public class CheckSquare
{
    private GameBoard board;

    private int boardHeight;

    private int boardWidth;

    private static final int[] distantX = {-1, 0, 1};
    private static final int[] distantY = {-1, 0, 1};

    public CheckSquare(GameBoard board)
    {
        this.board = board;
        boardHeight = board.getBoardHeight();
        boardWidth = board.getBoardWidth();
    }

    private boolean hasKickedBoundary(int x, int y)
    {
        return x < 0 || x >= boardWidth || y < 0 || y >= boardHeight;
    }

    protected boolean isSuccess()
    {
        int count = 0;

        for (int y = 0; y < boardHeight; y++)
        {
            for (int x = 0; x < boardWidth; x++)
            {
                if (((SmartSquare) board.getSquareAt(x, y)).getTraverse())
                    count++;
            }
        }

        return count == boardHeight * boardWidth;
    }

    protected void showBomb(int currentX, int currentY)
    {
        ThemeManager theme = ThemeManager.getInstance();
        
        for (int y = 0; y < boardHeight; y++)
        {
            for (int x = 0; x < boardWidth; x++)
            {
                if (currentX == x && currentY == y){}
                else if (((SmartSquare) board.getSquareAt(x, y)).getBombExist())
                    board.getSquareAt(x, y).setImage(theme.getBombImage());
                else if(((SmartSquare) board.getSquareAt(x, y)).getGuessThisSquareIsBomb())
                    board.getSquareAt(x, y).setImage(theme.getFlagWrongImage()); // Wrong guess!
            }
        }
    }

    protected void countBomb(int currentX, int currentY)
    {
        int count = 0;
        SmartSquare currentObject;
        ThemeManager theme = ThemeManager.getInstance();

        if (hasKickedBoundary(currentX, currentY))
            return;
        else if(((SmartSquare)board.getSquareAt(currentX, currentY)).getTraverse())
            return;
        else {
            SmartSquare squareObject;

            currentObject = (SmartSquare)board.getSquareAt(currentX, currentY);
            currentObject.setTraverse(true);

            for (int x : distantX)
            {
                for (int y: distantY)
                {
                    if (hasKickedBoundary(currentX + x, currentY + y)){}
                    else if (x == 0 && y == 0){}
                    else{
                        squareObject = (SmartSquare)board.getSquareAt(currentX + x, currentY + y);
                        count = squareObject.getBombExist() ? count + 1 : count;
                    }
                }
            }
        }

        if (count != 0)
            currentObject.setImage(theme.getNumberImagePath(count));
        else {
            currentObject.setImage(theme.getNumberImagePath(0));
            countBomb(currentX - 1, currentY -1); 
            countBomb(currentX, currentY -1); 
            countBomb(currentX + 1, currentY -1);
            countBomb(currentX - 1, currentY); 
            countBomb(currentX + 1, currentY); 
            countBomb(currentX - 1, currentY + 1); 
            countBomb(currentX, currentY + 1); 
            countBomb(currentX + 1, currentY + 1);
        }
    }
}
