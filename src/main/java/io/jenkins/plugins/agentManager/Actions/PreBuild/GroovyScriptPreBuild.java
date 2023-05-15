package io.jenkins.plugins.agentManager.Actions.PreBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.PreBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class GroovyScriptPreBuild extends io.jenkins.plugins.agentManager.Actions.GroovyScript implements PreBuildAction {
    @DataBoundConstructor public GroovyScriptPreBuild(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("Groovy")
    public static final class DescriptorImpl extends PreBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom groovy script";
        }
    }
}
