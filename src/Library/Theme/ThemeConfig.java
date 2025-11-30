package Library.Theme;

import java.awt.Color;

/**
 * 建造者模式 - 主题配置类
 * 存储自定义主题的所有配置信息
 */
public class ThemeConfig {
    
    private String themeName;
    private String displayName;
    private String description;
    
    // 颜色配置
    private Color primaryColor;
    private Color backgroundColor;
    private Color blockColor;
    private Color borderLightColor;
    private Color borderDarkColor;
    private Color emptyColor;
    
    // 数字颜色 (0-8)
    private Color[] numberColors;
    
    // 特殊元素颜色
    private Color bombColor;
    private Color flagColor;
    private Color questionMarkColor;
    
    // 构造函数 - 包访问权限，只能通过 Builder 创建
    ThemeConfig() {
        numberColors = new Color[9];
    }
    
    // Getters
    public String getThemeName() { return themeName; }
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public Color getPrimaryColor() { return primaryColor; }
    public Color getBackgroundColor() { return backgroundColor; }
    public Color getBlockColor() { return blockColor; }
    public Color getBorderLightColor() { return borderLightColor; }
    public Color getBorderDarkColor() { return borderDarkColor; }
    public Color getEmptyColor() { return emptyColor; }
    public Color[] getNumberColors() { return numberColors; }
    public Color getNumberColor(int index) { return numberColors[index]; }
    public Color getBombColor() { return bombColor; }
    public Color getFlagColor() { return flagColor; }
    public Color getQuestionMarkColor() { return questionMarkColor; }
    
    // Setters - 包访问权限
    void setThemeName(String themeName) { this.themeName = themeName; }
    void setDisplayName(String displayName) { this.displayName = displayName; }
    void setDescription(String description) { this.description = description; }
    void setPrimaryColor(Color primaryColor) { this.primaryColor = primaryColor; }
    void setBackgroundColor(Color backgroundColor) { this.backgroundColor = backgroundColor; }
    void setBlockColor(Color blockColor) { this.blockColor = blockColor; }
    void setBorderLightColor(Color borderLightColor) { this.borderLightColor = borderLightColor; }
    void setBorderDarkColor(Color borderDarkColor) { this.borderDarkColor = borderDarkColor; }
    void setEmptyColor(Color emptyColor) { this.emptyColor = emptyColor; }
    void setNumberColors(Color[] numberColors) { this.numberColors = numberColors; }
    void setNumberColor(int index, Color color) { this.numberColors[index] = color; }
    void setBombColor(Color bombColor) { this.bombColor = bombColor; }
    void setFlagColor(Color flagColor) { this.flagColor = flagColor; }
    void setQuestionMarkColor(Color questionMarkColor) { this.questionMarkColor = questionMarkColor; }
    
    @Override
    public String toString() {
        return "ThemeConfig{" +
                "themeName='" + themeName + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
