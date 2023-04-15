package io.jenkins.plugins.agentManager.View;

import hudson.Extension;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public abstract class Action implements ExtensionPoint, Describable<Action> {
    protected String name;
    protected Action(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public Descriptor<Action> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    public static class ActionDescriptor extends Descriptor<Action> {}

    public static class Cleanup extends Action {
        @DataBoundConstructor
        public Cleanup() {
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

    public static class StopBuild extends Action {
        @DataBoundConstructor public StopBuild() {
            super("StopBuild");
        }

        @Extension
        @Symbol("StopBuild")
        public static final class DescriptorImpl extends ActionDescriptor {
            @NonNull
            @Override
            public String getDisplayName() {
                return "Stop build";
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

    public static class GracefulReboot extends Action {
        @DataBoundConstructor public GracefulReboot() {
            super("GracefulReboot");
        }

        @Extension
        @Symbol("GracefulReboot")
        public static final class DescriptorImpl extends ActionDescriptor {
            @NonNull
            @Override
            public String getDisplayName() {
                return "Gracefully reboot agent";
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
