package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;


public class ScriptNodeProperty extends NodeProperty<Node> {
    private final String script;

    @DataBoundConstructor
    public ScriptNodeProperty(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    @Extension
    public static class DescriptorImpl extends NodePropertyDescriptor {
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return true;
        }

        @Override
        public NodeProperty<?> newInstance(
                final StaplerRequest req,
                final JSONObject formData
        ) throws FormException {

            final String script = formData.getString("script");

            if (script == null && script.isEmpty() && script.isEmpty())
                return null;

            return new ScriptNodeProperty(script);
        }

        @Override
        public String getDisplayName() {
            return "Post-build script";
        }
    }
}
