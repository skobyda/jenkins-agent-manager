package io.jenkins.plugins.agentManager.Actions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class StopBuild extends io.jenkins.plugins.agentManager.Actions.StopBuild implements DuringBuildAction {
    @DataBoundConstructor public StopBuild() {
        super();
    }

    @Override
    public String getName() {
        return "StopBuild";
    }

    @Extension
    @Symbol("StopBuild")
    public static final class DescriptorImpl extends DuringBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Stop build";
        }
    }
}

