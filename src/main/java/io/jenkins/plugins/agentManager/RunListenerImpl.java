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
import jenkins.util.Timer;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.swing.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Extension
public class RunListenerImpl extends RunListener<Run<?, ?>> {
    Launcher launcher;
    FilePath workspace;
    ScheduledExecutorService executorService;

    @DataBoundConstructor
    public RunListenerImpl() {
        // noop
    }

    @Override
    public Environment setUpEnvironment(AbstractBuild run, Launcher launcher, BuildListener listener) {
        // TODO should action run if build has a parent
        // Some projects run builds in form of tree
        // Run setup actions only on root
        // AbstractBuild rootBuild = rootBuild.getRootBuild();
        //
        // if (rootBuild != null)
        //    return null;

        // TODO
        // Research if saving launcher and keeping it alive is not incorrect
        this.launcher = launcher;
        this.workspace = run.getWorkspace();
        listener.getLogger().println("setUpEnvironment");
        listener.getLogger().println(this.workspace);

        ActionRunner.act(this.launcher, listener, run, workspace, true);

        return new Environment() {};
    }

    @Override
    public void onCompleted(Run<?, ?> run, TaskListener listener) {
        listener.getLogger().println("onCompleted");
        AbstractBuild build = (AbstractBuild) run;
        listener.getLogger().println(build.getWorkspace());

        listener.getLogger().println(run.getResult().toString());

        executorService.shutdown();

        ActionRunner.act(this.launcher, listener, build, workspace, false);
    }

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        listener.getLogger().println("onStarted");
        AbstractBuild build = (AbstractBuild) run;

        this.executorService = Timer.get();

        Runnable beeper = () -> System.out.println("beep");
        executorService.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);

        // Maybe have a canceller so service doesn't get stuck in a loop forever
        /* ScheduledFuture<?> handler = executorService.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);
        Runnable canceller = () -> beeperHandle.cancel(false);
        scheduler.schedule(canceller, 1, HOURS); */
    }
}
