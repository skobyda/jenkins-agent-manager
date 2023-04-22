package io.jenkins.plugins.agentManager.View.ActionInstance;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.Action;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class StopBuild extends Action {
    @DataBoundConstructor public StopBuild() {
        super("StopBuild");
    }

    @Extension
    @Symbol("StopBuild")
    public static final class DescriptorImpl extends ActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Stop build";
        }
    }
}

