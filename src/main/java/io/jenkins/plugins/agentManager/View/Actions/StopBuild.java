package io.jenkins.plugins.agentManager.View.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Action;

import javax.servlet.ServletException;
import java.io.IOException;

public abstract class StopBuild implements Action {
    @Override
    public String getName() {
        return "StopBuild";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run) {
        // TODO
        try {
            run.doStop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
