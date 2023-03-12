package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import java.io.IOException;

import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

// The reason we want to use Notifier over SimpleBuildStep or BuildWrapper is:
// SimpleBuildStep is run only if the main build is successful, thus it would break our use case of having post-build actions which are triggered
// only if the main build fails
// BuildWrapper is still part of the build, thus we don't know the resut of the build yet.
// Alternatively, we can also use Recorder
public class PostBuildExecutor extends Notifier {
    private final String name;

    @DataBoundConstructor
    public PostBuildExecutor(String name) {
        this.name = name;
    }

    @Override
    public boolean perform(AbstractBuild<?,?> run, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("Running post-build executor");

        ActionRunner.act(launcher, listener, run, false);

        // TODO throw an error in case of failure
        return true;
    }

    @Symbol("script")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Post-build script";
        }
    }
}