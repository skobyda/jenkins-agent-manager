package io.jenkins.plugins.agentManager.Conditions.PostBuild;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import io.jenkins.plugins.agentManager.Utils.Time;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class DurationPostBuild extends io.jenkins.plugins.agentManager.Conditions.Duration implements PostBuildCondition {
    @DataBoundConstructor
    public DurationPostBuild(String durationCondition, long time, String unit) {
        super(durationCondition, time, unit);
    }

    @Extension
    @Symbol("Duration")
    public static final class DescriptorImpl extends PostBuildConditionDescriptor {
        @NonNull
        @Override
        public String getDisplayName() {
            return "Based on build duration";
        }

        public ListBoxModel doFillUnitItems() {
            return new ListBoxModel(
                    new ListBoxModel.Option(Time.getMillisecondsString()),
                    new ListBoxModel.Option(Time.getSecondsString()),
                    new ListBoxModel.Option(Time.getMinutesString())
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
