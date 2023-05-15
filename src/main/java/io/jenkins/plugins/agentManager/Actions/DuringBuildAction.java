package io.jenkins.plugins.agentManager.Actions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public interface DuringBuildAction extends ExtensionPoint, Describable<DuringBuildAction>, Action {
    default Descriptor<DuringBuildAction> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class DuringBuildActionDescriptor extends Descriptor<DuringBuildAction> {}
}
