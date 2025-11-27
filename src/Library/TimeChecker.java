package Library;
public final class TimeChecker {
    private static final TimeChecker INSTANCE = new TimeChecker();
    
    private TimeChecker() {}
    
    public static TimeChecker getInstance() {
        return INSTANCE;
    }

    public String format(long time) {
        return calculateTime(time);
    }

    public static String calculateTime(long time)
    {
        int CONVERT_TO_SEC = 1000;
        int CONVERT_TO_OTHERS = 60;

        int ms = (int) time;
        int sec = ms / CONVERT_TO_SEC;
        int min = sec / CONVERT_TO_OTHERS;
        int hr = min / CONVERT_TO_OTHERS; 

        if (hr == 0)
        {
            if(min == 0)
            {
                if (sec == 0)
                    return ms + " ms";
                else
                    return sec + " s " + ms % 1000 + " ms";
            } else
                return min + " min " + sec % CONVERT_TO_OTHERS + " s " + ms % CONVERT_TO_SEC + " ms";
        } else
            return hr + " hour " + min % CONVERT_TO_OTHERS + " min " + sec % CONVERT_TO_OTHERS + " s " + ms % CONVERT_TO_SEC + " ms";
    }
}
