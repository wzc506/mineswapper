package Library;

import java.io.*;
import java.util.*;

public final class RecordManager {
    private static final RecordManager INSTANCE = new RecordManager();
    private static final String RECORD_FILE = "records.dat";
    private static final int MAX_RECORDS = 10; // 每种模式最多保存10条记录
    
    private Map<String, List<Long>> records;
    
    private final List<RecordObserver> observers = new ArrayList<>();
    
    private RecordManager() {
        records = new HashMap<>();
        loadRecords();
    }
    
    public static RecordManager getInstance() {
        return INSTANCE;
    }
    
    /**
     * 添加观察者
     */
    public void addObserver(RecordObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    /**
     * 移除观察者
     */
    public void removeObserver(RecordObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * 通知所有观察者记录已更新
     */
    private void notifyObservers(String modeName) {
        for (RecordObserver observer : observers) {
            observer.onRecordUpdated(modeName);
        }
    }

    /**
     * 获取模式名称 - 委托给 GameModeFactory
     */
    public static String getModeName(int width, int height, int bombs) {
        return GameModeFactory.getMode(width, height, bombs).getName();
    }
    
    /**
     * 添加一条记录
     * @param modeName 模式名称
     * @param time 用时（毫秒）
     * @return 如果记录进入前10名则返回排名（1-10），否则返回-1
     */
    public int addRecord(String modeName, long time) {
        List<Long> modeRecords = records.computeIfAbsent(modeName, k -> new ArrayList<>());
        
        modeRecords.add(time);
        
        Collections.sort(modeRecords);
        
        while (modeRecords.size() > MAX_RECORDS) {
            modeRecords.remove(modeRecords.size() - 1);
        }
        
        int rank = modeRecords.indexOf(time) + 1;
        
        saveRecords();
        notifyObservers(modeName);
        
        if (rank > 0 && rank <= MAX_RECORDS) {
            return rank;
        }
        return -1;
    }
    
    /**
     * 获取某个模式的所有记录
     */
    public List<Long> getRecords(String modeName) {
        return records.getOrDefault(modeName, new ArrayList<>());
    }
    
    /**
     * 获取某个模式的最佳记录
     */
    public Long getBestRecord(String modeName) {
        List<Long> modeRecords = records.get(modeName);
        if (modeRecords != null && !modeRecords.isEmpty()) {
            return modeRecords.get(0);
        }
        return null;
    }
    
    /**
     * 获取格式化的记录列表字符串
     */
    public String getFormattedRecords(String modeName) {
        List<Long> modeRecords = getRecords(modeName);
        if (modeRecords.isEmpty()) {
            return "暂无记录";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("【").append(modeName).append("】最佳记录：\n");
        for (int i = 0; i < modeRecords.size(); i++) {
            sb.append(String.format("%2d. %s\n", i + 1, TimeChecker.calculateTime(modeRecords.get(i))));
        }
        return sb.toString();
    }
    
    /**
     * 从文件加载记录
     */
    @SuppressWarnings("unchecked")
    private void loadRecords() {
        File file = new File(RECORD_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            records = (Map<String, List<Long>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("加载记录失败: " + e.getMessage());
            records = new HashMap<>();
        }
    }
    
    /**
     * 保存记录到文件
     */
    private void saveRecords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RECORD_FILE))) {
            oos.writeObject(records);
        } catch (IOException e) {
            System.err.println("保存记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 清除所有记录
     */
    public void clearAllRecords() {
        records.clear();
        saveRecords();
    }
    
    /**
     * 清除某个模式的记录
     */
    public void clearRecords(String modeName) {
        records.remove(modeName);
        saveRecords();
    }
}
