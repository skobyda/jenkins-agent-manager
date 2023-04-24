package io.jenkins.plugins.agentManager.View;

import hudson.model.AbstractDescribableImpl;

public abstract class BuildEntry extends AbstractDescribableImpl<BuildEntry> {
    public abstract Condition getCondition();
    public abstract Action getAction();
}
