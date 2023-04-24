package io.jenkins.plugins.agentManager.View.Conditions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PreBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Script extends io.jenkins.plugins.agentManager.View.Conditions.Script implements PreBuildCondition {
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
