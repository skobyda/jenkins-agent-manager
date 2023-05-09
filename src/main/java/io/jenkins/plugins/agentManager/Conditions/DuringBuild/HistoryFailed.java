package io.jenkins.plugins.agentManager.Conditions.DuringBuild;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.Conditions.DuringBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class HistoryFailed extends io.jenkins.plugins.agentManager.Conditions.HistoryFailed implements DuringBuildCondition {
    @DataBoundConstructor
    public HistoryFailed(int quantity) {
        super(quantity);
    }

    @Extension
    @Symbol("History")
    public static final class DescriptorImpl extends DuringBuildConditionDescriptor {
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
