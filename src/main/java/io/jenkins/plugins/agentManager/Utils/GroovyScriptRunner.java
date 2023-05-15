package io.jenkins.plugins.agentManager.Utils;

import hudson.Launcher;
import hudson.model.TaskListener;
import hudson.remoting.Channel;
import hudson.remoting.VirtualChannel;
import hudson.util.RemotingDiagnostics;

import java.io.IOException;
import java.util.Objects;

public class GroovyScriptRunner {
    public String executeScript(Launcher launcher, TaskListener listener, String scriptContent) throws IOException, InterruptedException {
        VirtualChannel channel = launcher.getChannel();
        assert channel != null;

        return RemotingDiagnostics.executeGroovy(scriptContent, channel);
    }

    public void run(Launcher launcher, TaskListener listener, String script) {
        try {
            executeScript(launcher, listener, script);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean evaluateCondition(Launcher launcher, TaskListener listener, String scriptContent) {
        // String result = executeScript(launcher, listener, scriptContent);
        // TODO implement groovy script condition evaluation

        return false;
    }
}
