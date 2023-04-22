package io.jenkins.plugins.agentManager.View.ActionInstance;

import hudson.Extension;
import io.jenkins.plugins.agentManager.View.Action;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class CustomScript extends Action {
    private final String scriptText;
    // TODO support groovy and windows thing
    private final String language;
    @DataBoundConstructor public CustomScript(String scriptText) {
        super("CustomScript");
        System.out.println("CustomScript");
        System.out.println(scriptText);
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
    @Symbol("CustomScript")
    public static final class DescriptorImpl extends ActionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Run custom script";
        }
    }
}
