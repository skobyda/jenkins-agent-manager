package io.jenkins.plugins.agentManager.Actions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class CustomScript extends io.jenkins.plugins.agentManager.Actions.CustomScript implements DuringBuildAction {
    @DataBoundConstructor public CustomScript(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("CustomScript")
    public static final class DescriptorImpl extends DuringBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom script";
        }
    }
}
