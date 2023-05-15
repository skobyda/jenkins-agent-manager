package io.jenkins.plugins.agentManager.Conditions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class ScriptPostBuild extends io.jenkins.plugins.agentManager.Conditions.Script implements PostBuildCondition {
    @DataBoundConstructor public ScriptPostBuild(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("Script")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Script output";
        }
    }
}
