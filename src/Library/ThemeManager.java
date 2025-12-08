package Library;

import Library.Theme.*;
import javax.swing.ImageIcon;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 主题管理器 - 单例模式
 * 整合享元模式、抽象工厂模式和建造者模式
 */
public class ThemeManager {
    
    private static ThemeManager instance;
    
    private String currentTheme;
    private List<String> availableThemes;
    private static final String THEMES_FOLDER = "themes";
    private static final String DEFAULT_THEME = "default";
    private static final String SETTINGS_FILE = "theme_settings.dat";  // 主题设置保存文件
    
    // 设计模式组件
    private final ImageFlyweightFactory imageFlyweight;      // 享元模式
    private final ThemeFactoryProvider factoryProvider;       // 抽象工厂模式
    private final ThemeDirector themeDirector;                // 建造者模式
    
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
        
        // 初始化设计模式组件
        imageFlyweight = ImageFlyweightFactory.getInstance();
        factoryProvider = ThemeFactoryProvider.getInstance();
        themeDirector = new ThemeDirector();
        
        scanThemes();
        
        // 加载上次保存的主题设置
        String savedTheme = loadThemeSetting();
        if (savedTheme != null && availableThemes.contains(savedTheme)) {
            currentTheme = savedTheme;
        } else {
            currentTheme = DEFAULT_THEME;
        }
        
        // 预加载当前主题
        imageFlyweight.preloadTheme(currentTheme);
        factoryProvider.setCurrentTheme(currentTheme);
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
        
        File themesDir = new File(THEMES_FOLDER);
        if (themesDir.exists() && themesDir.isDirectory()) {
            File[] themeFiles = themesDir.listFiles(File::isDirectory);
            if (themeFiles != null) {
                for (File themeDir : themeFiles) {
                    if (themeDir.getName().equals(DEFAULT_THEME) && isValidTheme(themeDir)) {
                        availableThemes.add(themeDir.getName());
                        break;
                    }
                }
                for (File themeDir : themeFiles) {
                    if (!themeDir.getName().equals(DEFAULT_THEME) && isValidTheme(themeDir)) {
                        availableThemes.add(themeDir.getName());
                    }
                }
            }
        }

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
            // 预加载新主题
            imageFlyweight.preloadTheme(themeName);
            this.currentTheme = themeName;
            // 同步更新抽象工厂
            factoryProvider.setCurrentTheme(themeName);
            // 保存主题设置
            saveThemeSetting(themeName);
        }
    }
    
    /**
     * 保存主题设置到文件
     */
    private void saveThemeSetting(String themeName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SETTINGS_FILE))) {
            writer.println(themeName);
        } catch (IOException e) {
            System.err.println("无法保存主题设置: " + e.getMessage());
        }
    }
    
    /**
     * 从文件加载主题设置
     */
    private String loadThemeSetting() {
        File file = new File(SETTINGS_FILE);
        if (!file.exists()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String themeName = reader.readLine();
            if (themeName != null && !themeName.trim().isEmpty()) {
                return themeName.trim();
            }
        } catch (IOException e) {
            System.err.println("无法加载主题设置: " + e.getMessage());
        }
        return null;
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
    
    // ==================== 享元模式相关方法 ====================
    
    /**
     * 获取享元工厂（用于共享图片资源）
     */
    public ImageFlyweightFactory getImageFlyweight() {
        return imageFlyweight;
    }
    
    /**
     * 获取共享的 ImageIcon（享元模式）
     */
    public ImageIcon getSharedImage(String imageName) {
        return imageFlyweight.getImage(currentTheme, imageName);
    }
    
    /**
     * 获取缓存统计信息
     */
    public String getCacheStats() {
        return imageFlyweight.getCacheStats();
    }
    
    /**
     * 清除图片缓存
     */
    public void clearCache() {
        imageFlyweight.clearAllCache();
    }
    
    // ==================== 抽象工厂模式相关方法 ====================
    
    /**
     * 获取主题工厂提供者
     */
    public ThemeFactoryProvider getFactoryProvider() {
        return factoryProvider;
    }
    
    /**
     * 获取当前主题的工厂
     */
    public ThemeFactory getCurrentThemeFactory() {
        return factoryProvider.getCurrentFactory();
    }
    
    // ==================== 建造者模式相关方法 ====================
    
    /**
     * 获取主题指挥者
     */
    public ThemeDirector getThemeDirector() {
        return themeDirector;
    }
    
    /**
     * 创建自定义主题配置
     */
    public ThemeConfig createCustomThemeConfig() {
        return new ThemeBuilder().build();
    }
    
    /**
     * 使用建造者创建暗黑主题配置
     */
    public ThemeConfig buildDarkTheme() {
        return themeDirector.constructDarkTheme();
    }
    
    /**
     * 使用建造者创建海洋主题配置
     */
    public ThemeConfig buildOceanTheme() {
        return themeDirector.constructOceanTheme();
    }
    
    /**
     * 使用建造者创建森林主题配置
     */
    public ThemeConfig buildForestTheme() {
        return themeDirector.constructForestTheme();
    }
    
    /**
     * 使用建造者创建像素主题配置
     */
    public ThemeConfig buildPixelTheme() {
        return themeDirector.constructPixelTheme();
    }
}
