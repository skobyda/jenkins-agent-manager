package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.ActionInstance;

public abstract class ScriptRunner {
    public abstract void run(Launcher launcher, TaskListener listener, ActionInstance.CustomScript script);
}

