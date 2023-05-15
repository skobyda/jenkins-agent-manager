package io.jenkins.plugins.agentManager.Conditions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class ResultPostBuild extends io.jenkins.plugins.agentManager.Conditions.Result implements PostBuildCondition {
    @DataBoundConstructor
    public ResultPostBuild(Boolean success, Boolean failure, Boolean aborted, Boolean unstable, Boolean notBuilt) {
        super(success, failure, aborted, unstable, notBuilt);
    }

    @Extension
    @Symbol("Result")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Based on build result";
        }
    }
}
