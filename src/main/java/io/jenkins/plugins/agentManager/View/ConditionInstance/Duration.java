package io.jenkins.plugins.agentManager.View.ConditionInstance;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.View.Condition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Duration extends Condition {
    private final String durationCondition;
    private final long time;
    private final String unit;

    public String getDurationCondition() {
        return durationCondition;
    }

    public long getTime() {
        return time;
    }

    public String getUnit() {
        return unit;
    }

    @DataBoundConstructor
    public Duration(String durationCondition, long time, String unit) {
        super("Duration");
        this.durationCondition = durationCondition;
        this.time = time;
        this.unit = unit;
    }

    @Extension
    @Symbol("Duration")
    public static final class DescriptorImpl extends ConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Based on build duration";
        }

        public ListBoxModel doFillUnitItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("milliseconds"),
                    new ListBoxModel.Option("seconds"),
                    new ListBoxModel.Option("minutes")
                    );
        }

        public ListBoxModel doFillDurationConditionItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option("Build took more than"),
                    new ListBoxModel.Option("Build took less than")
                    );
        }
    }
}
