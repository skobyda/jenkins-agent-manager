package io.jenkins.plugins.agentManager.Conditions;

import hudson.Launcher;
import hudson.model.Result;
import hudson.model.*;
import hudson.util.RunList;

public abstract class HistoryTime implements Condition {
    private final int quantity;
    private final long averageTime;

    public int getQuantity() {
        return quantity;
    }

    public long getAverageTime() {
        return averageTime;
    }

    public HistoryTime(int quantity, long averageTime) {
        this.quantity = quantity;
        this.averageTime = averageTime;
    }

    @Override
    public String getName() {
        return "HistoryFailed";
    }

    @Override
    public boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) {
        Computer computer = Computer.currentComputer();
        RunList<? extends Run<?, ?>> runList = computer.getBuilds();
        int quantity = getQuantity();

        Run previousRun = runList.iterator().next();
        long currentAverage = 0;
        for (int i = 0; i < quantity; i++) {
            // If there is not enough runs to evaluate condition, don't pass the condition
            if (previousRun == null)
                return false;

            currentAverage += previousRun.getDuration();
            previousRun = previousRun.getPreviousBuild();
        }

        // Check
        return (currentAverage / quantity) < averageTime;
    }
}
