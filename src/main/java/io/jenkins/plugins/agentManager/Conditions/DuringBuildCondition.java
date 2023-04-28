package io.jenkins.plugins.agentManager.Conditions;

import hudson.ExtensionPoint;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public interface DuringBuildCondition extends ExtensionPoint, Describable<DuringBuildCondition>, Condition {
    default Descriptor<DuringBuildCondition> getDescriptor() {
        return Jenkins.get().getDescriptor(getClass());
    }

    class DuringBuildConditionDescriptor extends Descriptor<DuringBuildCondition> {}
}
