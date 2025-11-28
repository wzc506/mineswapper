package UserInterface.GameModes;

import Library.GameMode;

public class BeginnerMode implements GameMode {
    @Override
    public String getName() {
        return "新手";
    }
    
    @Override
    public int getWidth() {
        return 10;
    }
    
    @Override
    public int getHeight() {
        return 10;
    }
    
    @Override
    public int getBombs() {
        return 10;
    }
}
