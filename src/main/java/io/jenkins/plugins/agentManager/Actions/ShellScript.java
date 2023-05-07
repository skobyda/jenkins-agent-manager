package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.ShellScriptRunner;

public abstract class ShellScript implements Action {
    private final String scriptText;
    private final String language;

    public ShellScript(String scriptText) {
        this.scriptText = scriptText;
        this.language = "BASH";
    }

    public String getScriptText() {
        return scriptText;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String getName() {
        return "CustomScript";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        ShellScriptRunner runner = new ShellScriptRunner();

        String scriptContent = getScriptText();
        runner.run(launcher, listener, scriptContent);
    }
}
