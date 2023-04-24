package io.jenkins.plugins.agentManager.View;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;

public interface Condition {
    String getName();

    boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run);
}
