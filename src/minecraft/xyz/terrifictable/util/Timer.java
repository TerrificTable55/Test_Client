package xyz.terrifictable.util;

public class Timer {

    public long lastMs = System.currentTimeMillis();

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    public boolean hasReached(final double milliseconds) {
        return this.getCurrentMS() - this.lastMs >= milliseconds;
    }
    public boolean delay(final float milliSec) {
        return this.getTime() - this.lastMs >= milliSec;
    }
    public void reset() {
        lastMs = System.currentTimeMillis();
    }
    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - lastMs >= time) {
            if (reset)
                reset();
            return true;
        }
        return false;
    }
    public static long getTime() {
        return System.nanoTime() / 1000000;
    }
}
