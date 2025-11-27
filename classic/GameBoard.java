import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBoard extends JFrame {
    private final int boardHeight;
    private final int boardWidth;
    private final int numberOfBombs;
    private final GameSquare[][] board;
    private final CheckSquare checker;
    private long startTime;
    private boolean gameEnded = false;

    public GameBoard(String title, int width, int height, int bombs) {
        super(title);
        this.boardWidth = width;
        this.boardHeight = height;
        this.numberOfBombs = bombs;
        this.board = new GameSquare[width][height];
        this.checker = new CheckSquare(this);

        setTitle(title);
        setSize(20 + width * 20, 20 + height * 20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(height, width));
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[x][y] = new GameSquare(x, y);
                board[x][y].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        handleSquareClick((GameSquare) e.getSource(), e.getButton(), e.getClickCount());
                    }
                });
                boardPanel.add(board[x][y]);
            }
        }

        setContentPane(boardPanel);
        new BombPlacer(this, bombs);
        this.startTime = System.currentTimeMillis();
        
        setVisible(true);
        setResizable(false);
    }

    private void handleSquareClick(GameSquare square, int button, int clickCount) {
        if (gameEnded || square.getTraverse()) {
            return;
        }

        // Right-click handling
        if (button == MouseEvent.BUTTON3) {
            if (!square.getGuessThisSquareIsBomb()) {
                square.setImage("images/redFlag.png");
                square.setGuessThisSquareIsBomb(true);
            } else {
                square.setImage("images/block.png");
                square.setGuessThisSquareIsBomb(false);
            }
            return;
        }

        // Left-click handling
        if (button == MouseEvent.BUTTON1) {
            square.setGuessThisSquareIsBomb(false);

            if (square.getBombExist()) {
                square.setImage("images/bombReveal.png");
                endGame(false, square.getXLocation(), square.getYLocation());
            } else {
                checker.countBomb(square.getXLocation(), square.getYLocation());
                if (checker.isSuccess()) {
                    endGame(true, -1, -1);
                }
            }
        }
    }

    private void endGame(boolean win, int x, int y) {
        gameEnded = true;
        long costTime = System.currentTimeMillis() - startTime;
        checker.showBomb(x, y);

        String msg;
        String title;
        ImageIcon img;

        if (win) {
            msg = "你赢了！用时 " + TimeChecker.calculateTime(costTime) + "! 你想再试一次吗?";
            title = "恭喜";
            img = new ImageIcon("images/passFace.jpg");
        } else {
            msg = "你输了！用时 " + TimeChecker.calculateTime(costTime) + ". 你想再试一次吗?";
            title = "游戏结束";
            img = new ImageIcon("images/cai.jpg");
        }

        int choose = JOptionPane.showConfirmDialog(this, msg, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, img);

        if (choose == JOptionPane.YES_OPTION) {
            new Menu("扫雷");
        }
        this.dispose();
    }

    public GameSquare getSquareAt(int x, int y) {
        if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
            return null;
        return board[x][y];
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }
    
    public int getNumberOfBombs() {
        return numberOfBombs;
    }
}
