package io.jenkins.plugins.agentManager.View.ActionInstance;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.Action;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class GracefulReboot extends Action {
    @DataBoundConstructor public GracefulReboot() {
        super("GracefulReboot");
    }

    @Extension
    @Symbol("GracefulReboot")
    public static final class DescriptorImpl extends ActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Gracefully reboot agent";
        }
    }
}
