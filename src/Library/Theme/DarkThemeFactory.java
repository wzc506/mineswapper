package Library.Theme;

import java.awt.Color;

public class DarkThemeFactory extends AbstractThemeFactory {
    
    public DarkThemeFactory() {
        super("dark");
    }
    
    @Override
    public String getDisplayName() {
        return "暗黑";
    }
    
    @Override
    public Color getPrimaryColor() {
        return new Color(50, 50, 60);
    }
    
    @Override
    public Color getBackgroundColor() {
        return new Color(30, 30, 40);
    }
    
    @Override
    public String getDescription() {
        return "深色护眼主题，适合夜间游戏";
    }
}
