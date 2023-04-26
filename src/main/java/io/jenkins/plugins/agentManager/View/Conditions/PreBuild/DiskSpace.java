package io.jenkins.plugins.agentManager.View.Conditions.PreBuild;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.View.PreBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class DiskSpace extends io.jenkins.plugins.agentManager.View.Conditions.DiskSpace implements PreBuildCondition {
    @DataBoundConstructor
    public DiskSpace(long space, String unit) {
        super(space, unit);
    }

    @Extension
    @Symbol("DiskSpace")
    public static final class DescriptorImpl extends PreBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Available space left";
        }

        public ListBoxModel doFillUnitItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("MB"),
                    new ListBoxModel.Option("GB")
                    );
        }
    }
}