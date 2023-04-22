package io.jenkins.plugins.agentManager.View.ActionInstance;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.Action;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class SetOffline extends Action {
    @DataBoundConstructor public SetOffline() {
        super("SetOffline");
    }

    @Extension
    @Symbol("SetOffline")
    public static final class DescriptorImpl extends ActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Take agent offline";
        }
    }
}
