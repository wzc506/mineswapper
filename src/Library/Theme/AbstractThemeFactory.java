package Library.Theme;

import javax.swing.ImageIcon;
import java.awt.Color;

/**
 * 抽象工厂模式 - 抽象主题工厂基类
 * 提供通用实现，使用享元模式获取图片
 */
public abstract class AbstractThemeFactory implements ThemeFactory {
    
    protected final String themeName;
    protected final ImageFlyweightFactory imageFactory;
    
    public AbstractThemeFactory(String themeName) {
        this.themeName = themeName;
        this.imageFactory = ImageFlyweightFactory.getInstance();
    }
    
    @Override
    public String getThemeName() {
        return themeName;
    }
    
    /**
     * 获取图片的通用方法，使用享元工厂
     */
    protected ImageIcon getImage(String imageName) {
        return imageFactory.getImage(themeName, imageName);
    }
    
    @Override
    public ImageIcon getBlockImage() {
        return getImage("block.png");
    }
    
    @Override
    public ImageIcon getNumberImage(int number) {
        if (number < 0 || number > 8) {
            throw new IllegalArgumentException("数字必须在 0-8 之间");
        }
        return getImage(number + ".png");
    }
    
    @Override
    public ImageIcon getBombImage() {
        return getImage("bomb.png");
    }
    
    @Override
    public ImageIcon getBombRevealImage() {
        return getImage("bombReveal.png");
    }
    
    @Override
    public ImageIcon getFlagImage() {
        return getImage("redFlag.png");
    }
    
    @Override
    public ImageIcon getFlagWrongImage() {
        return getImage("flagWrong.png");
    }
    
    @Override
    public ImageIcon getQuestionMarkImage() {
        return getImage("questionMark.png");
    }
    
    @Override
    public ImageIcon getFailFaceImage() {
        // 尝试获取主题特定的图片，如果不存在则使用默认
        try {
            return getImage("cai.jpg");
        } catch (Exception e) {
            return imageFactory.getImage("default", "cai.jpg");
        }
    }
    
    @Override
    public ImageIcon getPassFaceImage() {
        try {
            return getImage("passFace.jpg");
        } catch (Exception e) {
            return imageFactory.getImage("default", "passFace.jpg");
        }
    }
    
    /**
     * 预加载主题资源
     */
    public void preload() {
        imageFactory.preloadTheme(themeName);
    }
}
