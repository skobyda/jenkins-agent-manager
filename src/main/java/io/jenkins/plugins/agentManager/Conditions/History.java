package io.jenkins.plugins.agentManager.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.util.RunList;

public abstract class History implements Condition {
    private final String historyCondition;
    private final int quantity;

    public String getHistoryCondition() {
        return historyCondition;
    }

    public int getQuantity() {
        return quantity;
    }

    public History(String historyCondition, int quantity) {
        this.historyCondition = historyCondition;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return "History";
    }

    @Override
    public boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) {
        Computer computer = Computer.currentComputer();
        RunList<? extends Run<?, ?>> runList = computer.getBuilds();
        int quantity = getQuantity();

        System.out.println("History");
        System.out.println(quantity);
        System.out.println(getHistoryCondition());
        // TODO fix this
        for (int i = 0; i < quantity; i++) {
            // If there is not enough runs to evaluate condition, pass the condition
            if (!runList.iterator().hasNext())
                return true;
            // Run run = runList.iterator().next();
        }

        return false;
    }
}
