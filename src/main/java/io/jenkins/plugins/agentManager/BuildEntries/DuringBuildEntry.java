package io.jenkins.plugins.agentManager.BuildEntries;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.Actions.Action;
import io.jenkins.plugins.agentManager.Conditions.Condition;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import io.jenkins.plugins.agentManager.Conditions.DuringBuildCondition;
import io.jenkins.plugins.agentManager.Utils.Time;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class DuringBuildEntry extends BuildEntry {
    /**
     * Override locations. Never null.
     */
    private DuringBuildCondition duringBuildCondition;
    private DuringBuildAction duringBuildAction;
    private Long time;
    private String unit;
    public Boolean loop;
    private Boolean actionPerformed;

    @DataBoundConstructor
    public DuringBuildEntry(Long time, String unit, boolean loop, DuringBuildCondition duringBuildCondition, DuringBuildAction duringBuildAction) {
        System.out.println("DurinBuildEntry");
        this.duringBuildCondition = duringBuildCondition;
        this.duringBuildAction = duringBuildAction;
        this.time =  time;
        this.unit = unit;
        this.loop = loop;
        this.actionPerformed = false;
        System.out.println(loop);
    }

    @Extension
    @Symbol("duringBuildActionInstance")
    public static class DescriptorImpl extends Descriptor<BuildEntry> {
        @Override public String getDisplayName() {
            return "Action run during build";
        }

        public ListBoxModel doFillUnitItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option(Time.getMillisecondsString()),
                    new ListBoxModel.Option(Time.getSecondsString()),
                    new ListBoxModel.Option(Time.getMinutesString())
            );
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getLoop() {
        return loop;
    }

    public void setLoop(Boolean loop) {
        this.loop = loop;
    }

    public Boolean getActionPerformed() {
        return actionPerformed;
    }

    public void setActionPerformed() {
        this.actionPerformed = true;
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
