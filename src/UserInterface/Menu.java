package UserInterface;
import javax.swing.*;

import Library.MusicPlayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class Menu extends JFrame implements ActionListener
{
    private JButton start;
    private JRadioButton beginner, intermediate, advanced, custom;
    private JTextField width, height, mines;
    private JCheckBox musicCheckBox;
    private JComboBox<String> musicSelector;
    private JSlider volumeSlider;

    public Menu(String title)
    {
        setTitle(title);

        JLabel subtitle = new JLabel("难度选择");
        subtitle.setBounds(100,10,100,20);
        add(subtitle);

        beginner = new JRadioButton("新手");
        beginner.setBounds(40,40,150,20);
        add(beginner);

        JLabel bDescFirstLine = new JLabel("10 地雷");
        bDescFirstLine.setBounds(70,60,100,20);
        JLabel bDescSecondLine = new JLabel("10 x 10 方格");
        bDescSecondLine.setBounds(70,80,100,20);
        add(bDescFirstLine);
        add(bDescSecondLine);

        intermediate=new JRadioButton("中级");
        intermediate.setBounds(40,100,150,20);
        add(intermediate);

        JLabel iDescFirstLine = new JLabel("40 地雷");
        iDescFirstLine.setBounds(70,120,100,20);
        JLabel iDescSecondLine = new JLabel("16 x 16 方格");
        iDescSecondLine.setBounds(70,140,100,20);
        add(iDescFirstLine);
        add(iDescSecondLine);

        advanced=new JRadioButton("高级");
        advanced.setBounds(40,160,160,20);
        add(advanced);

        JLabel aDescFirstLine = new JLabel("100 地雷");
        aDescFirstLine.setBounds(70,180,100,20);
        JLabel aDescSecondLine = new JLabel("30 x 25 方格");
        aDescSecondLine.setBounds(70,200,100,20);
        add(aDescFirstLine);
        add(aDescSecondLine);

        custom = new JRadioButton("自定义");
        custom.setBounds(40,220,100,20);
        add(custom);

        JLabel widthLabel = new JLabel("宽度 (10-30):");
        widthLabel.setBounds(70,240,80,20);
        add(widthLabel);

        width = new JTextField();
        width.setBounds(170,240,40,20);
        add(width);

        JLabel heightLabel = new JLabel("高度 (10-25):");
        heightLabel.setBounds(70,260,90,20);
        add(heightLabel);

        height = new JTextField();
        height.setBounds(170,260,40,20);
        add(height);

        JLabel mineLabel = new JLabel("地雷 (10-100):");
        mineLabel.setBounds(70,280,90,20);
        add(mineLabel);

        mines = new JTextField();
        mines.setBounds(170,280,40,20);
        add(mines);

        musicCheckBox = new JCheckBox("播放背景音乐", false);
        musicCheckBox.setBounds(40,300,120,20);
        add(musicCheckBox);

        // 音乐选择下拉框
        JLabel musicLabel = new JLabel("选择音乐:");
        musicLabel.setBounds(40,325,70,20);
        add(musicLabel);

        musicSelector = new JComboBox<>();
        musicSelector.addItem("随机播放");
        // 从 MusicPlayer 获取音乐列表
        for (String musicName : MusicPlayer.getInstance().getMusicNames()) {
            musicSelector.addItem(musicName);
        }
        musicSelector.setBounds(110,325,150,20);
        add(musicSelector);

        // 音量控制
        JLabel volumeLabel = new JLabel("音量:");
        volumeLabel.setBounds(40,350,40,20);
        add(volumeLabel);

        volumeSlider = new JSlider(0, 100, 80);
        volumeSlider.setBounds(80,350,150,20);
        volumeSlider.addChangeListener(e -> {
            float volume = volumeSlider.getValue() / 100.0f;
            MusicPlayer.getInstance().setVolume(volume);
        });
        add(volumeSlider);

        start = new JButton("开始游戏");
        start.setBounds(80,385,100,20);
        add(start);

        width.setEditable(false);
        height.setEditable(false);
        mines.setEditable(false);

        custom.addActionListener(this);
        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);
        start.addActionListener(this);

        ButtonGroup group = new ButtonGroup();
        group.add(beginner);
        group.add(intermediate);
        group.add(advanced);
        group.add(custom);

        beginner.setSelected(true);
        setSize(280,470);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == custom)
        {
            width.setEditable(true);
            height.setEditable(true);
            mines.setEditable(true);
        } else if (e.getSource() == start) {
            int boardWidth = 0;
            int boardHeight = 0;
            int bombs = 0;
            boolean errorFlag = false;

            if (beginner.isSelected())
            {
                boardWidth = 10;
                boardHeight = 10;
                bombs = 10;
            } else if (intermediate.isSelected()) {
                boardWidth = 16;
                boardHeight = 16;
                bombs = 40;
            } else if (advanced.isSelected()) {
                boardWidth = 30;
                boardHeight = 25;
                bombs = 100;
            } else {
                if(!checkValid(width.getText(), height.getText(), mines.getText()))
                {
                    errorFlag = true;
                    JOptionPane.showMessageDialog(null, "请输入正确的数字!");

                } else {
                    boardWidth = Integer.parseInt(width.getText());
                    boardHeight = Integer.parseInt(height.getText());
                    bombs = Integer.parseInt(mines.getText());
                }
            }

            if(!errorFlag)
            {
                // 根据复选框状态控制音乐播放
                if (musicCheckBox.isSelected()) {
                    // 获取选择的音乐索引（0 是"随机播放"，所以实际索引要减1）
                    int selectedMusicIndex = musicSelector.getSelectedIndex() - 1;
                    MusicPlayer.getInstance().setSelectedIndex(selectedMusicIndex);
                    MusicPlayer.getInstance().play();
                } else {
                    MusicPlayer.getInstance().stop();
                }

                this.dispose();
                GameBoard b = new GameBoard("扫雷", boardWidth, boardHeight);
                ProduceBombs.getInstance().placeBombs(b, bombs);
                ((SmartSquare) b.getSquareAt(0, 0)).setStartTime(System.currentTimeMillis());
            }

        } else{
            width.setEditable(false);
            height.setEditable(false);
            mines.setEditable(false);
        }
    }

    private boolean checkValid(String bWidth, String bHeight, String bomb)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (bWidth == null || bHeight== null || bomb == null)
            return false;
        else if (bWidth.isEmpty() || bHeight.isEmpty() || bomb.isEmpty())
            return false;
        else if (!pattern.matcher(bWidth).matches() || !pattern.matcher(bHeight).matches() || !pattern.matcher(bomb).matches())
            return false;
        else if (Integer.parseInt(bWidth) < 10 || Integer.parseInt(bWidth) > 30 || Integer.parseInt(bHeight) < 10 || Integer.parseInt(bHeight) > 25
                || Integer.parseInt(bomb) < 10 || Integer.parseInt(bomb) > 100)
            return false;
        else
            return Integer.parseInt(bWidth) * Integer.parseInt(bHeight) >= Integer.parseInt(bomb);
    }
}
