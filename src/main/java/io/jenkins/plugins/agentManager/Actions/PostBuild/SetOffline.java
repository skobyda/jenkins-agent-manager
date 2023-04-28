package io.jenkins.plugins.agentManager.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class SetOffline extends io.jenkins.plugins.agentManager.Actions.SetOffline implements PostBuildAction {
    @DataBoundConstructor public SetOffline() {
        super();
    }

    @Override
    public String getName() {
        return "SetOffline";
    }

    @Extension
    @Symbol("SetOffline")
    public static final class DescriptorImpl extends PostBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Take agent offline";
        }
    }
}
