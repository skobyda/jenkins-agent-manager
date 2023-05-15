package io.jenkins.plugins.agentManager.Actions.DuringBuild;

import hudson.Extension;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class ShellScriptDuringBuild extends io.jenkins.plugins.agentManager.Actions.ShellScript implements DuringBuildAction {
    @DataBoundConstructor public ShellScriptDuringBuild(String scriptText) {
        super(scriptText);
    }

    @Extension
    @Symbol("ShellScript")
    public static final class DescriptorImpl extends DuringBuildActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom shell script";
        }
    }
}
