package io.jenkins.plugins.agentManager.Actions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class CleanupDuringBuild extends io.jenkins.plugins.agentManager.Actions.Cleanup implements DuringBuildAction {
    @DataBoundConstructor
    public CleanupDuringBuild() {
        super();
    }

    @Override
    public String getName() {
        return "Cleanup";
    }

    @Extension
    @Symbol("Cleanup")
    public static final class DescriptorImpl extends DuringBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Cleanup workspace";
        }
    }
}
