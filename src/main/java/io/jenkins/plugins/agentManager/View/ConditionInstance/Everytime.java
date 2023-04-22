package io.jenkins.plugins.agentManager.View.ConditionInstance;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.Condition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Everytime extends Condition {
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
