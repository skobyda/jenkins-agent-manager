package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.ActionInstance;

public abstract class ScriptRunner {
    public abstract void run(Launcher launcher, TaskListener listener, ActionInstance.Action.CustomScript script);
}

