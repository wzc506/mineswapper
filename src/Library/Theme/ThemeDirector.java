package Library.Theme;

import java.awt.Color;

/**
 * 建造者模式 - 主题指挥者
 * 封装常用主题的构建过程
 */
public class ThemeDirector {
    
    private ThemeBuilder builder;
    
    public ThemeDirector() {
        this.builder = new ThemeBuilder();
    }
    
    public ThemeDirector(ThemeBuilder builder) {
        this.builder = builder;
    }
    
    /**
     * 构建暗黑主题配置
     */
    public ThemeConfig constructDarkTheme() {
        return builder.reset()
                .themeName("dark_custom")
                .displayName("暗黑")
                .description("深色护眼主题")
                .applyDarkPreset()
                .bombColor(new Color(255, 50, 50))
                .flagColor(new Color(255, 215, 0))
                .questionMarkColor(new Color(200, 100, 255))
                .numberColor(1, new Color(100, 149, 237))
                .numberColor(2, new Color(34, 139, 34))
                .numberColor(3, new Color(255, 69, 0))
                .build();
    }
    
    /**
     * 构建海洋主题配置
     */
    public ThemeConfig constructOceanTheme() {
        return builder.reset()
                .themeName("ocean_custom")
                .displayName("海洋")
                .description("清凉海洋主题")
                .applyOceanPreset()
                .bombColor(new Color(255, 69, 0))
                .flagColor(new Color(255, 255, 0))
                .questionMarkColor(new Color(138, 43, 226))
                .numberColor(1, new Color(0, 0, 139))
                .numberColor(2, new Color(0, 128, 128))
                .numberColor(3, new Color(255, 99, 71))
                .build();
    }
    
    /**
     * 构建森林主题配置
     */
    public ThemeConfig constructForestTheme() {
        return builder.reset()
                .themeName("forest_custom")
                .displayName("森林")
                .description("清新森林主题")
                .applyForestPreset()
                .bombColor(new Color(139, 0, 0))
                .flagColor(new Color(255, 165, 0))
                .questionMarkColor(new Color(255, 20, 147))
                .numberColor(1, new Color(0, 100, 0))
                .numberColor(2, new Color(34, 139, 34))
                .numberColor(3, new Color(220, 20, 60))
                .build();
    }
    
    /**
     * 构建像素主题配置
     */
    public ThemeConfig constructPixelTheme() {
        return builder.reset()
                .themeName("pixel_custom")
                .displayName("像素")
                .description("复古8位像素风格")
                .blockColor(140, 140, 140)
                .borderLightColor(new Color(200, 200, 200))
                .borderDarkColor(new Color(80, 80, 80))
                .emptyColor(new Color(192, 192, 192))
                .primaryColor(140, 140, 140)
                .backgroundColor(192, 192, 192)
                .bombColor(Color.BLACK)
                .flagColor(Color.RED)
                .questionMarkColor(Color.BLACK)
                // 经典扫雷数字配色
                .numberColor(1, new Color(0, 0, 255))
                .numberColor(2, new Color(0, 128, 0))
                .numberColor(3, new Color(255, 0, 0))
                .numberColor(4, new Color(0, 0, 128))
                .numberColor(5, new Color(128, 0, 0))
                .numberColor(6, new Color(0, 128, 128))
                .numberColor(7, Color.BLACK)
                .numberColor(8, new Color(128, 128, 128))
                .build();
    }
    
    /**
     * 构建高对比度主题（无障碍）
     */
    public ThemeConfig constructHighContrastTheme() {
        return builder.reset()
                .themeName("high_contrast")
                .displayName("高对比度")
                .description("高对比度无障碍主题")
                .blockColor(Color.WHITE)
                .borderLightColor(Color.WHITE)
                .borderDarkColor(Color.BLACK)
                .emptyColor(Color.WHITE)
                .backgroundColor(Color.WHITE)
                .primaryColor(Color.BLACK)
                .bombColor(Color.BLACK)
                .flagColor(Color.RED)
                .questionMarkColor(Color.BLUE)
                .numberColor(1, Color.BLUE)
                .numberColor(2, new Color(0, 100, 0))
                .numberColor(3, Color.RED)
                .numberColor(4, new Color(0, 0, 139))
                .numberColor(5, new Color(139, 0, 0))
                .numberColor(6, new Color(0, 139, 139))
                .numberColor(7, Color.BLACK)
                .numberColor(8, Color.GRAY)
                .build();
    }
    
    /**
     * 根据用户偏好构建自定义主题
     */
    public ThemeConfig constructCustomTheme(String name, String displayName, 
            Color primary, Color background, Color bomb, Color flag) {
        return builder.reset()
                .themeName(name)
                .displayName(displayName)
                .description("用户自定义主题")
                .primaryColor(primary)
                .backgroundColor(background)
                .blockColor(primary)
                .emptyColor(background)
                .bombColor(bomb)
                .flagColor(flag)
                .build();
    }
}
