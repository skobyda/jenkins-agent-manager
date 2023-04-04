package io.jenkins.plugins.agentManager.View;

import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Node;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public abstract class Trigger implements ExtensionPoint, Describable<Trigger> {
    protected String name;
    protected Trigger(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public Descriptor<Trigger> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    public static class TriggerDescriptor extends Descriptor<Trigger> {}

    public static class Failure extends Trigger {
        @DataBoundConstructor
        public Failure() {
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
