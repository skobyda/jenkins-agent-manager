package io.jenkins.plugins.agentManager.Conditions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.PreBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class HistoryTimePreBuild extends io.jenkins.plugins.agentManager.Conditions.HistoryTime implements PreBuildCondition {
    @DataBoundConstructor
    public HistoryTimePreBuild(int quantity, long averageTime) {
        super(quantity, averageTime);
    }

    @Extension
    @Symbol("History")
    public static final class DescriptorImpl extends PreBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Previous builds finish faster than";
        }
    }
}
