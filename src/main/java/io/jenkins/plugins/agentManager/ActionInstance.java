package io.jenkins.plugins.agentManager;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class ActionInstance extends NodeProperty<Node> {
    /**
     * Override locations. Never null.
     */
    private Trigger trigger;
    private Action action;

    @DataBoundConstructor
    public ActionInstance(Trigger trigger, Action action) {
        System.out.println("ActionInstance");
        System.out.println(trigger);
        this.trigger = trigger;
        this.action = action;
    }

    public Trigger getTrigger() {
        // Could return currently configured/saved item here to initialized form with this data
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        // Could return currently configured/saved item here to initialized form with this data
        this.trigger = trigger;
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

    public DescriptorExtensionList<Action,Descriptor<Action>> getActionDescriptors() {
        return Jenkins.get().getDescriptorList(Action.class);
    }

    public static class TriggerDescriptor extends Descriptor<Trigger> {}

    // Symbol links this class to jelly's f:repeatable's 'var' attribute
    @Extension
    @Symbol("trigger")
    public static final class TriggerDescriptorImpl extends NodePropertyDescriptor {
        public ListBoxModel doFillTriggerItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("Before"),
                    new ListBoxModel.Option("Success"),
                    new ListBoxModel.Option("Failure")
            );
        }

        // Prevents it from being seen as a separate node property in node configuration page
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return false;
        }
    }

    public static abstract class Trigger implements ExtensionPoint, Describable<Trigger> {
        protected String name;
        protected Trigger(String name) { this.name = name; }

        public Descriptor<Trigger> getDescriptor() {
            return Jenkins.get().getDescriptor(getClass());
        }
    }


    public static class Failure extends Trigger {
        @DataBoundConstructor public Failure() {
            super("Failure");
        }

        @Extension
        public static final class DescriptorImpl extends TriggerDescriptor {}
    }

    public static class Before extends Trigger {
        @DataBoundConstructor public Before() {
            super("Before");
        }

        @Extension
        public static final class DescriptorImpl extends TriggerDescriptor {}
    }

    public static class Success extends Trigger {
        @DataBoundConstructor public Success() {
            super("Success");
        }

        @Extension
        public static final class DescriptorImpl extends TriggerDescriptor {}
    }

    public static class ActionDescriptor extends Descriptor<Action> {}

    // Symbol links this class to jelly's f:repeatable's 'var' attribute
    @Extension
    @Symbol("action")
    public static final class ActionDescriptorImpl extends NodePropertyDescriptor {
        public ListBoxModel doFillActionItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("Cleanup"),
                    new ListBoxModel.Option("Reboot"),
                    new ListBoxModel.Option("Run CustomScript")
            );
        }

        // Prevents it from being seen as a separate node property in node configuration page
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return false;
        }
    }


    public static abstract class Action implements ExtensionPoint, Describable<Action> {
        protected String name;
        protected Action(String name) { this.name = name; }

        public Descriptor<Action> getDescriptor() {
            return Jenkins.get().getDescriptor(getClass());
        }
    }

    public static class Cleanup extends Action {
        @DataBoundConstructor public Cleanup() {
            super("Cleanup");
        }

        @Extension
        public static final class DescriptorImpl extends ActionDescriptor {}
    }

    public static class Reboot extends Action {
        @DataBoundConstructor public Reboot() {
            super("Reboot");
        }

        @Extension
        public static final class DescriptorImpl extends ActionDescriptor {}
    }

    public static class CustomScript extends Action {
        private final String scriptText;
        // TODO support groovy and windows thing
        private final String language;
        @DataBoundConstructor public CustomScript(String scriptText) {
            super("CustomScript");
            System.out.println("CustomScript");
            System.out.println(scriptText);
            this.scriptText = scriptText;
            this.language = "BASH";
        }

        public String getScriptText() {
            return scriptText;
        }

        public String getLanguage() {
            return language;
        }
        @Extension
        public static final class DescriptorImpl extends ActionDescriptor { }
    }
}