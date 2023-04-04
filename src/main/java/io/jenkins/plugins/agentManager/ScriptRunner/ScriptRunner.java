package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Action;
import io.jenkins.plugins.agentManager.View.ActionInstance;
import io.jenkins.plugins.agentManager.View.Condition;

import java.io.IOException;

public abstract class ScriptRunner {
    public abstract void run(Launcher launcher, TaskListener listener, Action.CustomScript script);
    public abstract boolean evaluateCondition(Launcher launcher, TaskListener listener, Condition.Script script);
}

