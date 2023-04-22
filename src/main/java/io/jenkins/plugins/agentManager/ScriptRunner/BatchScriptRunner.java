package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.ConditionInstance.Script;

public class BatchScriptRunner extends ScriptRunner {
    public void run(Launcher launcher, TaskListener listener, String script) {
        // noop
    }

    public boolean evaluateCondition(Launcher launcher, TaskListener listener, Script script) {
        return true;
        // noop
    }
}
