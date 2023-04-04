package io.jenkins.plugins.agentManager.View;

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

        public String getName() {
            return name;
        }

        public Descriptor<Trigger> getDescriptor() {
            return Jenkins.get().getDescriptor(getClass());
        }

        public static class Failure extends Trigger {
            @DataBoundConstructor public Failure() {
                super("Failure");
            }

            @Extension
            @Symbol("Failure")
            public static final class DescriptorImpl extends TriggerDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "After failed build";
                }
            }
        }

        public static class Before extends Trigger {
            @DataBoundConstructor public Before() {
                super("Before");
            }

            @Extension
            @Symbol("Build")
            public static final class DescriptorImpl extends TriggerDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Before build";
                }
            }
        }

        public static class Success extends Trigger {
            @DataBoundConstructor public Success() {
                super("Success");
            }

            @Extension
            @Symbol("Success")
            public static final class DescriptorImpl extends TriggerDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "After successful build";
                }
            }
        }
    }

    public static class ConditionDescriptor extends Descriptor<Condition> {}

    // Symbol links this class to jelly's f:repeatable's 'var' attribute
    @Extension
    @Symbol("condition")
    public static final class ConditionDescriptorImpl extends NodePropertyDescriptor {
        public ListBoxModel doFillConditionItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("Everytime"),
                    new ListBoxModel.Option("Duration"),
                    new ListBoxModel.Option("Script")
            );
        }

        // Prevents it from being seen as a separate node property in node configuration page
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return false;
        }
    }

    public static abstract class Condition implements ExtensionPoint, Describable<Condition> {
        protected String name;
        protected Condition(String name) { this.name = name; }

        public String getName() {
            return name;
        }

        public Descriptor<Condition> getDescriptor() {
            return Jenkins.get().getDescriptor(getClass());
        }

        public static class Everytime extends Condition {
            @DataBoundConstructor public Everytime() {
                super("Everytime");
            }

            @Extension
            @Symbol("Everytime")
            public static final class DescriptorImpl extends ConditionDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Everytime";
                }
            }
        }

        public static class Duration extends Condition {
            private final String durationCondition;
            private final long time;
            private final String unit;

            public String getDurationCondition() {
                return durationCondition;
            }

            public long getTime() {
                return time;
            }

            public String getUnit() {
                return unit;
            }

            @DataBoundConstructor public Duration(String durationCondition, long time, String unit) {
                super("Duration");
                System.out.println("Duration");
                System.out.println(durationCondition);
                System.out.println(time);
                System.out.println(unit);
                this.durationCondition = durationCondition;
                this.time = time;
                this.unit = unit;
            }

            @Extension
            @Symbol("Duration")
            public static final class DescriptorImpl extends ConditionDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Based on build duration";
                }

                public ListBoxModel doFillUnitItems() {
                    return new ListBoxModel(
                            new ListBoxModel.Option("milliseconds"),
                            new ListBoxModel.Option("seconds"),
                            new ListBoxModel.Option("minutes")
                    );
                }

                public ListBoxModel doFillDurationConditionItems() {
                    return new ListBoxModel(
                            new ListBoxModel.Option("Build took more than"),
                            new ListBoxModel.Option("Build took less than")
                    );
                }
            }
        }

        public static class Script extends Condition {
            private final String scriptText;
            // TODO support groovy and windows thing
            private final String language;
            @DataBoundConstructor public Script(String scriptText) {
                super("Script");
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
            @Symbol("Script")
            public static final class DescriptorImpl extends ConditionDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Script output";
                }
            }
        }
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
                    new ListBoxModel.Option("SetOffline"),
                    new ListBoxModel.Option("CustomScript")
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

        public String getName() {
            return name;
        }

        public Descriptor<Action> getDescriptor() {
            return Jenkins.get().getDescriptor(getClass());
        }

        public static class Cleanup extends Action {
            @DataBoundConstructor public Cleanup() {
                super("Cleanup");
            }

            @Extension
            @Symbol("Cleanup")
            public static final class DescriptorImpl extends ActionDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Cleanup workspace";
                }
            }
        }

        public static class Reboot extends Action {
            @DataBoundConstructor public Reboot() {
                super("Reboot");
            }

            @Extension
            @Symbol("Reboot")
            public static final class DescriptorImpl extends ActionDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Reboot agent";
                }
            }
        }

        public static class SetOffline extends Action {
            @DataBoundConstructor public SetOffline() {
                super("SetOffline");
            }

            @Extension
            @Symbol("SetOffline")
            public static final class DescriptorImpl extends ActionDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Take agent offline";
                }
            }
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
            @Symbol("CustomScript")
            public static final class DescriptorImpl extends ActionDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Run custom script";
                }
            }
        }
    }
}
