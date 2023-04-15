package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Action;
import io.jenkins.plugins.agentManager.View.Condition;

public class BatchScriptRunner extends ScriptRunner {
    public void run(Launcher launcher, TaskListener listener, String script) {
        // noop
    }

    public boolean evaluateCondition(Launcher launcher, TaskListener listener, Condition.Script script) {
        return true;
        // noop
    }
}
