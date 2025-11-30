package Library.Theme;

import javax.swing.ImageIcon;
import java.awt.Color;

/**
 * 抽象工厂模式 - 主题抽象工厂接口
 * 定义创建一系列相关主题元素的接口
 * 确保同一主题的所有元素风格统一
 */
public interface ThemeFactory {

    String getThemeName();

    String getDisplayName();

    ImageIcon getBlockImage();

    ImageIcon getNumberImage(int number);

    ImageIcon getBombImage();

    ImageIcon getBombRevealImage();

    ImageIcon getFlagImage();

    ImageIcon getFlagWrongImage();
 
    ImageIcon getQuestionMarkImage();

    ImageIcon getFailFaceImage();

    ImageIcon getPassFaceImage();

    Color getPrimaryColor();

    Color getBackgroundColor();

    String getDescription();
}
