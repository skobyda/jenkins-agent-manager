package io.jenkins.plugins.agentManager.BuildEntries;

import hudson.model.AbstractDescribableImpl;
import io.jenkins.plugins.agentManager.Actions.Action;
import io.jenkins.plugins.agentManager.Conditions.Condition;

public abstract class BuildEntry extends AbstractDescribableImpl<BuildEntry> {
    public abstract Condition getCondition();
    public abstract Action getAction();

}
