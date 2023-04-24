package io.jenkins.plugins.agentManager.View.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Reboot extends io.jenkins.plugins.agentManager.View.Actions.Reboot implements PostBuildAction {
    @DataBoundConstructor public Reboot() {
        super();
    }

    @Extension
    @Symbol("Reboot")
    public static final class DescriptorImpl extends PostBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Reboot agent";
        }
    }
}
