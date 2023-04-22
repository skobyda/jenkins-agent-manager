package io.jenkins.plugins.agentManager.View;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public abstract class Action implements ExtensionPoint, Describable<Action> {
    protected String name;
    protected Action(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public Descriptor<Action> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    public static class ActionDescriptor extends Descriptor<Action> {}

}
