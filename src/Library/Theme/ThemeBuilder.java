package Library.Theme;

import java.awt.Color;

/**
 * 建造者模式 - 主题建造器
 * 使用流式 API 分步骤构建自定义主题配置
 */
public class ThemeBuilder {
    
    private ThemeConfig config;
    
    public ThemeBuilder() {
        this.config = new ThemeConfig();
        // 设置默认值
        setDefaultValues();
    }
    
    /**
     * 设置默认值
     */
    private void setDefaultValues() {
        config.setThemeName("custom");
        config.setDisplayName("自定义");
        config.setDescription("用户自定义主题");
        config.setPrimaryColor(new Color(192, 192, 192));
        config.setBackgroundColor(new Color(240, 240, 240));
        config.setBlockColor(new Color(192, 192, 192));
        config.setBorderLightColor(new Color(255, 255, 255));
        config.setBorderDarkColor(new Color(128, 128, 128));
        config.setEmptyColor(new Color(192, 192, 192));
        config.setBombColor(Color.BLACK);
        config.setFlagColor(Color.RED);
        config.setQuestionMarkColor(Color.BLACK);
        
        // 默认数字颜色（经典扫雷配色）
        config.setNumberColor(0, new Color(192, 192, 192));
        config.setNumberColor(1, new Color(0, 0, 255));      // 蓝色
        config.setNumberColor(2, new Color(0, 128, 0));      // 绿色
        config.setNumberColor(3, new Color(255, 0, 0));      // 红色
        config.setNumberColor(4, new Color(0, 0, 128));      // 深蓝
        config.setNumberColor(5, new Color(128, 0, 0));      // 深红
        config.setNumberColor(6, new Color(0, 128, 128));    // 青色
        config.setNumberColor(7, new Color(0, 0, 0));        // 黑色
        config.setNumberColor(8, new Color(128, 128, 128));  // 灰色
    }
    
    /**
     * 设置主题名称
     */
    public ThemeBuilder themeName(String name) {
        config.setThemeName(name);
        return this;
    }
    
    /**
     * 设置显示名称
     */
    public ThemeBuilder displayName(String name) {
        config.setDisplayName(name);
        return this;
    }
    
    /**
     * 设置描述
     */
    public ThemeBuilder description(String desc) {
        config.setDescription(desc);
        return this;
    }
    
    /**
     * 设置主色调
     */
    public ThemeBuilder primaryColor(Color color) {
        config.setPrimaryColor(color);
        return this;
    }
    
    /**
     * 设置主色调（RGB）
     */
    public ThemeBuilder primaryColor(int r, int g, int b) {
        return primaryColor(new Color(r, g, b));
    }
    
    /**
     * 设置背景色
     */
    public ThemeBuilder backgroundColor(Color color) {
        config.setBackgroundColor(color);
        return this;
    }
    
    /**
     * 设置背景色（RGB）
     */
    public ThemeBuilder backgroundColor(int r, int g, int b) {
        return backgroundColor(new Color(r, g, b));
    }
    
    /**
     * 设置方块颜色
     */
    public ThemeBuilder blockColor(Color color) {
        config.setBlockColor(color);
        return this;
    }
    
    /**
     * 设置方块颜色（RGB）
     */
    public ThemeBuilder blockColor(int r, int g, int b) {
        return blockColor(new Color(r, g, b));
    }
    
    /**
     * 设置边框高光颜色
     */
    public ThemeBuilder borderLightColor(Color color) {
        config.setBorderLightColor(color);
        return this;
    }
    
    /**
     * 设置边框阴影颜色
     */
    public ThemeBuilder borderDarkColor(Color color) {
        config.setBorderDarkColor(color);
        return this;
    }
    
    /**
     * 设置空白格颜色
     */
    public ThemeBuilder emptyColor(Color color) {
        config.setEmptyColor(color);
        return this;
    }
    
    /**
     * 设置炸弹颜色
     */
    public ThemeBuilder bombColor(Color color) {
        config.setBombColor(color);
        return this;
    }
    
    /**
     * 设置旗帜颜色
     */
    public ThemeBuilder flagColor(Color color) {
        config.setFlagColor(color);
        return this;
    }
    
    /**
     * 设置问号颜色
     */
    public ThemeBuilder questionMarkColor(Color color) {
        config.setQuestionMarkColor(color);
        return this;
    }
    
    /**
     * 设置单个数字颜色
     */
    public ThemeBuilder numberColor(int index, Color color) {
        if (index >= 0 && index <= 8) {
            config.setNumberColor(index, color);
        }
        return this;
    }
    
    /**
     * 设置所有数字颜色
     */
    public ThemeBuilder numberColors(Color... colors) {
        for (int i = 0; i < Math.min(colors.length, 9); i++) {
            config.setNumberColor(i, colors[i]);
        }
        return this;
    }
    
    /**
     * 应用暗色预设
     */
    public ThemeBuilder applyDarkPreset() {
        config.setBlockColor(new Color(50, 50, 60));
        config.setBorderLightColor(new Color(80, 80, 100));
        config.setBorderDarkColor(new Color(30, 30, 40));
        config.setEmptyColor(new Color(30, 30, 40));
        config.setPrimaryColor(new Color(50, 50, 60));
        config.setBackgroundColor(new Color(30, 30, 40));
        return this;
    }
    
    /**
     * 应用海洋预设
     */
    public ThemeBuilder applyOceanPreset() {
        config.setBlockColor(new Color(70, 130, 180));
        config.setBorderLightColor(new Color(100, 149, 237));
        config.setBorderDarkColor(new Color(65, 105, 225));
        config.setEmptyColor(new Color(176, 224, 230));
        config.setPrimaryColor(new Color(70, 130, 180));
        config.setBackgroundColor(new Color(176, 224, 230));
        return this;
    }
    
    /**
     * 应用森林预设
     */
    public ThemeBuilder applyForestPreset() {
        config.setBlockColor(new Color(85, 107, 47));
        config.setBorderLightColor(new Color(107, 142, 35));
        config.setBorderDarkColor(new Color(34, 139, 34));
        config.setEmptyColor(new Color(144, 238, 144));
        config.setPrimaryColor(new Color(85, 107, 47));
        config.setBackgroundColor(new Color(144, 238, 144));
        return this;
    }
    
    /**
     * 构建主题配置
     */
    public ThemeConfig build() {
        // 验证必要参数
        if (config.getThemeName() == null || config.getThemeName().isEmpty()) {
            throw new IllegalStateException("主题名称不能为空");
        }
        return config;
    }
    
    /**
     * 重置建造器
     */
    public ThemeBuilder reset() {
        this.config = new ThemeConfig();
        setDefaultValues();
        return this;
    }
}
