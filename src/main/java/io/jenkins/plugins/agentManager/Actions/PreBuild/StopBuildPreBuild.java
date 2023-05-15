package io.jenkins.plugins.agentManager.Actions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PreBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class StopBuildPreBuild extends io.jenkins.plugins.agentManager.Actions.StopBuild implements PreBuildAction {
    @DataBoundConstructor public StopBuildPreBuild() {
        super();
    }


    @Extension
    @Symbol("StopBuild")
    public static final class DescriptorImpl extends PreBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Stop build";
        }
    }
}

