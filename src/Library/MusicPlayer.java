package Library;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicPlayer {
    private static MusicPlayer instance = null;
    private Clip clip;
    private List<File> musicFiles;
    private int currentIndex = 0;
    private int selectedIndex = -1; 
    private boolean isPlaying = false;
    private Random random = new Random();

    private MusicPlayer() {  
        musicFiles = new ArrayList<>();
        loadMusicFiles();
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    /**
     * 加载bgm文件夹中的所有音乐文件
     */
    private void loadMusicFiles() {
        File bgmFolder = new File("bgm");
        if (bgmFolder.exists() && bgmFolder.isDirectory()) {
            File[] files = bgmFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(".wav")) {
                        musicFiles.add(file);
                    }
                }
            }
        }
    }

    /**
     * 获取音乐文件列表（用于显示在界面上）
     */
    public List<String> getMusicNames() {
        List<String> names = new ArrayList<>();
        for (File file : musicFiles) {
            String name = file.getName();
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                name = name.substring(0, dotIndex);
            }
            names.add(name);
        }
        return names;
    }

    /**
     * 获取音乐文件数量
     */
    public int getMusicCount() {
        return musicFiles.size();
    }

    /**
     * 设置选择的音乐索引（-1 表示随机播放）
     */
    public void setSelectedIndex(int index) {
        this.selectedIndex = index;
    }

    /**
     * 播放音乐
     */
    public void play() {
        if (musicFiles.isEmpty()) {
            System.out.println("没有找到音乐文件");
            return;
        }

        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            if (selectedIndex >= 0 && selectedIndex < musicFiles.size()) {
                currentIndex = selectedIndex;
            } else {
                currentIndex = random.nextInt(musicFiles.size());
            }
            
            File musicFile = musicFiles.get(currentIndex);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && isPlaying) {
                    playNext();
                }
            });

            clip.start();
            isPlaying = true;
            System.out.println("正在播放: " + musicFile.getName());

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("播放音乐时出错: " + e.getMessage());
            playNext();
        }
    }

    /**
     * 播放指定索引的音乐
     */
    public void play(int index) {
        setSelectedIndex(index);
        play();
    }

    /**
     * 播放下一首音乐
     */
    public void playNext() {
        if (musicFiles.isEmpty()) {
            return;
        }

        if (clip != null) {
            clip.close();
        }

        play();
    }

    /**
     * 停止播放
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
            isPlaying = false;
            System.out.println("音乐已停止");
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPlaying = false;
            System.out.println("音乐已暂停");
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
            isPlaying = true;
            System.out.println("继续播放");
        }
    }

    /**
     * 设置音量 (0.0 到 1.0)
     */
    public void setVolume(float volume) {
        if (clip != null && clip.isOpen()) {
            try {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float min = volumeControl.getMinimum();
                float max = volumeControl.getMaximum();
                float gain = min + (max - min) * volume;
                volumeControl.setValue(gain);
            } catch (IllegalArgumentException e) {
                System.err.println("无法设置音量: " + e.getMessage());
            }
        }
    }

    /**
     * 检查是否正在播放
     */
    public boolean isPlaying() {
        return isPlaying && clip != null && clip.isRunning();
    }
}
