/* package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildWrapper;
import io.jenkins.plugins.agentManager.ActionRunner;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

public class PreBuildExecutor extends BuildWrapper {
    @DataBoundConstructor
    public PreBuildExecutor() {

        // noop
    }

    @Override
    public Environment setUp(AbstractBuild build, final Launcher launcher, BuildListener listener) throws IOException,
            InterruptedException {
        listener.getLogger().println("Running pre-build executor");

        ActionRunner.act(launcher, listener, build, true);

        // return environment
        return new AgentManagerEnvironment(launcher, build) {};
    }

    @Override
    public void preCheckout(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        // TODO run setup here if we don't want user to manually select it in the build step
        // If you want your BuildWrapper extension point to always be applied to a Jenkins job, without requiring the user to select it as a build step, you can do so by overriding the preCheckout method in your BuildWrapper implementation.
        // The preCheckout method is called by Jenkins before a build begins, and allows you to modify the build environment or perform other tasks before the build actually starts. By overriding this method and implementing your desired behavior, you can ensure that your BuildWrapper extension point is always applied to the build.
    }


    @Extension
    public static final class DescriptorImpl extends Descriptor<BuildWrapper> {

        // This human readable name is used in the configuration screen.
        public String getDisplayName() {
            // TODO localization
            return "Pre-build script";
        }
    }

    public class AgentManagerEnvironment extends BuildWrapper.Environment {
        Launcher launcher;

        @DataBoundConstructor
        public AgentManagerEnvironment(Launcher launcher, AbstractBuild build) {
            this.launcher = launcher;
        }

        @Override
        public boolean tearDown(AbstractBuild build, BuildListener listener) {
            // listener.getLogger().println("Running tear-down");

            // ActionRunner.act(launcher, listener, build, false);

            // TODO return false if build cannot continue
            return true;
        }
    }
}
 */