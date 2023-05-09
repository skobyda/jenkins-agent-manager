package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.HelperActions;
import io.jenkins.plugins.agentManager.Utils.ShellScriptRunner;

public abstract class Reboot implements Action {
    @Override
    public String getName() {
        return "Reboot";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        HelperActions.setAgentOffline(listener, launcher, run, computer);

        HelperActions.rebootAgent(listener, launcher, computer);
    }
}
