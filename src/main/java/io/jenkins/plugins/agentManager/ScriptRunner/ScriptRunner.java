package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.ConditionInstance.Script;

public abstract class ScriptRunner {
    public abstract void run(Launcher launcher, TaskListener listener, String script);
    public abstract boolean evaluateCondition(Launcher launcher, TaskListener listener, Script script);
}
