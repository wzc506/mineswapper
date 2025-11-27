package UserInterface;
import javax.swing.*;

import Library.MusicPlayer;

import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JFrame implements ActionListener
{
	private JPanel boardPanel = new JPanel();

	private int boardHeight;
	private int boardWidth;
	private GameSquare[][] board;
	
	private boolean isMusicPlaying = true;
	private JButton playPauseBtn;

	public GameBoard(String title, int width, int height)
	{
		super();

		this.boardWidth = width;
		this.boardHeight = height;

		this.board = new GameSquare[width][height];

		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		boardPanel.setLayout(new GridLayout(height, width));

		for (int y = 0; y<height; y++)
		{
			for (int x = 0; x<width; x++)
			{
				board[x][y] = new SmartSquare(x, y, this);
				board[x][y].addActionListener(this);

				boardPanel.add(board[x][y]);
			}
		}

		add(boardPanel, BorderLayout.CENTER);
		
		createMusicControlPanel();

		setSize(20 + width * 20, 55 + height * 20);
		setResizable(false);
		setVisible(true);
	}

	private void createMusicControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		isMusicPlaying = MusicPlayer.getInstance().isPlaying();
		
		playPauseBtn = new JButton(isMusicPlaying ? "暂停" : "播放");
		playPauseBtn.setPreferredSize(new Dimension(60, 25));
		playPauseBtn.addActionListener(e -> {
			MusicPlayer player = MusicPlayer.getInstance();
			if (isMusicPlaying) {
				player.pause();
				playPauseBtn.setText("播放");
				isMusicPlaying = false;
			} else {
				player.resume();
				playPauseBtn.setText("暂停");
				isMusicPlaying = true;
			}
		});
		controlPanel.add(playPauseBtn);
		
		JButton nextBtn = new JButton("下一首");
		nextBtn.setPreferredSize(new Dimension(80, 25));
		nextBtn.addActionListener(e -> {
			MusicPlayer.getInstance().playNext();
			playPauseBtn.setText("暂停");
			isMusicPlaying = true;
		});
		controlPanel.add(nextBtn);
		
		add(controlPanel, BorderLayout.SOUTH);
	}

	public GameSquare getSquareAt(int x, int y)
	{
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
			return null;

		return board[x][y];
	}

	public int getBoardWidth()
	{
		return boardWidth;
	}

	public int getBoardHeight()
	{
		return boardHeight;
	}

	public void actionPerformed(ActionEvent e)
	{
		GameSquare b = (GameSquare)e.getSource();
		b.clicked();
	}
}
