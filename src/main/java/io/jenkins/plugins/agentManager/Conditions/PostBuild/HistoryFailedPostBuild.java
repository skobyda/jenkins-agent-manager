package io.jenkins.plugins.agentManager.Conditions.PostBuild;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class HistoryFailedPostBuild extends io.jenkins.plugins.agentManager.Conditions.HistoryFailed implements PostBuildCondition {
    @DataBoundConstructor
    public HistoryFailedPostBuild(int quantity) {
        super(quantity);
    }

    @Extension
    @Symbol("History")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Previous builds failed";
        }

        public ListBoxModel doFillHistoryConditionItems() {
            return new ListBoxModel(
                new ListBoxModel.Option("Keep failing"),
                new ListBoxModel.Option("Finish too fast")
            );
        }
    }
}
