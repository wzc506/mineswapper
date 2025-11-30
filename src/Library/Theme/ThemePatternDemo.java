package Library.Theme;

import javax.swing.ImageIcon;
import java.awt.Color;

/**
 * 设计模式使用示例
 * 展示享元模式、抽象工厂模式和建造者模式的用法
 */
public class ThemePatternDemo {
    
    public static void main(String[] args) {
        System.out.println("========== 设计模式演示 ==========\n");
        
        demonstrateFlyweight();
        demonstrateAbstractFactory();
        demonstrateBuilder();
    }
    
    /**
     * 演示享元模式 - 共享图片资源
     */
    public static void demonstrateFlyweight() {
        System.out.println("【享元模式 (Flyweight Pattern)】");
        System.out.println("作用：共享图片资源，减少内存占用\n");
        
        ImageFlyweightFactory factory = ImageFlyweightFactory.getInstance();
        
        System.out.println("模拟 750 个格子请求 block.png...");
        for (int i = 0; i < 750; i++) {
            ImageIcon icon = factory.getImage("default", "block.png");
        }
        
        factory.getImage("default", "bomb.png");
        factory.getImage("default", "1.png");
        factory.getImage("default", "2.png");
        
        System.out.println(factory.getCacheStats());
        System.out.println("说明：750 次请求只创建了 1 个 block.png 对象！\n");
    }
    
    /**
     * 演示抽象工厂模式 - 创建主题产品族
     */
    public static void demonstrateAbstractFactory() {
        System.out.println("【抽象工厂模式 (Abstract Factory Pattern)】");
        System.out.println("作用：确保同一主题的所有元素风格统一\n");
        
        ThemeFactoryProvider provider = ThemeFactoryProvider.getInstance();

        String[] themes = {"default", "dark", "ocean", "forest", "pixel"};
        
        for (String themeName : themes) {
            ThemeFactory factory = provider.getFactory(themeName);
            System.out.printf("主题: %-8s | 显示名: %-4s | 主色调: %s | 描述: %s%n",
                    factory.getThemeName(),
                    factory.getDisplayName(),
                    colorToHex(factory.getPrimaryColor()),
                    factory.getDescription());
        }
        
        // 切换主题
        System.out.println("\n切换到暗黑主题...");
        provider.setCurrentTheme("dark");
        ThemeFactory current = provider.getCurrentFactory();
        System.out.println("当前主题: " + current.getDisplayName());
        System.out.println();
    }
    
    /**
     * 演示建造者模式 - 分步构建自定义主题
     */
    public static void demonstrateBuilder() {
        System.out.println("【建造者模式 (Builder Pattern)】");
        System.out.println("作用：分步骤构建复杂的自定义主题配置\n");

        System.out.println("1. 使用 ThemeDirector 构建预设主题：");
        ThemeDirector director = new ThemeDirector();
        
        ThemeConfig darkConfig = director.constructDarkTheme();
        System.out.println("   暗黑主题: " + darkConfig);
        
        ThemeConfig pixelConfig = director.constructPixelTheme();
        System.out.println("   像素主题: " + pixelConfig);

        System.out.println("\n2. 使用 ThemeBuilder 链式调用创建自定义主题：");
        ThemeConfig customConfig = new ThemeBuilder()
                .themeName("my_theme")
                .displayName("我的主题")
                .description("这是我自定义的主题")
                .primaryColor(100, 150, 200)
                .backgroundColor(240, 248, 255)
                .blockColor(new Color(100, 150, 200))
                .bombColor(Color.RED)
                .flagColor(Color.ORANGE)
                .numberColor(1, Color.BLUE)
                .numberColor(2, Color.GREEN)
                .numberColor(3, Color.RED)
                .build();
        
        System.out.println("   自定义主题: " + customConfig);
        System.out.println("   主色调: " + colorToHex(customConfig.getPrimaryColor()));
        System.out.println("   背景色: " + colorToHex(customConfig.getBackgroundColor()));
        
        System.out.println("\n3. 基于预设创建变体主题：");
        ThemeConfig variantConfig = new ThemeBuilder()
                .themeName("dark_red")
                .displayName("暗红")
                .applyDarkPreset()           // 先应用暗黑预设
                .primaryColor(139, 0, 0)     // 然后修改主色调为暗红
                .bombColor(Color.YELLOW)     // 修改炸弹颜色
                .build();
        
        System.out.println("   暗红变体: " + variantConfig);
        System.out.println();
    }
    
    /**
     * 将 Color 转换为十六进制字符串
     */
    private static String colorToHex(Color color) {
        return String.format("#%02X%02X%02X", 
                color.getRed(), color.getGreen(), color.getBlue());
    }
}
