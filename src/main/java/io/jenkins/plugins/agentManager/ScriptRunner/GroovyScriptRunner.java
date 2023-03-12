package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.ScriptNodeProperty;
import io.jenkins.plugins.agentManager.ScriptRunner.ScriptRunner;

public class GroovyScriptRunner extends ScriptRunner {
    public void run(Launcher launcher, TaskListener listener, ScriptNodeProperty.ScriptInstance script) {
        // noop
    }
}
