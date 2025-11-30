package Library;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 主题图片生成器
 * 用于生成不同风格的游戏主题图片
 */
public class ThemeGenerator {

    private static final int SIZE = 30;

    public static void main(String[] args) {
        generateDarkTheme();
        generateOceanTheme();
        generateForestTheme();
        generatePixelTheme();
        System.out.println("主题图片生成完成！");
    }

    /**
     * 生成暗黑主题
     */
    public static void generateDarkTheme() {
        String themePath = "themes/dark/";
        Color blockColor = new Color(50, 50, 60);
        Color borderColor = new Color(80, 80, 100);
        Color emptyColor = new Color(30, 30, 40);
        Color[] numberColors = {
            new Color(100, 100, 100),  // 0
            new Color(100, 149, 237),  // 1 蓝色
            new Color(34, 139, 34),    // 2 绿色
            new Color(255, 69, 0),     // 3 红色
            new Color(0, 0, 139),      // 4 深蓝
            new Color(139, 0, 0),      // 5 深红
            new Color(0, 139, 139),    // 6 青色
            new Color(128, 0, 128),    // 7 紫色
            new Color(169, 169, 169)   // 8 灰色
        };
        
        generateThemeImages(themePath, blockColor, borderColor, emptyColor, numberColors,
                new Color(255, 50, 50), new Color(255, 215, 0), new Color(200, 100, 255));
    }

    /**
     * 生成海洋主题
     */
    public static void generateOceanTheme() {
        String themePath = "themes/ocean/";
        Color blockColor = new Color(70, 130, 180);
        Color borderColor = new Color(100, 149, 237);
        Color emptyColor = new Color(176, 224, 230);
        Color[] numberColors = {
            new Color(176, 224, 230),  // 0
            new Color(0, 0, 139),      // 1 深蓝
            new Color(0, 128, 128),    // 2 青色
            new Color(255, 99, 71),    // 3 番茄红
            new Color(25, 25, 112),    // 4 午夜蓝
            new Color(178, 34, 34),    // 5 火砖红
            new Color(0, 139, 139),    // 6 深青
            new Color(72, 61, 139),    // 7 深紫
            new Color(105, 105, 105)   // 8 灰色
        };
        
        generateThemeImages(themePath, blockColor, borderColor, emptyColor, numberColors,
                new Color(255, 69, 0), new Color(255, 255, 0), new Color(138, 43, 226));
    }

    /**
     * 生成森林主题
     */
    public static void generateForestTheme() {
        String themePath = "themes/forest/";
        Color blockColor = new Color(85, 107, 47);
        Color borderColor = new Color(107, 142, 35);
        Color emptyColor = new Color(144, 238, 144);
        Color[] numberColors = {
            new Color(144, 238, 144),  // 0
            new Color(0, 100, 0),      // 1 深绿
            new Color(34, 139, 34),    // 2 森林绿
            new Color(220, 20, 60),    // 3 深红
            new Color(0, 0, 139),      // 4 深蓝
            new Color(139, 69, 19),    // 5 鞍褐
            new Color(47, 79, 79),     // 6 深灰
            new Color(128, 0, 0),      // 7 暗红
            new Color(85, 85, 85)      // 8 灰色
        };
        
        generateThemeImages(themePath, blockColor, borderColor, emptyColor, numberColors,
                new Color(139, 0, 0), new Color(255, 165, 0), new Color(255, 20, 147));
    }

    /**
     * 生成像素主题 - 复古8位风格
     */
    public static void generatePixelTheme() {
        String themePath = "themes/pixel/";
        
        File dir = new File(themePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 像素风格颜色
        Color blockColor = new Color(140, 140, 140);
        Color borderLight = new Color(200, 200, 200);
        Color borderDark = new Color(80, 80, 80);
        Color emptyColor = new Color(192, 192, 192);
        
        // 生成像素风格方块
        generatePixelBlockImage(themePath + "block.png", blockColor, borderLight, borderDark);
        
        // 数字颜色 - 经典扫雷配色
        Color[] numberColors = {
            emptyColor,                // 0
            new Color(0, 0, 255),      // 1 蓝色
            new Color(0, 128, 0),      // 2 绿色
            new Color(255, 0, 0),      // 3 红色
            new Color(0, 0, 128),      // 4 深蓝
            new Color(128, 0, 0),      // 5 深红
            new Color(0, 128, 128),    // 6 青色
            new Color(0, 0, 0),        // 7 黑色
            new Color(128, 128, 128)   // 8 灰色
        };
        
        // 生成像素风格数字
        for (int i = 0; i <= 8; i++) {
            generatePixelNumberImage(themePath + i + ".png", i, emptyColor, numberColors[i]);
        }
        
        // 生成像素风格炸弹
        generatePixelBombImage(themePath + "bomb.png", emptyColor, Color.BLACK);
        generatePixelBombRevealImage(themePath + "bombReveal.png", new Color(255, 0, 0), Color.BLACK);
        
        // 生成像素风格旗帜
        generatePixelFlagImage(themePath + "redFlag.png", blockColor, borderLight, borderDark, Color.RED);
        generatePixelFlagWrongImage(themePath + "flagWrong.png", emptyColor, Color.RED);
        
        // 生成像素风格问号
        generatePixelQuestionMarkImage(themePath + "questionMark.png", blockColor, borderLight, borderDark, Color.BLACK);
    }

    /**
     * 生成像素风格方块图片
     */
    private static void generatePixelBlockImage(String path, Color color, Color borderLight, Color borderDark) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        
        // 关闭抗锯齿以获得像素风格
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        // 填充主体
        g.setColor(color);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 3D边框效果 - 左上高光
        g.setColor(borderLight);
        for (int i = 0; i < 3; i++) {
            g.drawLine(i, i, SIZE - 1 - i, i);
            g.drawLine(i, i, i, SIZE - 1 - i);
        }
        
        // 3D边框效果 - 右下阴影
        g.setColor(borderDark);
        for (int i = 0; i < 3; i++) {
            g.drawLine(SIZE - 1 - i, i, SIZE - 1 - i, SIZE - 1 - i);
            g.drawLine(i, SIZE - 1 - i, SIZE - 1 - i, SIZE - 1 - i);
        }
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成像素风格数字图片
     */
    private static void generatePixelNumberImage(String path, int number, Color bgColor, Color textColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        // 绘制背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 绘制凹陷边框
        g.setColor(bgColor.darker());
        g.drawLine(0, 0, SIZE - 1, 0);
        g.drawLine(0, 0, 0, SIZE - 1);
        g.setColor(bgColor.brighter());
        g.drawLine(SIZE - 1, 0, SIZE - 1, SIZE - 1);
        g.drawLine(0, SIZE - 1, SIZE - 1, SIZE - 1);
        
        // 绘制像素风格数字
        if (number > 0) {
            g.setColor(textColor);
            // 使用像素字体风格
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            FontMetrics fm = g.getFontMetrics();
            String text = String.valueOf(number);
            int x = (SIZE - fm.stringWidth(text)) / 2;
            int y = (SIZE - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(text, x, y);
        }
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成像素风格炸弹图片
     */
    private static void generatePixelBombImage(String path, Color bgColor, Color bombColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        // 绘制背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 像素风格炸弹 - 简单的圆形
        g.setColor(bombColor);
        // 绘制主体（像素化圆形）
        int[][] bombPixels = {
            {0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
            {0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
            {0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
            {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
            {0,0,0,0,0,1,1,1,1,1,0,0,0,0,0}
        };
        
        int offsetX = (SIZE - 15) / 2;
        int offsetY = (SIZE - 15) / 2;
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if (bombPixels[y][x] == 1) {
                    g.fillRect(offsetX + x, offsetY + y, 1, 1);
                }
            }
        }
        
        // 绘制炸弹刺（像素线条）
        g.drawLine(SIZE/2, 2, SIZE/2, 5);
        g.drawLine(SIZE/2, SIZE-6, SIZE/2, SIZE-3);
        g.drawLine(2, SIZE/2, 5, SIZE/2);
        g.drawLine(SIZE-6, SIZE/2, SIZE-3, SIZE/2);
        
        // 高光
        g.setColor(Color.WHITE);
        g.fillRect(offsetX + 3, offsetY + 3, 2, 2);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成像素风格炸弹揭示图片
     */
    private static void generatePixelBombRevealImage(String path, Color bgColor, Color bombColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        // 红色背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 复用炸弹绘制逻辑
        g.setColor(bombColor);
        g.fillOval(7, 7, SIZE - 14, SIZE - 14);
        g.drawLine(SIZE/2, 2, SIZE/2, 5);
        g.drawLine(SIZE/2, SIZE-6, SIZE/2, SIZE-3);
        g.drawLine(2, SIZE/2, 5, SIZE/2);
        g.drawLine(SIZE-6, SIZE/2, SIZE-3, SIZE/2);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成像素风格旗帜图片
     */
    private static void generatePixelFlagImage(String path, Color bgColor, Color borderLight, Color borderDark, Color flagColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        // 绘制3D方块背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        g.setColor(borderLight);
        for (int i = 0; i < 3; i++) {
            g.drawLine(i, i, SIZE - 1 - i, i);
            g.drawLine(i, i, i, SIZE - 1 - i);
        }
        g.setColor(borderDark);
        for (int i = 0; i < 3; i++) {
            g.drawLine(SIZE - 1 - i, i, SIZE - 1 - i, SIZE - 1 - i);
            g.drawLine(i, SIZE - 1 - i, SIZE - 1 - i, SIZE - 1 - i);
        }
        
        // 绘制像素旗杆
        g.setColor(Color.BLACK);
        g.fillRect(SIZE/2, 6, 2, SIZE - 10);
        
        // 绘制像素旗帜
        g.setColor(flagColor);
        for (int i = 0; i < 6; i++) {
            g.fillRect(SIZE/2 - 6 + i, 6 + i, 6 - i, 1);
        }
        
        // 绘制底座
        g.setColor(Color.BLACK);
        g.fillRect(SIZE/2 - 4, SIZE - 6, 10, 2);
        g.fillRect(SIZE/2 - 2, SIZE - 8, 6, 2);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成像素风格错误旗帜图片
     */
    private static void generatePixelFlagWrongImage(String path, Color bgColor, Color flagColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        // 绘制背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 绘制旗帜
        g.setColor(Color.BLACK);
        g.fillRect(SIZE/2, 6, 2, SIZE - 10);
        g.setColor(flagColor);
        for (int i = 0; i < 6; i++) {
            g.fillRect(SIZE/2 - 6 + i, 6 + i, 6 - i, 1);
        }
        
        // 绘制像素 X
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        for (int i = 0; i < SIZE - 8; i++) {
            g.fillRect(4 + i, 4 + i, 2, 2);
            g.fillRect(SIZE - 6 - i, 4 + i, 2, 2);
        }
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成像素风格问号图片
     */
    private static void generatePixelQuestionMarkImage(String path, Color bgColor, Color borderLight, Color borderDark, Color textColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        
        // 绘制3D方块背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        g.setColor(borderLight);
        for (int i = 0; i < 3; i++) {
            g.drawLine(i, i, SIZE - 1 - i, i);
            g.drawLine(i, i, i, SIZE - 1 - i);
        }
        g.setColor(borderDark);
        for (int i = 0; i < 3; i++) {
            g.drawLine(SIZE - 1 - i, i, SIZE - 1 - i, SIZE - 1 - i);
            g.drawLine(i, SIZE - 1 - i, SIZE - 1 - i, SIZE - 1 - i);
        }
        
        // 绘制像素问号
        g.setColor(textColor);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        String text = "?";
        int x = (SIZE - fm.stringWidth(text)) / 2;
        int y = (SIZE - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, x, y);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成主题的所有图片
     */
    private static void generateThemeImages(String themePath, Color blockColor, Color borderColor,
            Color emptyColor, Color[] numberColors, Color bombColor, Color flagColor, Color questionColor) {
        
        File dir = new File(themePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 生成方块图片
        generateBlockImage(themePath + "block.png", blockColor, borderColor);
        
        // 生成数字图片 0-8
        for (int i = 0; i <= 8; i++) {
            generateNumberImage(themePath + i + ".png", i, emptyColor, numberColors[i]);
        }
        
        // 生成炸弹图片
        generateBombImage(themePath + "bomb.png", emptyColor, bombColor);
        generateBombRevealImage(themePath + "bombReveal.png", new Color(255, 100, 100), bombColor);
        
        // 生成旗帜图片
        generateFlagImage(themePath + "redFlag.png", blockColor, borderColor, flagColor);
        generateFlagWrongImage(themePath + "flagWrong.png", emptyColor, flagColor);
        
        // 生成问号图片
        generateQuestionMarkImage(themePath + "questionMark.png", blockColor, borderColor, questionColor);
    }

    /**
     * 生成方块图片
     */
    private static void generateBlockImage(String path, Color color, Color borderColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制方块
        g.setColor(color);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 绘制高光边框（3D效果）
        g.setColor(borderColor.brighter());
        g.drawLine(0, 0, SIZE - 1, 0);
        g.drawLine(0, 0, 0, SIZE - 1);
        
        g.setColor(borderColor.darker());
        g.drawLine(SIZE - 1, 0, SIZE - 1, SIZE - 1);
        g.drawLine(0, SIZE - 1, SIZE - 1, SIZE - 1);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成数字图片
     */
    private static void generateNumberImage(String path, int number, Color bgColor, Color textColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 绘制边框
        g.setColor(bgColor.darker());
        g.drawRect(0, 0, SIZE - 1, SIZE - 1);
        
        // 绘制数字
        if (number > 0) {
            g.setColor(textColor);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            FontMetrics fm = g.getFontMetrics();
            String text = String.valueOf(number);
            int x = (SIZE - fm.stringWidth(text)) / 2;
            int y = (SIZE - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(text, x, y);
        }
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成炸弹图片
     */
    private static void generateBombImage(String path, Color bgColor, Color bombColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 绘制炸弹
        g.setColor(bombColor);
        g.fillOval(6, 6, SIZE - 12, SIZE - 12);
        
        // 绘制炸弹刺
        g.setStroke(new BasicStroke(2));
        int center = SIZE / 2;
        g.drawLine(center, 2, center, SIZE - 2);
        g.drawLine(2, center, SIZE - 2, center);
        g.drawLine(5, 5, SIZE - 5, SIZE - 5);
        g.drawLine(SIZE - 5, 5, 5, SIZE - 5);
        
        // 高光
        g.setColor(Color.WHITE);
        g.fillOval(10, 10, 4, 4);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成炸弹揭示图片（踩到的炸弹）
     */
    private static void generateBombRevealImage(String path, Color bgColor, Color bombColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制红色背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 绘制炸弹
        g.setColor(bombColor);
        g.fillOval(6, 6, SIZE - 12, SIZE - 12);
        
        // 绘制炸弹刺
        g.setStroke(new BasicStroke(2));
        int center = SIZE / 2;
        g.drawLine(center, 2, center, SIZE - 2);
        g.drawLine(2, center, SIZE - 2, center);
        g.drawLine(5, 5, SIZE - 5, SIZE - 5);
        g.drawLine(SIZE - 5, 5, 5, SIZE - 5);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成旗帜图片
     */
    private static void generateFlagImage(String path, Color bgColor, Color borderColor, Color flagColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制方块背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        g.setColor(borderColor.brighter());
        g.drawLine(0, 0, SIZE - 1, 0);
        g.drawLine(0, 0, 0, SIZE - 1);
        g.setColor(borderColor.darker());
        g.drawLine(SIZE - 1, 0, SIZE - 1, SIZE - 1);
        g.drawLine(0, SIZE - 1, SIZE - 1, SIZE - 1);
        
        // 绘制旗杆
        g.setColor(Color.BLACK);
        g.fillRect(SIZE / 2 - 1, 8, 2, SIZE - 12);
        
        // 绘制旗帜
        g.setColor(flagColor);
        int[] xPoints = {SIZE / 2, SIZE / 2 + 10, SIZE / 2};
        int[] yPoints = {8, 13, 18};
        g.fillPolygon(xPoints, yPoints, 3);
        
        // 绘制底座
        g.setColor(Color.DARK_GRAY);
        g.fillRect(SIZE / 2 - 4, SIZE - 6, 8, 3);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成错误旗帜图片
     */
    private static void generateFlagWrongImage(String path, Color bgColor, Color flagColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        
        // 绘制旗杆
        g.setColor(Color.BLACK);
        g.fillRect(SIZE / 2 - 1, 8, 2, SIZE - 12);
        
        // 绘制旗帜
        g.setColor(flagColor);
        int[] xPoints = {SIZE / 2, SIZE / 2 + 10, SIZE / 2};
        int[] yPoints = {8, 13, 18};
        g.fillPolygon(xPoints, yPoints, 3);
        
        // 绘制红色 X
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3));
        g.drawLine(4, 4, SIZE - 4, SIZE - 4);
        g.drawLine(SIZE - 4, 4, 4, SIZE - 4);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 生成问号图片
     */
    private static void generateQuestionMarkImage(String path, Color bgColor, Color borderColor, Color textColor) {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制方块背景
        g.setColor(bgColor);
        g.fillRect(0, 0, SIZE, SIZE);
        g.setColor(borderColor.brighter());
        g.drawLine(0, 0, SIZE - 1, 0);
        g.drawLine(0, 0, 0, SIZE - 1);
        g.setColor(borderColor.darker());
        g.drawLine(SIZE - 1, 0, SIZE - 1, SIZE - 1);
        g.drawLine(0, SIZE - 1, SIZE - 1, SIZE - 1);
        
        // 绘制问号
        g.setColor(textColor);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        String text = "?";
        int x = (SIZE - fm.stringWidth(text)) / 2;
        int y = (SIZE - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, x, y);
        
        g.dispose();
        saveImage(image, path);
    }

    /**
     * 保存图片
     */
    private static void saveImage(BufferedImage image, String path) {
        try {
            ImageIO.write(image, "PNG", new File(path));
        } catch (IOException e) {
            System.err.println("无法保存图片: " + path);
            e.printStackTrace();
        }
    }
}
