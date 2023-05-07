package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.GroovyScriptRunner;
import io.jenkins.plugins.agentManager.Utils.ShellScriptRunner;

public abstract class GroovyScript implements Action {
    private final String scriptText;
    // TODO support groovy and windows thing
    private final String language;

    public GroovyScript(String scriptText) {
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
        GroovyScriptRunner runner = new GroovyScriptRunner();

        String scriptContent = getScriptText();
        runner.run(launcher, listener, scriptContent);
    }
}
