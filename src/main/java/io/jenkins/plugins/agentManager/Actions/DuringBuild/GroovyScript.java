package io.jenkins.plugins.agentManager.Actions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class GroovyScript extends io.jenkins.plugins.agentManager.Actions.GroovyScript implements DuringBuildAction {
    @DataBoundConstructor public GroovyScript(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("GroovyScript")
    public static final class DescriptorImpl extends DuringBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom groovy script";
        }
    }
}
