package UserInterface;
import javax.swing.*;

public abstract class GameSquare extends JButton
{
	protected int xLocation;

	protected int yLocation;

	protected GameBoard board;

	public GameSquare(int x, int y, String filename, GameBoard board)
	{
		super(new ImageIcon(filename));

		this.board = board;
		xLocation = x;
		yLocation = y;
	}

	public void setImage(String filename)
	{
		this.setIcon(new ImageIcon(filename));
	}

	public abstract void clicked();
}
