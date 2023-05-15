package io.jenkins.plugins.agentManager.Conditions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public interface PostBuildCondition extends ExtensionPoint, Describable<PostBuildCondition>, Condition {
    /** Gets a descriptor only specific for the conditions of actions run after the build
     *
     * @return descriptor
     */
    default Descriptor<PostBuildCondition> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class PostBuildConditionDescriptor extends Descriptor<PostBuildCondition> {}
}
