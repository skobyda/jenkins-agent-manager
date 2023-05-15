package io.jenkins.plugins.agentManager.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class GracefulRebootPostBuild extends io.jenkins.plugins.agentManager.Actions.GracefulReboot implements PostBuildAction {
    @DataBoundConstructor public GracefulRebootPostBuild() {
        super();
    }

    @Extension
    @Symbol("GracefulReboot")
    public static final class DescriptorImpl extends PostBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Gracefully reboot agent";
        }
    }
}
