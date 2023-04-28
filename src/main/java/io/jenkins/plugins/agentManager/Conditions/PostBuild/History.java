package io.jenkins.plugins.agentManager.Conditions.PostBuild;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class History extends io.jenkins.plugins.agentManager.Conditions.History implements PostBuildCondition {
    @DataBoundConstructor
    public History(String historyCondition, int quantity) {
        super(historyCondition, quantity);
    }

    @Extension
    @Symbol("History")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Based on build history";
        }

        public ListBoxModel doFillHistoryConditionItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("Keep failing"),
                    new ListBoxModel.Option("Finish too fast")
                    );
        }
    }
}
