package io.jenkins.plugins.agentManager.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.ShellScriptRunner;

public abstract class Script implements Condition {
    private final String scriptText;
    private final String language;
    public Script(String scriptText) {
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
        return "Script";
    }

    @Override
    public boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) {
        ShellScriptRunner runner = new ShellScriptRunner();

        String scriptContent = getScriptText();
        return runner.evaluateCondition(launcher, listener, scriptContent);
    }
}
