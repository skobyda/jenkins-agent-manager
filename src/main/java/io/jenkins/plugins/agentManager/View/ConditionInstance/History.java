package io.jenkins.plugins.agentManager.View.ConditionInstance;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.View.Action;
import io.jenkins.plugins.agentManager.View.Condition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class History extends Condition {
    private final String historyCondition;
    private final int quantity;
    private final Action action;

    public String getHistoryCondition() {
        return historyCondition;
    }

    public int getQuantity() {
        return quantity;
    }

    public Action getAction() {
        return action;
    }

    @DataBoundConstructor
    public History(String historyCondition, int quantity, Action action) {
        super("History");
        this.historyCondition = historyCondition;
        this.quantity = quantity;
        this.action = action;
    }

    @Extension
    @Symbol("History")
    public static final class DescriptorImpl extends ConditionDescriptor {
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
