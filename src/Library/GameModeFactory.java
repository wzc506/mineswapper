package Library;

import UserInterface.GameModes.AdvancedMode;
import UserInterface.GameModes.BeginnerMode;
import UserInterface.GameModes.CustomMode;
import UserInterface.GameModes.IntermediateMode;


public class GameModeFactory {
    
    public static final String BEGINNER = "beginner";
    public static final String INTERMEDIATE = "intermediate";
    public static final String ADVANCED = "advanced";
    
    private static final GameMode BEGINNER_MODE = new BeginnerMode();
    private static final GameMode INTERMEDIATE_MODE = new IntermediateMode();
    private static final GameMode ADVANCED_MODE = new AdvancedMode();
    
    public static GameMode createMode(String type) {
        switch (type) {
            case BEGINNER:
                return BEGINNER_MODE;
            case INTERMEDIATE:
                return INTERMEDIATE_MODE;
            case ADVANCED:
                return ADVANCED_MODE;
            default:
                throw new IllegalArgumentException("未知的模式类型: " + type);
        }
    }
    
    public static GameMode createCustomMode(int width, int height, int bombs) {
        return new CustomMode(width, height, bombs);
    }
    
    public static GameMode getMode(int width, int height, int bombs) {
        if (width == 10 && height == 10 && bombs == 10) {
            return BEGINNER_MODE;
        } else if (width == 16 && height == 16 && bombs == 40) {
            return INTERMEDIATE_MODE;
        } else if (width == 30 && height == 25 && bombs == 100) {
            return ADVANCED_MODE;
        } else {
            return createCustomMode(width, height, bombs);
        }
    }
}
