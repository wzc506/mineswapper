package Library.Theme;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象工厂模式 - 主题工厂提供者
 * 管理所有主题工厂实例，提供统一的访问入口
 */
public class ThemeFactoryProvider {
    
    private static ThemeFactoryProvider instance;
    
    private final Map<String, ThemeFactory> factories;
    private ThemeFactory currentFactory;
    
    private ThemeFactoryProvider() {
        factories = new HashMap<>();
        registerDefaultFactories();
        currentFactory = factories.get("default");
    }
    
    public static ThemeFactoryProvider getInstance() {
        if (instance == null) {
            instance = new ThemeFactoryProvider();
        }
        return instance;
    }
    
    /**
     * 注册默认的主题工厂
     */
    private void registerDefaultFactories() {
        registerFactory(new DefaultThemeFactory());
        registerFactory(new DarkThemeFactory());
        registerFactory(new OceanThemeFactory());
        registerFactory(new ForestThemeFactory());
        registerFactory(new PixelThemeFactory());
    }
    
    /**
     * 注册主题工厂
     */
    public void registerFactory(ThemeFactory factory) {
        factories.put(factory.getThemeName(), factory);
    }
    
    /**
     * 获取指定主题的工厂
     */
    public ThemeFactory getFactory(String themeName) {
        ThemeFactory factory = factories.get(themeName);
        if (factory == null) {
            return factories.get("default");
        }
        return factory;
    }
    
    /**
     * 获取当前主题工厂
     */
    public ThemeFactory getCurrentFactory() {
        return currentFactory;
    }
    
    /**
     * 设置当前主题
     */
    public void setCurrentTheme(String themeName) {
        ThemeFactory factory = factories.get(themeName);
        if (factory != null) {
            this.currentFactory = factory;
        }
    }
    
    /**
     * 获取所有已注册的主题名称
     */
    public String[] getAvailableThemes() {
        return factories.keySet().toArray(new String[0]);
    }
    
    /**
     * 检查主题是否存在
     */
    public boolean hasTheme(String themeName) {
        return factories.containsKey(themeName);
    }
}
