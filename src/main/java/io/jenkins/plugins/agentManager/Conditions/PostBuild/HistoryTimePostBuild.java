package io.jenkins.plugins.agentManager.Conditions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class HistoryTimePostBuild extends io.jenkins.plugins.agentManager.Conditions.HistoryTime implements PostBuildCondition {
    @DataBoundConstructor
    public HistoryTimePostBuild(int quantity, long averageTime) {
        super(quantity, averageTime);
    }

    @Extension
    @Symbol("History")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Previous builds finish faster than";
        }
    }
}
