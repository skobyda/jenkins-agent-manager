package io.jenkins.plugins.agentManager.View.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Condition;

public abstract class NoCondition implements Condition {
    public NoCondition() {
    }

    @Override
    public String getName() {
        return "NoCondition";
    }

    @Override
    public boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) {
            return true;
    }
}
