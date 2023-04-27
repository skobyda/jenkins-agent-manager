package io.jenkins.plugins.agentManager.View;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

// TODO for thesis text:
// MAKE This interface instead of abstract class because when some classes will implement this, classes can extend multiple interface, but no multiple abstract classes
public interface DuringBuildAction extends ExtensionPoint, Describable<DuringBuildAction>, Action {
    default Descriptor<DuringBuildAction> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class DuringBuildActionDescriptor extends Descriptor<DuringBuildAction> {}
}
