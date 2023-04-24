package io.jenkins.plugins.agentManager.View.Conditions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Everytime extends io.jenkins.plugins.agentManager.View.Conditions.Everytime implements PostBuildCondition {
    @DataBoundConstructor
    public Everytime() {
        super();
    }

    @Extension
    @Symbol("Everytime")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Everytime";
        }
    }
}
