package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.ActionInstance;

import java.io.IOException;

public class BatchScriptRunner extends ScriptRunner {
    public void run(Launcher launcher, TaskListener listener, ActionInstance.Action.CustomScript script) {
        // noop
    }

    public boolean evaluateCondition(Launcher launcher, TaskListener listener, ActionInstance.Condition.Script script) {
        return true;
        // noop
    }
}
