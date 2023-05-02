package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.ScriptRunner;

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

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        listener.getLogger().println(getScriptText());
        ScriptRunner runner = new ScriptRunner();
        listener.getLogger().println(runner);

        String scriptContent = getScriptText();
        runner.run(launcher, listener, scriptContent);
    }
}
