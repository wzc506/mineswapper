package UserInterface;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JFrame implements ActionListener
{
	private JPanel boardPanel = new JPanel();

	private int boardHeight;
	private int boardWidth;
	private GameSquare[][] board;

	public GameBoard(String title, int width, int height)
	{
		super();

		this.boardWidth = width;
		this.boardHeight = height;

		this.board = new GameSquare[width][height];

		setTitle(title);
		setSize(20+width*20,20+height*20);
		setContentPane(boardPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardPanel.setLayout(new GridLayout(height,width));

		for (int y = 0; y<height; y++)
		{
			for (int x = 0; x<width; x++)
			{
				board[x][y] = new SmartSquare(x, y, this);
				board[x][y].addActionListener(this);

				boardPanel.add(board[x][y]);
			}
		}

		setVisible(true);

	}

	public GameSquare getSquareAt(int x, int y)
	{
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
			return null;

		return board[x][y];
	}

	public void actionPerformed(ActionEvent e)
	{
		GameSquare b = (GameSquare)e.getSource();
		b.clicked();
	}
}
