package io.jenkins.plugins.agentManager.View;

import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public abstract class Condition implements ExtensionPoint, Describable<Condition> {
    protected String name;
    protected Condition(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public Descriptor<Condition> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    public static class ConditionDescriptor extends Descriptor<Condition> {}

    public static class Everytime extends Condition {
        @DataBoundConstructor
        public Everytime() {
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

        @DataBoundConstructor
        public Duration(String durationCondition, long time, String unit) {
            super("Duration");
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

    public static class History extends Condition {
        private final String historyCondition;
        private final int quantity;

        public String getHistoryCondition() {
            return historyCondition;
        }

        public int getQuantity() {
            return quantity;
        }

        @DataBoundConstructor
        public History(String historyCondition, int quantity) {
            super("History");
            this.historyCondition = historyCondition;
            this.quantity = quantity;
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
                        new ListBoxModel.Option("Builds keep failing"),
                        new ListBoxModel.Option("Builds end too fast")
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

