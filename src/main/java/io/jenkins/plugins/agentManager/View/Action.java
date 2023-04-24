package io.jenkins.plugins.agentManager.View;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;

public interface Action {
    String getName();

    void runAction(TaskListener listener, Launcher launcher, AbstractBuild run);
}
