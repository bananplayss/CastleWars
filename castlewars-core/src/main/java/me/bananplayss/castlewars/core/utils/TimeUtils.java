package me.bananplayss.castlewars.core.utils;

import java.text.DecimalFormat;

public class TimeUtils {

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public static String formatMMSS(long millis) {
        if (millis < 0) millis = 0;

        long totalSeconds = millis / 1000;

        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String format(long millis) {
        return df.format(millis / 1000.0);
    }
}
