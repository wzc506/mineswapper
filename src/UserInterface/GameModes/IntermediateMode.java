package UserInterface.GameModes;

import Library.GameMode;

public class IntermediateMode implements GameMode {
    @Override
    public String getName() {
        return "中级";
    }
    
    @Override
    public int getWidth() {
        return 16;
    }
    
    @Override
    public int getHeight() {
        return 16;
    }
    
    @Override
    public int getBombs() {
        return 40;
    }
}
