package io.jenkins.plugins.agentManager.Actions;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;

import java.io.IOException;
import java.util.Iterator;

public abstract class Cleanup implements Action {
    @Override
    public String getName() {
        return "Cleanup";
    }
    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        FilePath workspace = run.getWorkspace();

        try {
            assert workspace != null;
            workspace.deleteRecursive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
