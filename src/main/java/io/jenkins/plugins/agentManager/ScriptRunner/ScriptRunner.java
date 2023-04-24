package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;

public abstract class ScriptRunner {
    public abstract void run(Launcher launcher, TaskListener listener, String script);
    public abstract boolean evaluateCondition(Launcher launcher, TaskListener listener, String scriptContent);
}
