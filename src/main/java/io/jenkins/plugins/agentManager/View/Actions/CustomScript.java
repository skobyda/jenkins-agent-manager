package io.jenkins.plugins.agentManager.View.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.ScriptRunner.BashScriptRunner;
import io.jenkins.plugins.agentManager.ScriptRunner.BatchScriptRunner;
import io.jenkins.plugins.agentManager.ScriptRunner.ScriptRunner;
import io.jenkins.plugins.agentManager.View.Action;

public abstract class CustomScript implements Action {
    private final String scriptText;
    // TODO support groovy and windows thing
    private final String language;

    public CustomScript(String scriptText) {
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

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run) {
        // TODO
        // Use factory here ?
        ScriptRunner runner = null;
        listener.getLogger().println(getScriptText());
        if ("BASH".equals(getLanguage())) {
            runner = new BashScriptRunner();
        } else if ("GROOVY".equals(getLanguage())) {
            runner = new BatchScriptRunner();
        }
        listener.getLogger().println(runner);

        String scriptContent = getScriptText();
        runner.run(launcher, listener, scriptContent);
    }
}
