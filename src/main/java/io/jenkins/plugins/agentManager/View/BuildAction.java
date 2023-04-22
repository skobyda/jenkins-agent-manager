package io.jenkins.plugins.agentManager.View;

import hudson.model.AbstractDescribableImpl;

public abstract class BuildAction extends AbstractDescribableImpl<BuildAction> {
    public abstract Condition getCondition();
    public abstract void setCondition(Condition condition);
    public abstract Action getAction() ;
    public abstract void setAction(Action action);
}
