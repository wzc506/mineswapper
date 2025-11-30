package Library.Theme;

import javax.swing.ImageIcon;
import java.awt.Color;

public class DefaultThemeFactory extends AbstractThemeFactory {
    
    public DefaultThemeFactory() {
        super("default");
    }
    
    @Override
    public String getDisplayName() {
        return "经典";
    }
    
    @Override
    public Color getPrimaryColor() {
        return new Color(192, 192, 192);
    }
    
    @Override
    public Color getBackgroundColor() {
        return new Color(240, 240, 240);
    }
    
    @Override
    public String getDescription() {
        return "经典 Windows 扫雷风格";
    }
}
