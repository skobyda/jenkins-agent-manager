package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;

import javax.servlet.ServletException;
import java.io.IOException;

public abstract class StopBuild implements Action {
    @Override
    public String getName() {
        return "StopBuild";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild build, Computer computer) throws ServletException, IOException {
        build.doStop();
    }
}
