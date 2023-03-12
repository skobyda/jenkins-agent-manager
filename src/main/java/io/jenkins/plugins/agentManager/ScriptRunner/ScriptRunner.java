package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.ScriptNodeProperty;

public abstract class ScriptRunner {
    public abstract void run(Launcher launcher, TaskListener listener, ScriptNodeProperty.ScriptInstance script);
}

