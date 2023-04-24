package io.jenkins.plugins.agentManager.View.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Condition;

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
        System.out.println("Duration");
        System.out.println(durationCondition);
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
        long expectedDurationInMilliseconds;

        if (unit.equals("milliseconds"))
            expectedDurationInMilliseconds = time;
        else if (unit.equals("seconds"))
            expectedDurationInMilliseconds = time * 1000;
        else if (unit.equals("minutes"))
            expectedDurationInMilliseconds = time * 1000 * 60;
        else
            return false;
        // TODO add exception or error message

        boolean returnVal;
        if (durationCondition.equals("Build took more than"))
            returnVal = buildDuration > expectedDurationInMilliseconds;
        else
            returnVal = buildDuration < expectedDurationInMilliseconds;

        listener.getLogger().println("duration");
        listener.getLogger().println(buildDuration);
        listener.getLogger().println(expectedDurationInMilliseconds);
        return returnVal;
    }
}
