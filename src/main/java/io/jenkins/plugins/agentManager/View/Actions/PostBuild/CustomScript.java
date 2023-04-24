package io.jenkins.plugins.agentManager.View.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class CustomScript extends io.jenkins.plugins.agentManager.View.Actions.CustomScript implements PostBuildAction {
    @DataBoundConstructor public CustomScript(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("CustomScript")
    public static final class DescriptorImpl extends PostBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom script";
        }
    }
}
