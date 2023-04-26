package io.jenkins.plugins.agentManager.View.Conditions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class NoCondition extends io.jenkins.plugins.agentManager.View.Conditions.NoCondition implements PostBuildCondition {
    @DataBoundConstructor
    public NoCondition() {
        super();
    }

    @Extension
    @Symbol("NoCondition")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "No condition";
        }
    }
}
