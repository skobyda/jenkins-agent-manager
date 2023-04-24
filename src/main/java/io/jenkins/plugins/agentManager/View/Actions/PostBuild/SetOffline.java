package io.jenkins.plugins.agentManager.View.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class SetOffline extends io.jenkins.plugins.agentManager.View.Actions.SetOffline implements PostBuildAction {
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
