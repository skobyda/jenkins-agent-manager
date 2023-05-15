package io.jenkins.plugins.agentManager.Conditions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.PreBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class NoConditionPreBuild extends io.jenkins.plugins.agentManager.Conditions.NoCondition implements PreBuildCondition {
    @DataBoundConstructor
    public NoConditionPreBuild() {
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
