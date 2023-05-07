package io.jenkins.plugins.agentManager.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class ShellScript extends io.jenkins.plugins.agentManager.Actions.ShellScript implements PostBuildAction {
    @DataBoundConstructor public ShellScript(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("ShellScript")
    public static final class DescriptorImpl extends PostBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom shell script";
        }
    }
}
