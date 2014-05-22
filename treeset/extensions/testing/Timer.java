package treeset.extensions.testing;

/**
 * Basic timer for benchmarking. If used from static context, all calls are shared.
 *
 * Created by daemontus on 11/04/14.
 */
public class Timer {

    private static long time;
    private static long lap;

    private long mTime;
    private long mLap;


    public static void start() {
        time = System.currentTimeMillis();
        lap = time;
    }

    public static long lap() {
        long tmp = System.currentTimeMillis() - lap;
        lap = System.currentTimeMillis();
        return tmp;
    }

    public static long stop() {
        return System.currentTimeMillis() - time;
    }

    public void startTimer() {
        mTime = System.currentTimeMillis();
        mLap = mTime;
    }

    public long lapTime() {
        long tmp = System.currentTimeMillis() - mLap;
        mLap = System.currentTimeMillis();
        return tmp;
    }

    public long stopTimer() {
        return System.currentTimeMillis() - mTime;
    }

}
