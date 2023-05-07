package io.jenkins.plugins.agentManager.Actions.PostBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class GroovyScript extends io.jenkins.plugins.agentManager.Actions.GroovyScript implements PostBuildAction {
    @DataBoundConstructor public GroovyScript(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("GroovyScript")
    public static final class DescriptorImpl extends PostBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom groovy script";
        }
    }
}
