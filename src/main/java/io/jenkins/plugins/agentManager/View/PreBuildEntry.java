package io.jenkins.plugins.agentManager.View;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class PreBuildEntry extends BuildEntry {
    /**
     * Override locations. Never null.
     */
    private PreBuildCondition preBuildCondition;
    private PreBuildAction preBuildAction;

    @DataBoundConstructor
    public PreBuildEntry(PreBuildCondition preBuildCondition, PreBuildAction preBuildAction) {
        System.out.println("PreBuildActionInstance");
        this.preBuildCondition = preBuildCondition;
        this.preBuildAction = preBuildAction;
    }

    @Extension
    @Symbol("preBuildEntry")
    public static class DescriptorImpl extends Descriptor<BuildEntry> {
        @Override public String getDisplayName() {
            return "Action run before build";
        }
    }

    public PreBuildCondition getPreBuildCondition() {
        // Could return currently configured/saved item here to initialized form with this data
        return preBuildCondition;
    }

    public void setPreBuildCondition(PreBuildCondition preBuildCondition) {
        // Could return currently configured/saved item here to initialized form with this data
        this.preBuildCondition = preBuildCondition;
    }

    public PreBuildAction getPreBuildAction() {
        // Could return currently configured/saved item here to initialized form with this data
        return preBuildAction;
    }

    public void setPreBuildAction(PreBuildAction action) {
        // Could return currently configured/saved item here to initialized form with this data
        this.preBuildAction = action;
    }

    public Condition getCondition() {
        return this.preBuildCondition;
    }

    public Action getAction() {
        return this.preBuildAction;
    }

    public DescriptorExtensionList<PreBuildCondition,Descriptor<PreBuildCondition>> getPreBuildConditionDescriptors() {
        return Jenkins.get().getDescriptorList(PreBuildCondition.class);
    }

    public DescriptorExtensionList<PreBuildAction,Descriptor<PreBuildAction>> getPreBuildActionDescriptors() {
        return Jenkins.get().getDescriptorList(PreBuildAction.class);
    }
}
