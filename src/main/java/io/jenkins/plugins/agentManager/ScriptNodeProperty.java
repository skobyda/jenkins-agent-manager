package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ScriptNodeProperty extends NodeProperty<Node> {
    /**
     * Override locations. Never null.
     */
    private final List<ScriptInstance> scripts;

    @DataBoundConstructor
    public ScriptNodeProperty(List<ScriptInstance> scripts) {
        if (scripts == null) {
            scripts = new ArrayList<>();
        }
        this.scripts = scripts;
    }

    public ScriptNodeProperty(ScriptInstance... scripts) {
        this(Arrays.asList(scripts));
    }

    public List<ScriptInstance> getScripts() {
        return Collections.unmodifiableList(this.scripts);
    }

    @Extension @Symbol("script")
    public static class DescriptorImpl extends NodePropertyDescriptor {
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return true;
        }

        // TODO do a form validation
        // TODO add a help

        @Override
        public String getDisplayName() {
            return "Post-build script";
        }
    }

    public static final class ScriptInstance {
        private final String script;
        private final String language;
        private final String trigger;

        @DataBoundConstructor
        public ScriptInstance(String script, String language, String trigger) {
            this.script = script;
            this.language = language;
            this.trigger = trigger;
        }

        public String getScript() {
            return script;
        }

        public String getLanguage() {
            return language;
        }

        public String getTrigger() {
            return trigger;
        }
    }

}