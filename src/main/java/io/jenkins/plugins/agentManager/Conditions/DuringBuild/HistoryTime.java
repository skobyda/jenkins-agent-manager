package io.jenkins.plugins.agentManager.Conditions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.DuringBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class HistoryTime extends io.jenkins.plugins.agentManager.Conditions.HistoryTime implements DuringBuildCondition {
    @DataBoundConstructor
    public HistoryTime(int quantity, long averageTime) {
        super(quantity, averageTime);
    }

    @Extension
    @Symbol("History")
    public static final class DescriptorImpl extends DuringBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Previous builds finish faster than";
        }
    }
}
