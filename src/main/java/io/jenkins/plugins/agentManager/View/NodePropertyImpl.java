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

public final class NodePropertyImpl extends NodeProperty<Node> {

    private final List<BuildEntry> entries;

    @DataBoundConstructor public NodePropertyImpl(List<BuildEntry> entries) {
        this.entries = entries != null ? new ArrayList<BuildEntry>(entries) : Collections.<BuildEntry>emptyList();
        System.out.println(entries);
        System.out.println("Config");
        System.out.println(entries.get(0) instanceof PostBuildEntry);
    }

    public List<BuildEntry> getEntries() {
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
