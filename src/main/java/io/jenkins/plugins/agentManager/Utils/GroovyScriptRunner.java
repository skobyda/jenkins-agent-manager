package io.jenkins.plugins.agentManager.Utils;

import hudson.Launcher;
import hudson.model.TaskListener;
import hudson.util.RemotingDiagnostics;

import java.io.IOException;

public class GroovyScriptRunner {
    public String executeScript(Launcher launcher, TaskListener listener, String scriptContent) throws IOException, InterruptedException {
        return RemotingDiagnostics.executeGroovy(scriptContent, launcher.getChannel());
    }

    public void run(Launcher launcher, TaskListener listener, String script) {
        try {
            System.out.println("HERE");
            String result = executeScript(launcher, listener, script);
            System.out.println(result);
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
            String result = executeScript(launcher, listener, scriptContent);
            System.out.println(result);
            if (result == "whatistheresultofeexcutegroovy")
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
