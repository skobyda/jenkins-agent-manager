package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.model.Node;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.List;

@Extension
public class ActionNodeProperty extends NodeProperty<Node> {
    private List<ActionInstance> actionInstances;

    public ActionNodeProperty() {
        // noop
    }

    @DataBoundConstructor
    public ActionNodeProperty(List<ActionInstance> actionInstances) {
        this.actionInstances = actionInstances;
        System.out.println("ActionNodeProperty");
        System.out.println(actionInstances.get(0).getTrigger().name);
        System.out.println(actionInstances.get(0).getTrigger().getClass());
    }

    public List<ActionInstance> getActionInstances() {
        return actionInstances;
    }

    @Extension @Symbol("actionNodeProperty")
    public static class DescriptorImpl extends NodePropertyDescriptor {
        @Override
        public boolean isApplicable(Class<? extends Node> nodeType) {
            return true;
        }

        // TODO do a form validation
        // TODO add a help

        @Override
        public String getDisplayName() {
            return "Action management";
        }
    }
}
