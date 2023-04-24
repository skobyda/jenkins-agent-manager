package io.jenkins.plugins.agentManager.View.Conditions.PostBuild;

import hudson.Extension;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.agentManager.View.PostBuildCondition;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.springframework.lang.NonNull;

public class Duration extends io.jenkins.plugins.agentManager.View.Conditions.Duration implements PostBuildCondition {
    @DataBoundConstructor
    public Duration(String durationCondition, long time, String unit) {
        super(durationCondition, time, unit);
        System.out.println("Duration PostBuildCondition:");
        System.out.println(durationCondition);
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
