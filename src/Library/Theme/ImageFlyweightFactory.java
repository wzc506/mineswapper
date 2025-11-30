package Library.Theme;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;

/**
 * 享元模式 - 图片享元工厂
 * 缓存并共享图片资源，避免重复创建相同的 ImageIcon 对象
 * 特别适用于扫雷游戏中大量格子共享同一套图片的场景
 */
public class ImageFlyweightFactory {
    
    private static ImageFlyweightFactory instance;
    
    // 图片缓存池：key = "主题名/图片名", value = ImageIcon对象
    private final Map<String, ImageIcon> imageCache;
    
    // 缓存统计
    private int cacheHits = 0;
    private int cacheMisses = 0;
    
    private ImageFlyweightFactory() {
        imageCache = new HashMap<>();
    }
    
    public static ImageFlyweightFactory getInstance() {
        if (instance == null) {
            instance = new ImageFlyweightFactory();
        }
        return instance;
    }
    
    /**
     * 获取图片（享元对象）
     * 如果缓存中存在则直接返回，否则创建新的并缓存
     * 
     * @param imagePath 图片完整路径
     * @return 共享的 ImageIcon 对象
     */
    public ImageIcon getImage(String imagePath) {
        // 检查缓存
        if (imageCache.containsKey(imagePath)) {
            cacheHits++;
            return imageCache.get(imagePath);
        }
        
        // 缓存未命中，创建新的 ImageIcon
        cacheMisses++;
        ImageIcon icon = new ImageIcon(imagePath);
        imageCache.put(imagePath, icon);
        return icon;
    }
    
    /**
     * 获取指定主题的图片
     * 
     * @param themeName 主题名称
     * @param imageName 图片名称
     * @return 共享的 ImageIcon 对象
     */
    public ImageIcon getImage(String themeName, String imageName) {
        String path = "themes/" + themeName + "/" + imageName;
        return getImage(path);
    }
    
    /**
     * 清除指定主题的缓存
     * 当切换主题时可以选择清除旧主题缓存以释放内存
     * 
     * @param themeName 要清除的主题名称
     */
    public void clearThemeCache(String themeName) {
        String prefix = "themes/" + themeName + "/";
        imageCache.entrySet().removeIf(entry -> entry.getKey().startsWith(prefix));
    }
    
    /**
     * 清除所有缓存
     */
    public void clearAllCache() {
        imageCache.clear();
        cacheHits = 0;
        cacheMisses = 0;
    }
    
    /**
     * 预加载主题的所有图片到缓存
     * 可以在游戏开始前调用，避免游戏中的加载延迟
     * 
     * @param themeName 主题名称
     */
    public void preloadTheme(String themeName) {
        String[] images = {
            "block.png", "bomb.png", "bombReveal.png",
            "redFlag.png", "flagWrong.png", "questionMark.png",
            "0.png", "1.png", "2.png", "3.png", "4.png",
            "5.png", "6.png", "7.png", "8.png"
        };
        
        for (String imageName : images) {
            getImage(themeName, imageName);
        }
    }
    
    /**
     * 获取缓存大小
     */
    public int getCacheSize() {
        return imageCache.size();
    }
    
    /**
     * 获取缓存命中次数
     */
    public int getCacheHits() {
        return cacheHits;
    }
    
    /**
     * 获取缓存未命中次数
     */
    public int getCacheMisses() {
        return cacheMisses;
    }
    
    /**
     * 获取缓存命中率
     */
    public double getCacheHitRate() {
        int total = cacheHits + cacheMisses;
        return total == 0 ? 0 : (double) cacheHits / total * 100;
    }
    
    /**
     * 获取缓存统计信息
     */
    public String getCacheStats() {
        return String.format("缓存统计 - 大小: %d, 命中: %d, 未命中: %d, 命中率: %.2f%%",
                getCacheSize(), cacheHits, cacheMisses, getCacheHitRate());
    }
}
