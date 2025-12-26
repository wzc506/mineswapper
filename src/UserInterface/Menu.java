package UserInterface;

import Library.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

/**
 * 游戏菜单界面
 * 实现 RecordObserver 接口，当记录更新时自动刷新显示
 */
public class Menu extends JFrame implements ActionListener, RecordObserver
{
    private JButton start;
    private JRadioButton beginner, intermediate, advanced, custom;
    private JTextField width, height, mines;
    private JCheckBox musicCheckBox;
    private JComboBox<String> musicSelector;
    private JComboBox<String> themeSelector;
    private JSlider volumeSlider;
    private JTextArea recordsArea;
    private String currentModeName = "新手"; // 当前选中的模式名称

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

        // 主题选择
        JLabel themeLabel = new JLabel("游戏主题:");
        themeLabel.setBounds(40, 375, 70, 20);
        add(themeLabel);

        themeSelector = new JComboBox<>();
        ThemeManager themeManager = ThemeManager.getInstance();
        for (String theme : themeManager.getAvailableThemes()) {
            themeSelector.addItem(themeManager.getThemeDisplayName(theme));
        }
        
        // 根据保存的主题设置下拉框的选中项
        String currentTheme = themeManager.getCurrentTheme();
        java.util.List<String> availableThemes = themeManager.getAvailableThemes();
        int savedThemeIndex = availableThemes.indexOf(currentTheme);
        if (savedThemeIndex >= 0) {
            themeSelector.setSelectedIndex(savedThemeIndex);
        }
        
        themeSelector.setBounds(110, 375, 100, 20);
        themeSelector.addActionListener(e -> {
            int selectedIndex = themeSelector.getSelectedIndex();
            if (selectedIndex >= 0) {
                String themeName = themeManager.getAvailableThemes().get(selectedIndex);
                themeManager.setCurrentTheme(themeName);
            }
        });
        add(themeSelector);

        start = new JButton("开始游戏");
        start.setBounds(80, 410, 100, 25);
        add(start);

        // 右侧记录显示区域
        JLabel recordTitle = new JLabel("═══ 最佳记录 ═══");
        recordTitle.setBounds(280, 10, 150, 20);
        recordTitle.setFont(new java.awt.Font("微软雅黑", java.awt.Font.BOLD, 12));
        add(recordTitle);

        recordsArea = new JTextArea();
        recordsArea.setEditable(false);
        recordsArea.setFont(new java.awt.Font("微软雅黑", java.awt.Font.PLAIN, 11));
        recordsArea.setBackground(getBackground());
        recordsArea.setBorder(null);
        
        JScrollPane scrollPane = new JScrollPane(recordsArea);
        scrollPane.setBounds(270, 35, 180, 370);
        scrollPane.setBorder(BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY));
        add(scrollPane);
        
        // 初始化记录显示（默认显示新手模式）
        updateRecordsDisplay("新手");

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
        setSize(470, 500);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // 注册为记录观察者
        RecordManager.getInstance().addObserver(this);
        
        // 窗口关闭时移除观察者
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                RecordManager.getInstance().removeObserver(Menu.this);
            }
        });
    }
    
    /**
     * 观察者模式回调 - 当记录更新时自动刷新显示
     */
    @Override
    public void onRecordUpdated(String modeName) {
        if (modeName.equals(currentModeName)) {
            updateRecordsDisplay(modeName);
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == custom)
        {
            width.setEditable(true);
            height.setEditable(true);
            mines.setEditable(true);
            currentModeName = "自定义";
            updateRecordsDisplay(currentModeName);
        } else if (e.getSource() == beginner) {
            width.setEditable(false);
            height.setEditable(false);
            mines.setEditable(false);
            currentModeName = GameModeFactory.createMode(GameModeFactory.BEGINNER).getName();
            updateRecordsDisplay(currentModeName);
        } else if (e.getSource() == intermediate) {
            width.setEditable(false);
            height.setEditable(false);
            mines.setEditable(false);
            currentModeName = GameModeFactory.createMode(GameModeFactory.INTERMEDIATE).getName();
            updateRecordsDisplay(currentModeName);
        } else if (e.getSource() == advanced) {
            width.setEditable(false);
            height.setEditable(false);
            mines.setEditable(false);
            currentModeName = GameModeFactory.createMode(GameModeFactory.ADVANCED).getName();
            updateRecordsDisplay(currentModeName);
        } else if (e.getSource() == start) {
            startGame();
        }
    }
    
    /**
     * 开始游戏 - 使用工厂方法模式创建游戏模式
     */
    private void startGame() {
        GameMode mode;
        boolean errorFlag = false;

        if (beginner.isSelected()) {
            mode = GameModeFactory.createMode(GameModeFactory.BEGINNER);
        } else if (intermediate.isSelected()) {
            mode = GameModeFactory.createMode(GameModeFactory.INTERMEDIATE);
        } else if (advanced.isSelected()) {
            mode = GameModeFactory.createMode(GameModeFactory.ADVANCED);
        } else {
            // 自定义模式
            if (!checkValid(width.getText(), height.getText(), mines.getText())) {
                JOptionPane.showMessageDialog(null, "请输入正确的数字!");
                return;
            }
            int customWidth = Integer.parseInt(width.getText());
            int customHeight = Integer.parseInt(height.getText());
            int customBombs = Integer.parseInt(mines.getText());
            mode = GameModeFactory.createCustomMode(customWidth, customHeight, customBombs);
        }

        // 根据复选框状态控制音乐播放
        if (musicCheckBox.isSelected()) {
            int selectedMusicIndex = musicSelector.getSelectedIndex() - 1;
            MusicPlayer.getInstance().setSelectedIndex(selectedMusicIndex);
            MusicPlayer.getInstance().play();
        } else {
            MusicPlayer.getInstance().stop();
        }

        // 移除观察者
        RecordManager.getInstance().removeObserver(this);
        this.dispose();
        
        // 使用工厂模式创建的 GameMode 对象启动游戏
        GameBoard b = new GameBoard("扫雷", mode.getWidth(), mode.getHeight());
        b.setBombCount(mode.getBombs());
        b.setModeName(mode.getName());
        ProduceBombs.getInstance().placeBombs(b, mode.getBombs());
        ((SmartSquare) b.getSquareAt(0, 0)).setStartTime(System.currentTimeMillis());
    }

    /**
     * 更新右侧记录显示区域
     * @param modeName 当前选择的模式名称
     */
    private void updateRecordsDisplay(String modeName) {
        String content = RecordManager.getInstance().getFormattedRecords(modeName);
        recordsArea.setText(content);
        recordsArea.setCaretPosition(0); // 滚动到顶部
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
