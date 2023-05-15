package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.*;
import io.jenkins.plugins.agentManager.Utils.HelperActions;

import java.util.Objects;
import java.util.logging.Logger;

public abstract class GracefulReboot implements Action {
    private static final Logger LOGGER = Logger.getLogger(GracefulReboot.class.getName());

    @Override
    public String getName() {
        return  "GracefulReboot";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild build, Computer computer) throws InterruptedException {
        HelperActions.setAgentOffline(listener, launcher, build, computer);

        // Wait for all builds on computer to finish
        for (Object object : computer.getBuilds()) {
            Run run = (Run) object;
            while (run.isBuilding()) {
                // Have 5 seconds as arbitrary default timeout
                // TODO in the future allow user to specify timeout for which it will wait for builds to finish
                long defaultTimeout = 5000;
                try {
                    Executor executor = build.getExecutor();
                    assert executor != null;

                    long estimatedRemaining = executor.getEstimatedRemainingTimeMillis();
                    if (estimatedRemaining > 5000)
                        defaultTimeout = estimatedRemaining;
                } catch(Exception e) {
                    LOGGER.info(String.format("Could not get estimated remaining time of build %s, defaulting to 5 seconds", run.getNumber()));
                }
                Executor.currentExecutor().sleep(defaultTimeout);
            }
        }

        HelperActions.rebootAgent(listener, launcher, computer);
    }
}
