package io.jenkins.plugins.agentManager.Actions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public interface DuringBuildAction extends ExtensionPoint, Describable<DuringBuildAction>, Action {
    /** Gets a descriptor only specific for the actions run during the build
     *
     * @return descriptor
     */
    default Descriptor<DuringBuildAction> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class DuringBuildActionDescriptor extends Descriptor<DuringBuildAction> {}
}
