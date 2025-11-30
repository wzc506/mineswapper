package Library.Theme;

import java.awt.Color;

public class PixelThemeFactory extends AbstractThemeFactory {
    
    public PixelThemeFactory() {
        super("pixel");
    }
    
    @Override
    public String getDisplayName() {
        return "像素";
    }
    
    @Override
    public Color getPrimaryColor() {
        return new Color(140, 140, 140);
    }
    
    @Override
    public Color getBackgroundColor() {
        return new Color(192, 192, 192);
    }
    
    @Override
    public String getDescription() {
        return "复古 8-bit 像素风格";
    }
}
