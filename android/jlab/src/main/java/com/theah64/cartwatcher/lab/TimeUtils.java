package com.theah64.cartwatcher.lab;

/**
 * Created by theapache64 on 18/9/17.
 */

class TimeUtils {

    static int getPercentageFinished(int start, int now, int end) {
        int totalDuration = end - start;
        int timeSpent = now - start;
        float percFinished = (float) timeSpent / totalDuration * 100;
        return (int) percFinished;
    }
}
