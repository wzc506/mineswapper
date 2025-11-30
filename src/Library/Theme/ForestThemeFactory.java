package Library.Theme;

import java.awt.Color;

public class ForestThemeFactory extends AbstractThemeFactory {
    
    public ForestThemeFactory() {
        super("forest");
    }
    
    @Override
    public String getDisplayName() {
        return "森林";
    }
    
    @Override
    public Color getPrimaryColor() {
        return new Color(85, 107, 47);
    }
    
    @Override
    public Color getBackgroundColor() {
        return new Color(144, 238, 144);
    }
    
    @Override
    public String getDescription() {
        return "清新自然森林绿色主题";
    }
}
