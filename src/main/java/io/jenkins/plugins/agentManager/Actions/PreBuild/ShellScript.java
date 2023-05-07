package io.jenkins.plugins.agentManager.Actions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PreBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class ShellScript extends io.jenkins.plugins.agentManager.Actions.ShellScript implements PreBuildAction {
    @DataBoundConstructor public ShellScript(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("CustomScript")
    public static final class DescriptorImpl extends PreBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom shell script";
        }
    }
}
