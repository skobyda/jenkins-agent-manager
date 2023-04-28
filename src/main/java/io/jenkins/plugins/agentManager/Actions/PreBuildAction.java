package io.jenkins.plugins.agentManager.Actions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

// TODO for thesis text:
// MAKE This interface instead of abstract class because when some classes will implement this, classes can extend multiple interface, but no multiple abstract classes
public interface PreBuildAction extends ExtensionPoint, Describable<PreBuildAction>, Action {
    default Descriptor<PreBuildAction> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class PreBuildActionDescriptor extends Descriptor<PreBuildAction> {}
}
