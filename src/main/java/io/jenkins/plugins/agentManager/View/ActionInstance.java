package io.jenkins.plugins.agentManager.View;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class ActionInstance extends NodeProperty<Node> {
    /**
     * Override locations. Never null.
     */
    private Trigger trigger;
    private Condition condition;
    private Action action;

    @DataBoundConstructor
    public ActionInstance(Trigger trigger, Condition condition, Action action) {
        System.out.println("ActionInstance");
        System.out.println(trigger);
        this.trigger = trigger;
        this.condition = condition;
        this.action = action;
    }

    @Extension
    @Symbol("actionInstance")
    public static class ActionInstanceDescriptor extends NodePropertyDescriptor {
        public ListBoxModel doFillTriggerItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("Before"),
                    new ListBoxModel.Option("Success"),
                    new ListBoxModel.Option("Failure")
            );
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

         // Prevents it from being seen as a separate node property in node configuration page
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return false;
        }
    }

    public Trigger getTrigger() {
        // Could return currently configured/saved item here to initialized form with this data
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        // Could return currently configured/saved item here to initialized form with this data
        this.trigger = trigger;
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

    public DescriptorExtensionList<Trigger,Descriptor<Trigger>> getTriggerDescriptors() {
        return Jenkins.get().getDescriptorList(Trigger.class);
    }

    public DescriptorExtensionList<Condition,Descriptor<Condition>> getConditionDescriptors() {
        return Jenkins.get().getDescriptorList(Condition.class);
    }

    public DescriptorExtensionList<Action,Descriptor<Action>> getActionDescriptors() {
        return Jenkins.get().getDescriptorList(Action.class);
    }
}
