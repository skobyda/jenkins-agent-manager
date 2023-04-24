package io.jenkins.plugins.agentManager.View.Conditions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PreBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Everytime extends io.jenkins.plugins.agentManager.View.Conditions.Everytime implements PreBuildCondition {
    @DataBoundConstructor
    public Everytime() {
        super();
    }

    @Extension
    @Symbol("Everytime")
    public static final class DescriptorImpl extends PreBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Everytime";
        }
    }
}
