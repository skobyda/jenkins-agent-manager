package io.jenkins.plugins.agentManager.View.ConditionInstance;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.View.Condition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class DiskSpace extends Condition {
    private final long space;
    private final String unit;

    public long getSpace() {
        return space;
    }

    public String getUnit() {
        return unit;
    }

    @DataBoundConstructor
    public DiskSpace(long space, String unit) {
        super("DiskSpace");
        this.space = space;
        this.unit = unit;
    }

    @Extension
    @Symbol("DiskSpace")
    public static final class DescriptorImpl extends ConditionDescriptor {
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
