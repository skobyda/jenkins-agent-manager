package io.jenkins.plugins.agentManager.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.ScriptRunner.BashScriptRunner;
import io.jenkins.plugins.agentManager.Utils.ScriptRunner.BatchScriptRunner;
import io.jenkins.plugins.agentManager.Utils.ScriptRunner.ScriptRunner;

public abstract class Script implements Condition {
    private final String scriptText;
    // TODO support groovy and windows thing
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
        ScriptRunner runner = null;
        listener.getLogger().println(getScriptText());
        // TODO constant
        if ("BASH".equals(getLanguage())) {
            runner = new BashScriptRunner();
        } else if ("GROOVY".equals(getLanguage())) {
            runner = new BatchScriptRunner();
        }
        listener.getLogger().println(runner);

        String scriptContent = getScriptText();
        return runner.evaluateCondition(launcher, listener, scriptContent);
    }
}
