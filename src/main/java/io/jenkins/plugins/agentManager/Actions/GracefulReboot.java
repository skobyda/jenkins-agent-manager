package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.Run;
import hudson.model.TaskListener;

public abstract class GracefulReboot implements Action {
    @Override
    public String getName() {
        return  "GracefulReboot";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        // TODO fixthis
        // SetOffline.runAction(listener, launcher, run);

        // Wait for all builds on computer to finish
        // TODO prevent infinite time
        // TODO allow user to specify timeout for which it will wait for builds to finish
        for (Object object : computer.getBuilds()) {
            Run build = (Run) object;
            while (build.isBuilding()) {
                try {
                    long estimatedRemaining = build.getExecutor().getEstimatedRemainingTimeMillis();
                    Thread.sleep(estimatedRemaining);
                } catch (InterruptedException e) {
                    // TODO
                }
            }
        }

        // Reboot.runAction(listener, launcher, run);
    }
        /* private static void reboot(Launcher launcher, TaskListener listener, Run run, FilePath workspace) {
            OfflineCause cause = new OfflineCause();

            computer.disconnect(OfflineCause.UserCause);

                Executable currentExecutable = computer.getExecutors().getCurrentExecutable();
                if (currentExecutable != null) {
                    QueueTaskFuture<?> future = currentExecutable.getOwnerTask().getFuture();
                    if (future != null) {
                        future.waitForCompletion(10, TimeUnit.SECONDS);
                    }
                }
            }

            // Reconnect the computer
            if (computer != null) {
                computer.connect(false);
            }
        } */
}
