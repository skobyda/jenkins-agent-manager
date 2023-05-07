package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.ShellScriptRunner;

public abstract class Reboot implements Action {
    @Override
    public String getName() {
        return "Reboot";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        ShellScriptRunner runner = new ShellScriptRunner();

        String scriptContent;

        if (computer.isUnix())
            scriptContent = "shutdown -r now";
        else
            scriptContent = "shutdown -r -f -t 0";

        runner.run(launcher, listener, scriptContent);
    }
}
