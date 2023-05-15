package io.jenkins.plugins.agentManager.Conditions.PostBuild;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class DiskSpacePostBuild extends io.jenkins.plugins.agentManager.Conditions.DiskSpace implements PostBuildCondition {
    @DataBoundConstructor
    public DiskSpacePostBuild(long space, String unit) {
        super(space, unit);
    }

    @Extension
    @Symbol("DiskSpace")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
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
