package UserInterface.GameModes;

import Library.GameMode;

public class CustomMode implements GameMode {
    private final int width;
    private final int height;
    private final int bombs;
    
    public CustomMode(int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;
    }
    
    @Override
    public String getName() {
        return "自定义_" + width + "x" + height + "_" + bombs;
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public int getBombs() {
        return bombs;
    }
}
