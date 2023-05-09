package io.jenkins.plugins.agentManager.Conditions.PreBuild;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.Conditions.PreBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class HistoryFailed extends io.jenkins.plugins.agentManager.Conditions.HistoryFailed implements PreBuildCondition {
    @DataBoundConstructor
    public HistoryFailed(int quantity) {
        super(quantity);
    }

    @Extension
    @Symbol("History")
    public static final class DescriptorImpl extends PreBuildConditionDescriptor {
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
