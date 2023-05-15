package io.jenkins.plugins.agentManager.Actions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class SetOfflineDuringBuild extends io.jenkins.plugins.agentManager.Actions.SetOffline implements DuringBuildAction {
    @DataBoundConstructor public SetOfflineDuringBuild() {
        super();
    }

    @Override
    public String getName() {
        return "SetOffline";
    }

    @Extension
    @Symbol("SetOffline")
    public static final class DescriptorImpl extends DuringBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Take agent offline";
        }
    }
}
