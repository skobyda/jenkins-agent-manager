package io.jenkins.plugins.agentManager.Actions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

// TODO for thesis text:
// MAKE This interface instead of abstract class because when some classes will implement this, classes can extend multiple interface, but no multiple abstract classes
public interface PostBuildAction extends ExtensionPoint, Describable<PostBuildAction>, Action {
    default Descriptor<PostBuildAction> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class PostBuildActionDescriptor extends Descriptor<PostBuildAction> {}
}
