package io.jenkins.plugins.agentManager.View.ActionInstance;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.Action;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Cleanup extends Action {
    @DataBoundConstructor
    public Cleanup() {
        super("Cleanup");
    }

    @Extension
    @Symbol("Cleanup")
    public static final class DescriptorImpl extends ActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Cleanup workspace";
        }
    }
}
