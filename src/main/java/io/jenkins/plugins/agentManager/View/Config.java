package io.jenkins.plugins.agentManager.View;

import hudson.Extension;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Config extends NodeProperty<Node> {

    private final List<BuildAction> entries;

    @DataBoundConstructor public Config(List<BuildAction> entries) {
        this.entries = entries != null ? new ArrayList<BuildAction>(entries) : Collections.<BuildAction>emptyList();
        System.out.println(entries);
        System.out.println("Config");
        System.out.println(entries.get(0) instanceof  postFailedBuildAction);
    }

    public List<BuildAction> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Extension @Symbol("actionNodeProperty") public static final class DescriptorImpl extends NodePropertyDescriptor {
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return true;
        }

        // TODO do a form validation
        // TODO add a help

        @Override
        public String getDisplayName() {
            return "Configure build actions";
        }
    }
}
