package io.jenkins.plugins.agentManager.Actions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PreBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class SetOffline extends io.jenkins.plugins.agentManager.Actions.SetOffline implements PreBuildAction {
    @DataBoundConstructor public SetOffline() {
        super();
    }

    @Extension
    @Symbol("SetOffline")
    public static final class DescriptorImpl extends PreBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Take agent offline";
        }
    }
}
