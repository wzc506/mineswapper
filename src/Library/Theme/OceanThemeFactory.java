package Library.Theme;

import java.awt.Color;

public class OceanThemeFactory extends AbstractThemeFactory {
    
    public OceanThemeFactory() {
        super("ocean");
    }
    
    @Override
    public String getDisplayName() {
        return "海洋";
    }
    
    @Override
    public Color getPrimaryColor() {
        return new Color(70, 130, 180);
    }
    
    @Override
    public Color getBackgroundColor() {
        return new Color(176, 224, 230);
    }
    
    @Override
    public String getDescription() {
        return "清凉海洋蓝色主题";
    }
}
