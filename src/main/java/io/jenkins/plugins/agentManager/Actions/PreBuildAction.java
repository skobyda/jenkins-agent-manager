package io.jenkins.plugins.agentManager.Actions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public interface PreBuildAction extends ExtensionPoint, Describable<PreBuildAction>, Action {
    /** Gets a descriptor only specific for the actions run before the build
     *
     * @return descriptor
     */
    default Descriptor<PreBuildAction> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class PreBuildActionDescriptor extends Descriptor<PreBuildAction> {}
}
