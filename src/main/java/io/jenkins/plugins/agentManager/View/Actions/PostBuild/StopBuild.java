package io.jenkins.plugins.agentManager.View.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class StopBuild extends io.jenkins.plugins.agentManager.View.Actions.StopBuild implements PostBuildAction {
    @DataBoundConstructor public StopBuild() {
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

