package UserInterface.GameModes;

import Library.GameMode;

public class AdvancedMode implements GameMode {
    @Override
    public String getName() {
        return "高级";
    }
    
    @Override
    public int getWidth() {
        return 30;
    }
    
    @Override
    public int getHeight() {
        return 25;
    }
    
    @Override
    public int getBombs() {
        return 100;
    }
}
