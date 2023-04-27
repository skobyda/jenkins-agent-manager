package io.jenkins.plugins.agentManager.View;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class DuringBuildEntry extends BuildEntry {
    /**
     * Override locations. Never null.
     */
    private DuringBuildCondition duringBuildCondition;
    private DuringBuildAction duringBuildAction;

    @DataBoundConstructor
    public DuringBuildEntry(DuringBuildCondition duringBuildCondition, DuringBuildAction duringBuildAction) {
        System.out.println("DuringBuildActionInstance");
        this.duringBuildCondition = duringBuildCondition;
        this.duringBuildAction = duringBuildAction;
    }

    @Extension
    @Symbol("duringBuildActionInstance")
    public static class DescriptorImpl extends Descriptor<BuildEntry> {
        @Override public String getDisplayName() {
            return "Action run during build";
        }
    }

    public DuringBuildCondition getDuringBuildCondition() {
        // Could return currently configured/saved item here to initialized form with this data
        return duringBuildCondition;
    }

    public void setDuringBuildCondition(DuringBuildCondition duringBuildCondition) {
        // Could return currently configured/saved item here to initialized form with this data
        this.duringBuildCondition = duringBuildCondition;
    }

    public DuringBuildAction getDuringBuildAction() {
        // Could return currently configured/saved item here to initialized form with this data
        return duringBuildAction;
    }

    public void setDuringBuildAction(DuringBuildAction duringBuildAction) {
        // Could return currently configured/saved item here to initialized form with this data
        this.duringBuildAction = duringBuildAction;
    }

    public Condition getCondition() {
        return this.duringBuildCondition;
    }

    public Action getAction() {
        return this.duringBuildAction;
    }

    public DescriptorExtensionList<DuringBuildCondition,Descriptor<DuringBuildCondition>> getDuringBuildConditionDescriptors() {
        return Jenkins.get().getDescriptorList(DuringBuildCondition.class);
    }

    public DescriptorExtensionList<DuringBuildAction,Descriptor<DuringBuildAction>> getDuringBuildActionDescriptors() {
        return Jenkins.get().getDescriptorList(DuringBuildAction.class);
    }
}
