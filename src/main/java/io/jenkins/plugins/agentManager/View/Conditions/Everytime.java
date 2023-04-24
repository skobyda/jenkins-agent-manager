package io.jenkins.plugins.agentManager.View.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Condition;

public abstract class Everytime implements Condition {
    public Everytime() {
    }

    @Override
    public String getName() {
        return "Everytime";
    }

    @Override
    public boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) {
            return true;
    }
}
