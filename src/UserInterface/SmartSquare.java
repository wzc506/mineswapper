package UserInterface;
import javax.swing.*;

import Library.MusicPlayer;
import Library.RecordManager;
import Library.TimeChecker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SmartSquare extends GameSquare implements MouseListener
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
			window("你输了！用时 " + TimeChecker.getInstance().format(costTime) + ". 你想再试一次吗?", "游戏结束",
					new ImageIcon("images/cai.jpg"));
		} else{
			thisSquareHasTraversed = false;

			cq.countBomb(xLocation, yLocation);

			if (cq.isSuccess()) {
				long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
				cq.showBomb(xLocation, yLocation);
				
				// 保存记录
				String modeName = board.getModeName();
				String recordMsg = "";
				if (modeName != null) {
					int rank = RecordManager.getInstance().addRecord(modeName, costTime);
					if (rank > 0) {
						recordMsg = "\n🎉 新纪录！排名第 " + rank + " 名！";
					}
				}
				
				window("你赢了！用时 " + TimeChecker.getInstance().format(costTime) +
                        "!" + recordMsg + "\n你想再试一次吗?","恭喜",
						new ImageIcon("images/passFace.jpg"));
			}
		}
	}

	public void window(String msg, String title, Icon img)
	{
		// 游戏结束时停止音乐
		MusicPlayer.getInstance().stop();

		int choose = JOptionPane.showConfirmDialog(board, msg, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,img);

		if (choose == JOptionPane.YES_OPTION)
		{
			new Menu("扫雷");
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