package io.jenkins.plugins.agentManager.View.Actions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PreBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class StopBuild extends io.jenkins.plugins.agentManager.View.Actions.StopBuild implements PreBuildAction {
    @DataBoundConstructor public StopBuild() {
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

