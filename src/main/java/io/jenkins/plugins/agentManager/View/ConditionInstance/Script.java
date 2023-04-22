package io.jenkins.plugins.agentManager.View.ConditionInstance;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.Condition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Script extends Condition {
    private final String scriptText;
    // TODO support groovy and windows thing
    private final String language;
    @DataBoundConstructor public Script(String scriptText) {
    super("Script");
    this.scriptText = scriptText;
    this.language = "BASH";
    }

    public String getScriptText() {
        return scriptText;
    }

    public String getLanguage() {
        return language;
    }

    @Extension
    @Symbol("Script")
    public static final class DescriptorImpl extends ConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Script output";
        }
    }
}
