package io.jenkins.plugins.agentManager.Actions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class RebootDuringBuild extends io.jenkins.plugins.agentManager.Actions.Reboot implements DuringBuildAction {
    @DataBoundConstructor public RebootDuringBuild() {
        super();
    }

    @Extension
    @Symbol("Reboot")
    public static final class DescriptorImpl extends DuringBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Reboot agent";
        }
    }
}
