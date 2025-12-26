package UserInterface;

import Library.MusicPlayer;
import Library.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame implements ActionListener
{
	private JPanel boardPanel = new JPanel();

	private int boardHeight;
	private int boardWidth;
	private int bombCount; // 炸弹数量
	private String modeName; // 模式名称
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

		// 获取当前主题方块图片的尺寸
		Dimension blockSize = getThemeBlockSize();
		int blockWidth = blockSize.width;
		int blockHeight = blockSize.height;

		// 设置面板的固定大小，确保GridLayout能正确布局
		boardPanel.setPreferredSize(new Dimension(width * blockWidth, height * blockHeight));
		boardPanel.setLayout(new GridLayout(height, width));

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				board[x][y] = new SmartSquare(x, y, this);
				board[x][y].addActionListener(this);

				// 强制设置按钮的固定大小
				board[x][y].setPreferredSize(new Dimension(blockWidth, blockHeight));
				board[x][y].setMinimumSize(new Dimension(blockWidth, blockHeight));
				board[x][y].setMaximumSize(new Dimension(blockWidth, blockHeight));

				boardPanel.add(board[x][y]);
			}
		}

		add(boardPanel, BorderLayout.CENTER);

		createMusicControlPanel();

		// 根据实际方块大小计算窗口尺寸
		int windowWidth = boardPanel.getPreferredSize().width + getInsets().left + getInsets().right;
		int windowHeight = boardPanel.getPreferredSize().height + getInsets().top + getInsets().bottom + 50;

		setSize(windowWidth, windowHeight);
		setResizable(false);
		setVisible(true);

		// 验证布局
		validate();
	}

	/**
	 * 获取当前主题方块图片的实际尺寸
	 */
	private Dimension getThemeBlockSize() {
		int defaultWidth = 30;
		int defaultHeight = 30;

		try {
			ThemeManager themeManager = ThemeManager.getInstance();

			// 获取方块图片路径
			String blockImagePath = themeManager.getBlockImage();
			System.out.println("方块图片路径: " + blockImagePath);

			// 直接使用图片路径创建ImageIcon
			ImageIcon blockIcon = new ImageIcon(blockImagePath);

			// 检查图片是否有效加载
			if (blockIcon.getIconWidth() > 0 && blockIcon.getIconHeight() > 0) {
				System.out.println("成功加载方块图片尺寸: " + blockIcon.getIconWidth() + "x" + blockIcon.getIconHeight());
				return new Dimension(blockIcon.getIconWidth(), blockIcon.getIconHeight());
			} else {
				// 图片可能还没完全加载，尝试等待
				MediaTracker tracker = new MediaTracker(this);
				Image image = blockIcon.getImage();
				tracker.addImage(image, 0);

				try {
					tracker.waitForID(0, 1000); // 等待最多1秒
					if (!tracker.isErrorID(0)) {
						int width = blockIcon.getIconWidth();
						int height = blockIcon.getIconHeight();
						if (width > 0 && height > 0) {
							System.out.println("通过MediaTracker获取尺寸: " + width + "x" + height);
							return new Dimension(width, height);
						}
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (Exception e) {
			System.err.println("获取主题方块尺寸失败: " + e.getMessage());
			e.printStackTrace();
		}

		// 所有方法都失败，使用默认尺寸
		System.out.println("使用默认方块尺寸: " + defaultWidth + "x" + defaultHeight);
		return new Dimension(defaultWidth, defaultHeight);
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

	public void setBombCount(int count)
	{
		this.bombCount = count;
	}

	public int getBombCount()
	{
		return bombCount;
	}

	public void setModeName(String name)
	{
		this.modeName = name;
	}

	public String getModeName()
	{
		return modeName;
	}

	public void actionPerformed(ActionEvent e)
	{
		GameSquare b = (GameSquare)e.getSource();
		b.clicked();
	}
}