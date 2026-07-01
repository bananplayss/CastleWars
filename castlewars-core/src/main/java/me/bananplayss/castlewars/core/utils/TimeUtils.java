package me.bananplayss.castlewars.core.utils;

public class TimeUtils {

    public static String formatMMSS(long millis) {
        if (millis < 0) millis = 0;

        long totalSeconds = millis / 1000;

        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
