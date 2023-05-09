package io.jenkins.plugins.agentManager.Utils;

import hudson.Launcher;
import hudson.Messages;
import hudson.model.*;
import hudson.slaves.OfflineCause;
import org.jvnet.localizer.ResourceBundleHolder;

public class HelperActions {
    public static void rebootAgent(TaskListener listener, Launcher launcher, Computer computer) {
        ShellScriptRunner runner = new ShellScriptRunner();

        String scriptContent;

        if (computer.isUnix())
            scriptContent = "shutdown -r now";
        else
            scriptContent = "shutdown -r -f -t 0";

        runner.run(launcher, listener, scriptContent);
    }

    public static void setAgentOffline(TaskListener listener, Launcher launcher, AbstractBuild build, Computer computer) {
        ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);
        int buildNumber = build.getNumber();
        String buildName = build.getFullDisplayName();
        String computerName = computer.getDisplayName();
        String offlineMessage = String.format("Taking node '%s' offline temporarily. Triggered by build '%s' number '%d'", computerName, buildName, buildNumber);

        computer.setTemporarilyOffline(true, new OfflineCause.ByCLI(offlineMessage));
    }
}
