package io.jenkins.plugins.agentManager.Utils.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;

public class BatchScriptRunner extends ScriptRunner {
    public void run(Launcher launcher, TaskListener listener, String script) {
        // noop
    }

    public boolean evaluateCondition(Launcher launcher, TaskListener listener, String scriptContent) {
        return true;
        // noop
    }
}
