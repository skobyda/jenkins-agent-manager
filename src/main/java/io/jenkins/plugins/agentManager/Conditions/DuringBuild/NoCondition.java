package io.jenkins.plugins.agentManager.Conditions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.DuringBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class NoCondition extends io.jenkins.plugins.agentManager.Conditions.NoCondition implements DuringBuildCondition {
    @DataBoundConstructor
    public NoCondition() {
        super();
    }

    @Extension
    @Symbol("NoCondition")
    public static final class DescriptorImpl extends DuringBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "No condition (action runs everytime)";
        }
    }
}
