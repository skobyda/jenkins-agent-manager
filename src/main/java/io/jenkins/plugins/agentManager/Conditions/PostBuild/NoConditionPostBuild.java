package io.jenkins.plugins.agentManager.Conditions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class NoConditionPostBuild extends io.jenkins.plugins.agentManager.Conditions.NoCondition implements PostBuildCondition {
    @DataBoundConstructor
    public NoConditionPostBuild() {
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
