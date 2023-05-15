package io.jenkins.plugins.agentManager.Actions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public interface PostBuildAction extends ExtensionPoint, Describable<PostBuildAction>, Action {
    default Descriptor<PostBuildAction> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class PostBuildActionDescriptor extends Descriptor<PostBuildAction> {}
}
