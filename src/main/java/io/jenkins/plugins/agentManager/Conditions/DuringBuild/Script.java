package io.jenkins.plugins.agentManager.Conditions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.DuringBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Script extends io.jenkins.plugins.agentManager.Conditions.Script implements DuringBuildCondition {
    @DataBoundConstructor public Script(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("Script")
    public static final class DescriptorImpl extends DuringBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Script output";
        }
    }
}
