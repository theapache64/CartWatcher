package com.theah64.retrokit.utils;

/**
 * Created by theapache64 on 18/9/17.
 */

public class TimeUtils {

    public static int getPercentageFinished(long start, long now, long end) {
        long totalDuration = end - start;
        long timeSpent = now - start;
        float percFinished = (float) timeSpent / totalDuration * 100;
        return (int) percFinished;
    }
}
