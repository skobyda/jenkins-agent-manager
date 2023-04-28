package io.jenkins.plugins.agentManager.Actions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PreBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Cleanup extends io.jenkins.plugins.agentManager.Actions.Cleanup implements PreBuildAction {
    @DataBoundConstructor
    public Cleanup() {
        super();
    }

    @Extension
    @Symbol("Cleanup")
    public static final class DescriptorImpl extends PreBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Cleanup workspace";
        }
    }
}
