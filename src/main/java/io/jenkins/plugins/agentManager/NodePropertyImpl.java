package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import io.jenkins.plugins.agentManager.BuildEntries.BuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NodePropertyImpl extends NodeProperty<Node> {

    private final List<BuildEntry> entries;

    @DataBoundConstructor public NodePropertyImpl(List<BuildEntry> entries) {
        this.entries = entries != null ? new ArrayList<BuildEntry>(entries) : Collections.<BuildEntry>emptyList();
    }

    public List<BuildEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Extension @Symbol("actionNodeProperty") public static final class DescriptorImpl extends NodePropertyDescriptor {
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Configure build actions";
        }
    }
}
