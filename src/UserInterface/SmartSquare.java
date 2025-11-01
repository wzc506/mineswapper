package UserInterface;
import javax.swing.*;

import Library.TimeChecker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SmartSquare extends GameSquare implements MouseListener, TimeChecker
{
	private boolean thisSquareHasBomb;

	private boolean guessThisSquareIsBomb;

	private boolean thisSquareHasTraversed;

	private int xLocation;

	private int yLocation;

	private long startTime;

	public SmartSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/block.png", board);

		xLocation = x;
		yLocation = y;

		thisSquareHasBomb = false;
		thisSquareHasTraversed = false;
		guessThisSquareIsBomb = false;
		startTime = 0;

		addMouseListener(this);
	}

	protected void setBombExist(boolean result)
	{
		thisSquareHasBomb = result;
	}

	protected boolean getBombExist()
	{
		return thisSquareHasBomb;
	}

	protected boolean getTraverse()
	{
		return thisSquareHasTraversed;
	}

	protected void setTraverse(boolean result)
	{
		thisSquareHasTraversed = result;
	}

	protected boolean getGuessThisSquareIsBomb()
	{
		return guessThisSquareIsBomb;
	}

	protected void setStartTime(long time)
	{
		startTime = time;
	}

	protected long getStartTime()
	{
		return startTime;
	}

	public void clicked()
	{

		CheckSquare cq = new CheckSquare(board);

		guessThisSquareIsBomb = false;

		if(thisSquareHasBomb)
		{
			setImage("images/bombReveal.png");
			long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
			cq.showBomb(xLocation, yLocation);
			window("You used " + TimeChecker.calculateTime(costTime) +". Do you want to try again?", "Game Over",
					new ImageIcon("images/failFace.png"));
		} else{
			thisSquareHasTraversed = false;

			cq.countBomb(xLocation, yLocation);

			if (cq.isSuccess()) {
				long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
				cq.showBomb(xLocation, yLocation);
				window("You win this game in " + TimeChecker.calculateTime(costTime) +
                        "! Do you want to try again?","Congratulations",
						new ImageIcon("images/passFace.jpg"));
			}
		}
	}

	public void window(String msg, String title, Icon img)
	{

		int choose = JOptionPane.showConfirmDialog(board, msg, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,img);

		if (choose == JOptionPane.YES_OPTION)
		{
			Menu menu = new Menu("Mine sweeper");
		}

		board.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			int clickCount = e.getClickCount();

			if (clickCount == 1)
			{
				setImage("images/redFlag.png");
				guessThisSquareIsBomb = true;
			}

			if (clickCount == 2)
			{
				setImage("images/questionMark.png");
				guessThisSquareIsBomb = false;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}