package io.jenkins.plugins.agentManager.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.util.RunList;

public abstract class HistoryFailed implements Condition {
    private final int quantity;

    public int getQuantity() {
        return quantity;
    }

    public HistoryFailed(int quantity) {
        this.quantity = quantity;
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
        for (int i = 0; i < quantity; i++) {
            // If there is not enough runs to evaluate condition, don't pass the condition
            if (previousRun == null)
                return false;

            // If some introspected builds didn't fail, condition doesn't pass
            if (!previousRun.getResult().equals(Result.FAILURE))
                return false;

            previousRun = previousRun.getPreviousBuild();
        }

        return true;
    }
}
