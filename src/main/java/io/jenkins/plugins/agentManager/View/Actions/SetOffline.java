package io.jenkins.plugins.agentManager.View.Actions;

import hudson.Launcher;
import hudson.Messages;
import hudson.model.*;
import hudson.slaves.OfflineCause;
import io.jenkins.plugins.agentManager.View.Action;
import org.jvnet.localizer.ResourceBundleHolder;

public abstract class SetOffline implements Action {
    @Override
    public String getName() {
        return "SetOffline";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run) {
        listener.getLogger().println("Set offline");
        Computer computer = Computer.currentComputer();

        // if (computer != null)
        //     TODO

        // TODO localization
        ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);
        User user = User.current();
        int buildNumber = run.getNumber();
        String buildName = run.getFullDisplayName();
        String nodeName = computer.getNode().getDisplayName();
        String offlineMessage = String.format("Taking node '%s' offline temporarily. Triggered by build '%s' number '%d'", nodeName, buildName, buildNumber);

        // TODO research if ByCLI should be used instead
        computer.setTemporarilyOffline(true, new OfflineCause.ByCLI(offlineMessage));
    }
}
