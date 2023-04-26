package io.jenkins.plugins.agentManager.View.Conditions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PreBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class NoCondition extends io.jenkins.plugins.agentManager.View.Conditions.NoCondition implements PreBuildCondition {
    @DataBoundConstructor
    public NoCondition() {
        super();
    }

    @Extension
    @Symbol("NoCondition")
    public static final class DescriptorImpl extends PreBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "No condition";
        }
    }
}
