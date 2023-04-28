package io.jenkins.plugins.agentManager.Actions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class GracefulReboot extends io.jenkins.plugins.agentManager.Actions.GracefulReboot implements DuringBuildAction {
    @DataBoundConstructor public GracefulReboot() {
        super();
    }

    @Extension
    @Symbol("GracefulReboot")
    public static final class DescriptorImpl extends DuringBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Gracefully reboot agent";
        }
    }
}
