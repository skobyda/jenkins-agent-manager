package io.jenkins.plugins.agentManager.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class StopBuildPostBuild extends io.jenkins.plugins.agentManager.Actions.StopBuild implements PostBuildAction {
    @DataBoundConstructor public StopBuildPostBuild() {
        super();
    }

    @Override
    public String getName() {
        return "StopBuild";
    }

    @Extension
    @Symbol("StopBuild")
    public static final class DescriptorImpl extends PostBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Stop build";
        }
    }
}

