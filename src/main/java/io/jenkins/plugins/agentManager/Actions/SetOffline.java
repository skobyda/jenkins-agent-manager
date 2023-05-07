package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.Messages;
import hudson.model.*;
import hudson.slaves.OfflineCause;
import org.jvnet.localizer.ResourceBundleHolder;

public abstract class SetOffline implements Action {
    @Override
    public String getName() {
        return "SetOffline";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        // if (computer == null)
        //     TODO

        ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);
        User user = User.current();
        int buildNumber = run.getNumber();
        String buildName = run.getFullDisplayName();
        String computerName = computer.getDisplayName();
        String offlineMessage = String.format("Taking node '%s' offline temporarily. Triggered by build '%s' number '%d'", computerName, buildName, buildNumber);

        computer.setTemporarilyOffline(true, new OfflineCause.ByCLI(offlineMessage));
    }
}
