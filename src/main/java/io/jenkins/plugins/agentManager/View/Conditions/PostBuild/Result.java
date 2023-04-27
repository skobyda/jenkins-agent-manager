package io.jenkins.plugins.agentManager.View.Conditions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Result extends io.jenkins.plugins.agentManager.View.Conditions.Result implements PostBuildCondition {
    @DataBoundConstructor
    public Result(Boolean success, Boolean failure, Boolean aborted, Boolean unstable, Boolean notBuilt) {
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
