package io.jenkins.plugins.agentManager.View;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

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


    /* public abstract class Action implements ExtensionPoint, Describable<Action>  {
        protected String name;
        protected Action(String name) { this.name = name; }

        public String getName() {
            return name;
        }

        public Descriptor<Action> getDescriptor() {
            return Jenkins.get().getDescriptor(getClass());
        }

        public class ActionDescriptor extends Descriptor<Action> {
            public ListBoxModel doFillActionItems() {
                return new ListBoxModel(
                    new ListBoxModel.Option("CustomScript")
                );
            }
        }

        public class CustomScript extends Action {
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

            @Symbol("CustomScript")
            public final class DescriptorImpl extends ActionDescriptor {
                @NonNull
                @Override
                public String getDisplayName() {
                    return "Run custom script";
                }
            }
        }
    }
    */
}

