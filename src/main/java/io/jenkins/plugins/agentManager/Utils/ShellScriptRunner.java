package io.jenkins.plugins.agentManager.Utils;

import hudson.Launcher;
import hudson.model.TaskListener;

import java.io.IOException;

public class ShellScriptRunner {
    public int executeScript(Launcher launcher, TaskListener listener, String scriptContent) throws IOException, InterruptedException {
        return launcher.launch().cmdAsSingleString(scriptContent).stdout(listener).join();
    }

    public void run(Launcher launcher, TaskListener listener, String script) {
        try {
            executeScript(launcher, listener, script);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean evaluateCondition(Launcher launcher, TaskListener listener, String scriptContent) {
        boolean condition = true;
        try {
            if (executeScript(launcher, listener, scriptContent) != 0)
                condition = false;
        } catch (IOException | InterruptedException e) {
            condition = false;
        }

        return condition;
    }
}
