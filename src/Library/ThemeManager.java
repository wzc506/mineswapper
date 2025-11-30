package Library;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 主题管理器 - 单例模式
 * 管理游戏的皮肤主题，支持切换不同风格的图片资源
 */
public class ThemeManager {
    
    private static ThemeManager instance;
    
    private String currentTheme;
    private List<String> availableThemes;
    private static final String THEMES_FOLDER = "themes";
    private static final String DEFAULT_THEME = "default";
    
    // 图片资源名称常量
    public static final String BLOCK = "block.png";
    public static final String BOMB = "bomb.png";
    public static final String BOMB_REVEAL = "bombReveal.png";
    public static final String FLAG = "redFlag.png";
    public static final String FLAG_WRONG = "flagWrong.png";
    public static final String QUESTION_MARK = "questionMark.png";
    public static final String FAIL_FACE = "cai.jpg";
    public static final String PASS_FACE = "passFace.jpg";
    
    private ThemeManager() {
        availableThemes = new ArrayList<>();
        scanThemes();
        currentTheme = DEFAULT_THEME;
    }
    
    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }
    
    /**
     * 扫描可用的主题
     */
    private void scanThemes() {
        availableThemes.clear();
        
        // 扫描 themes 文件夹下的所有子文件夹
        File themesDir = new File(THEMES_FOLDER);
        if (themesDir.exists() && themesDir.isDirectory()) {
            File[] themeFiles = themesDir.listFiles(File::isDirectory);
            if (themeFiles != null) {
                // 确保 default 主题在第一位
                for (File themeDir : themeFiles) {
                    if (themeDir.getName().equals(DEFAULT_THEME) && isValidTheme(themeDir)) {
                        availableThemes.add(themeDir.getName());
                        break;
                    }
                }
                // 添加其他主题
                for (File themeDir : themeFiles) {
                    if (!themeDir.getName().equals(DEFAULT_THEME) && isValidTheme(themeDir)) {
                        availableThemes.add(themeDir.getName());
                    }
                }
            }
        }
        
        // 如果没有找到任何主题，添加默认主题
        if (availableThemes.isEmpty()) {
            availableThemes.add(DEFAULT_THEME);
        }
    }
    
    /**
     * 检查主题文件夹是否有效（包含必要的图片文件）
     */
    private boolean isValidTheme(File themeDir) {
        String[] requiredFiles = {"block.png", "bomb.png", "0.png", "1.png"};
        for (String fileName : requiredFiles) {
            File file = new File(themeDir, fileName);
            if (!file.exists()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 获取当前主题名称
     */
    public String getCurrentTheme() {
        return currentTheme;
    }
    
    /**
     * 设置当前主题
     */
    public void setCurrentTheme(String themeName) {
        if (availableThemes.contains(themeName)) {
            this.currentTheme = themeName;
        }
    }
    
    /**
     * 获取所有可用主题列表
     */
    public List<String> getAvailableThemes() {
        return new ArrayList<>(availableThemes);
    }
    
    /**
     * 获取主题显示名称
     */
    public String getThemeDisplayName(String themeName) {
        switch (themeName) {
            case "default":
                return "经典";
            case "dark":
                return "暗黑";
            case "ocean":
                return "海洋";
            case "forest":
                return "森林";
            case "pixel":
                return "像素";
            default:
                return themeName;
        }
    }
    
    /**
     * 获取图片完整路径
     * @param imageName 图片名称（如 "block.png", "1.png"）
     * @return 完整的图片路径
     */
    public String getImagePath(String imageName) {
        String themePath = THEMES_FOLDER + "/" + currentTheme + "/" + imageName;
        // 如果主题中没有该图片，则使用默认主题图片
        if (new File(themePath).exists()) {
            return themePath;
        } else {
            return THEMES_FOLDER + "/" + DEFAULT_THEME + "/" + imageName;
        }
    }
    
    /**
     * 获取数字图片路径（0-8）
     */
    public String getNumberImagePath(int number) {
        return getImagePath(number + ".png");
    }
    
    /**
     * 获取方块图片路径
     */
    public String getBlockImage() {
        return getImagePath(BLOCK);
    }
    
    /**
     * 获取炸弹图片路径
     */
    public String getBombImage() {
        return getImagePath(BOMB);
    }
    
    /**
     * 获取炸弹揭示图片路径
     */
    public String getBombRevealImage() {
        return getImagePath(BOMB_REVEAL);
    }
    
    /**
     * 获取旗帜图片路径
     */
    public String getFlagImage() {
        return getImagePath(FLAG);
    }
    
    /**
     * 获取错误旗帜图片路径
     */
    public String getFlagWrongImage() {
        return getImagePath(FLAG_WRONG);
    }
    
    /**
     * 获取问号图片路径
     */
    public String getQuestionMarkImage() {
        return getImagePath(QUESTION_MARK);
    }
    
    /**
     * 获取失败表情图片路径
     */
    public String getFailFaceImage() {
        return getImagePath(FAIL_FACE);
    }
    
    /**
     * 获取胜利表情图片路径
     */
    public String getPassFaceImage() {
        return getImagePath(PASS_FACE);
    }
    
    /**
     * 刷新主题列表
     */
    public void refreshThemes() {
        scanThemes();
    }
}
