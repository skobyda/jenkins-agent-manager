package io.jenkins.plugins.agentManager.View;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.model.Descriptor;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class PostBuildEntry extends BuildEntry {
    /**
     * Override locations. Never null.
     */
    private PostBuildCondition postBuildCondition;
    private PostBuildAction postBuildAction;

    @DataBoundConstructor
    public PostBuildEntry(PostBuildCondition postBuildCondition, PostBuildAction postBuildAction) {
        System.out.println("PostBuildActionInstance");
        this.postBuildCondition = postBuildCondition;
        this.postBuildAction = postBuildAction;
    }

    @Extension
    @Symbol("postBuildActionInstance")
    public static class DescriptorImpl extends Descriptor<BuildEntry> {
        @Override public String getDisplayName() {
            return "Action run after build";
        }
    }

    public PostBuildCondition getPostBuildCondition() {
        // Could return currently configured/saved item here to initialized form with this data
        return postBuildCondition;
    }

    public void setPostBuildCondition(PostBuildCondition postBuildCondition) {
        // Could return currently configured/saved item here to initialized form with this data
        this.postBuildCondition = postBuildCondition;
    }

    public PostBuildAction getPostBuildAction() {
        // Could return currently configured/saved item here to initialized form with this data
        return postBuildAction;
    }

    public void setPostBuildAction(PostBuildAction postBuildAction) {
        // Could return currently configured/saved item here to initialized form with this data
        this.postBuildAction = postBuildAction;
    }

    public Condition getCondition() {
        return this.postBuildCondition;
    }

    public Action getAction() {
        return this.postBuildAction;
    }

    public DescriptorExtensionList<PostBuildCondition,Descriptor<PostBuildCondition>> getPostBuildConditionDescriptors() {
        return Jenkins.get().getDescriptorList(PostBuildCondition.class);
    }

    public DescriptorExtensionList<PostBuildAction,Descriptor<PostBuildAction>> getPostBuildActionDescriptors() {
        return Jenkins.get().getDescriptorList(PostBuildAction.class);
    }
}
