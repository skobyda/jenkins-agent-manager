package io.jenkins.plugins.agentManager.View.Actions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PreBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class CustomScript extends io.jenkins.plugins.agentManager.View.Actions.CustomScript implements PreBuildAction {
    @DataBoundConstructor public CustomScript(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("CustomScript")
    public static final class DescriptorImpl extends PreBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom script";
        }
    }
}
