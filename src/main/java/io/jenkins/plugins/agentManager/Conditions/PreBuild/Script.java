package io.jenkins.plugins.agentManager.Conditions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.PreBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Script extends io.jenkins.plugins.agentManager.Conditions.Script implements PreBuildCondition {
    @DataBoundConstructor public Script(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("Script")
    public static final class DescriptorImpl extends PreBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Script output";
        }
    }
}
