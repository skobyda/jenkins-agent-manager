package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.model.listeners.RunListener;
import jenkins.util.Timer;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.Objects;
import java.util.logging.Logger;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

// The reason we want to use RunListener is that RunListener is not part of build as build-step,
// which is more desired when running actions over agent which is usually some diagnostics, or system/server maintenance, which not being part of the build itself
@Extension
public class RunListenerImpl extends RunListener<Run<?, ?>> {
    private static final Logger LOGGER = Logger.getLogger(RunListenerImpl.class.getName());
    List<ScheduledFuture<?>> futureList;

    @DataBoundConstructor
    public RunListenerImpl() {
        // noop
    }

    @Override
    public Environment setUpEnvironment(AbstractBuild build, Launcher launcher, BuildListener listener) {
        Computer currentComputer = Computer.currentComputer();
        if (currentComputer == null) {
            LOGGER.severe("No Computer found. Cannot proceed with post-build action");
        }

        ActionRunner actionRunner = new ActionRunner(launcher, listener, build);
        actionRunner.actPreBuild(currentComputer);
        LOGGER.info("PreBuild actions completed");

        return new Environment() {};
    }

    @Override
    public void onCompleted(Run<?, ?> run, TaskListener listener) {
        for (ScheduledFuture<?> future : futureList) {
            future.cancel(true);
        }

        Computer currentComputer = Computer.currentComputer();
        if (currentComputer == null) {
            LOGGER.severe("No Computer found. Cannot proceed with post-build action");
            return;
        }

        Node node = currentComputer.getNode();
        if (node == null) {
            LOGGER.severe("No Node found. Cannot proceed with post-build action");
            return;
        }

        Launcher launcher = node.createLauncher(listener);
        AbstractBuild build = (AbstractBuild) run;

        ActionRunner actionRunner = new ActionRunner(launcher, listener, build);
        LOGGER.info("Triggering post-build actions");
        actionRunner.actPostBuild(currentComputer);
        LOGGER.info("Post-build actions completed");
    }

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        Computer currentComputer = Computer.currentComputer();
        if (currentComputer == null) {
            LOGGER.severe("No Computer available. Cannot proceed with post-build action");
            return;
        }
        Node node = currentComputer.getNode();
        assert node != null;

        Launcher launcher = node.createLauncher(listener);
        if (launcher == null) {
            LOGGER.severe("No launcher created. Cannot proceed with post-build action");
            return;
        }
        AbstractBuild build = (AbstractBuild) run;

        ScheduledExecutorService executorService = Timer.get();

        ActionRunner actionRunner = new ActionRunner(launcher, listener, build);
        this.futureList = actionRunner.actDuringBuild(executorService, Computer.currentComputer());
    }
}
