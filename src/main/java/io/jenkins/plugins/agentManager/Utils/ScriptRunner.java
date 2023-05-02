package io.jenkins.plugins.agentManager.Utils;

import hudson.Launcher;
import hudson.model.TaskListener;

import java.io.IOException;

public class ScriptRunner {
    public int executeScript(Launcher launcher, TaskListener listener, String scriptContent) throws IOException, InterruptedException {
        return launcher.launch().cmdAsSingleString(scriptContent).stdout(listener).join();
    }

    public void run(Launcher launcher, TaskListener listener, String script) {
        try {
            executeScript(launcher, listener, script);
        } catch (IOException e) {
            // TODO e.printStackTrace(listener.fatalError());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // TODO
            throw new RuntimeException(e);
        }
    }

    public boolean evaluateCondition(Launcher launcher, TaskListener listener, String scriptContent) {
        boolean condition = true;
        try {
            if (executeScript(launcher, listener, scriptContent) != 0)
                condition = false;
        } catch (IOException e) {
            // TODO
            condition = false;
        } catch (InterruptedException e) {
        // TODO
            condition = false;
        }

        return condition;
    }
}
