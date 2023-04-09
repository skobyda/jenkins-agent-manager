package io.jenkins.plugins.agentManager.View;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class postFailedBuildAction extends ActionInstance {
    /**
     * Override locations. Never null.
     */
    private Condition condition;
    private Action action;

    @DataBoundConstructor
    public postFailedBuildAction(Condition condition, Action action) {
        System.out.println("ActionInstance");
        this.condition = condition;
        this.action = action;
    }

    @Extension
    @Symbol("actionInstance")
    public static class DescriptorImpl extends Descriptor<ActionInstance> {
        @Override public String getDisplayName() {
            return "Action run after failed build";
        }

        public ListBoxModel doFillActionItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("Cleanup"),
                    new ListBoxModel.Option("Reboot"),
                    new ListBoxModel.Option("SetOffline"),
                    new ListBoxModel.Option("CustomScript")
            );
        }

        public ListBoxModel doFillConditionItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("Everytime"),
                    new ListBoxModel.Option("Duration"),
                    new ListBoxModel.Option("Script"),
                    new ListBoxModel.Option("History")
            );
        }
    }

    public Condition getCondition() {
        // Could return currently configured/saved item here to initialized form with this data
        return condition;
    }

    public void setCondition(Condition condition) {
        // Could return currently configured/saved item here to initialized form with this data
        this.condition = condition;
    }

    public Action getAction() {
        // Could return currently configured/saved item here to initialized form with this data
        return action;
    }

    public void setAction(Action action) {
        // Could return currently configured/saved item here to initialized form with this data
        this.action = action;
    }

    public DescriptorExtensionList<Condition,Descriptor<Condition>> getConditionDescriptors() {
        return Jenkins.get().getDescriptorList(Condition.class);
    }

    public DescriptorExtensionList<Action,Descriptor<Action>> getActionDescriptors() {
        return Jenkins.get().getDescriptorList(Action.class);
    }
}
