package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.Messages;
import hudson.model.*;
import hudson.slaves.OfflineCause;
import io.jenkins.plugins.agentManager.Utils.HelperActions;
import org.jvnet.localizer.ResourceBundleHolder;

public abstract class SetOffline implements Action {
    @Override
    public String getName() {
        return "SetOffline";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild build, Computer computer) {
        HelperActions.setAgentOffline(listener, launcher, build, computer);
    }
}
