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

    private final List<ActionInstance> entries;

    @DataBoundConstructor public Config(List<ActionInstance> entries) {
        this.entries = entries != null ? new ArrayList<ActionInstance>(entries) : Collections.<ActionInstance>emptyList();
        System.out.println(entries);
        System.out.println("Config");
        System.out.println(entries.get(0) instanceof  postFailedBuildAction);
    }

    public List<ActionInstance> getEntries() {
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
