package io.jenkins.plugins.agentManager.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Cleanup extends io.jenkins.plugins.agentManager.Actions.Cleanup implements PostBuildAction {
    @DataBoundConstructor
    public Cleanup() {
        super();
    }

    @Override
    public String getName() {
        return "Cleanup";
    }

    @Extension
    @Symbol("Cleanup")
    public static final class DescriptorImpl extends PostBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Cleanup workspace";
        }
    }
}
