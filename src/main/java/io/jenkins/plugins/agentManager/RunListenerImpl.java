package io.jenkins.plugins.agentManager;

/* import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.model.listeners.RunListener;
import hudson.tasks.*;

import java.io.IOException;

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
        listener.getLogger().println(run.getResult().toString());

        ActionRunner.act(launcher, listener, run, false);

        // TODO throw an error in case of failure
        return true;
    }

    @Override
    public boolean needsToRunAfterFinalized() {
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

        // @Override
        // public boolean needsToRunAfterFinalized() {
        // }
    }
}
*/

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.model.listeners.RunListener;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.swing.*;

@Extension
public class RunListenerImpl extends RunListener<Run<?, ?>> {
    Launcher launcher;
    FilePath workspace;

    @DataBoundConstructor
    public RunListenerImpl() {
        // noop
    }

    @Override
    public Environment setUpEnvironment(AbstractBuild run, Launcher launcher, BuildListener listener) {
        // TODO
        // Research if saving launcher and keeping it alive is not incorrect
        this.launcher = launcher;
        this.workspace = run.getWorkspace();
        listener.getLogger().println("setUpEnvironment");
        listener.getLogger().println(this.workspace);

        ActionRunner.actPreBuild(this.launcher, listener, run, workspace);

        return new Environment() {};
    }

    @Override
    public void onCompleted(Run<?, ?> run, TaskListener listener) {
        listener.getLogger().println("Running post-build executor");
        AbstractBuild build = (AbstractBuild) run;
        listener.getLogger().println(build.getWorkspace());

        listener.getLogger().println(run.getResult().toString());

        ActionRunner.actPostBuild(this.launcher, listener, run, workspace);
    }
}
