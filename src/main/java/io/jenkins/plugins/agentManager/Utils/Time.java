package io.jenkins.plugins.agentManager.Utils;

import java.util.concurrent.TimeUnit;

public class Time {
    private static final String MILLISECONDS = "milliseconds";
    private static final String SECONDS = "seconds";
    private static final String MINUTES = "minutes";

    public static String getMillisecondsString() {
        return MILLISECONDS;
    }

    public static String getSecondsString() {
        return SECONDS;
    }

    public static String getMinutesString() {
        return MINUTES;
    }

    public static long convertToMilliseconds(long time, String unit) {
        if (unit.equals(Time.getMillisecondsString()))
            return time;
        else if (unit.equals(Time.getSecondsString()))
            return TimeUnit.MILLISECONDS.convert(time, TimeUnit.SECONDS);
        else if (unit.equals(Time.getMinutesString()))
            return TimeUnit.MILLISECONDS.convert(time, TimeUnit.MINUTES);
        else
            return 0;

    }
}
