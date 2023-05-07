package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;

public interface Action {
    String getName();

    void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) throws Exception;
}
