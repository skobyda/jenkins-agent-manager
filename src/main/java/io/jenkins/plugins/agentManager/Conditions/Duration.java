package io.jenkins.plugins.agentManager.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.Time;

import java.util.concurrent.TimeUnit;

public abstract class Duration implements Condition {
    private final String durationCondition;
    private final long time;
    private final String unit;

    public String getDurationCondition() {
        return durationCondition;
    }

    public long getTime() {
        return time;
    }

    public String getUnit() {
        return unit;
    }

    public Duration(String durationCondition, long time, String unit) {
        this.durationCondition = durationCondition;
        this.time = time;
        this.unit = unit;
    }

    @Override
    public String getName() {
        return "Duration";
    }

    @Override
    public boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) {
        long buildDuration = run.getDuration();
        String durationCondition = getDurationCondition();
        String unit = getUnit();
        long time = getTime();
        long expectedDurationInMilliseconds = Time.convertToMilliseconds(time, unit);

        boolean returnVal;
        if (durationCondition.equals("Build took more than"))
            returnVal = buildDuration > expectedDurationInMilliseconds;
        else
            returnVal = buildDuration < expectedDurationInMilliseconds;

        return returnVal;
    }
}
