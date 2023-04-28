package io.jenkins.plugins.agentManager.Conditions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public interface PreBuildCondition extends ExtensionPoint, Describable<PreBuildCondition>, Condition {
    default Descriptor<PreBuildCondition> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class PreBuildConditionDescriptor extends Descriptor<PreBuildCondition> {}
}
