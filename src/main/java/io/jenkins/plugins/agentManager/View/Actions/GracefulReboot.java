package io.jenkins.plugins.agentManager.View.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.Run;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Action;

public abstract class GracefulReboot implements Action {
    @Override
    public String getName() {
        return  "GracefulReboot";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run) {
        // TODO fixthis
        // SetOffline.runAction(listener, launcher, run);

        // Wait for all builds on computer to finish
        Computer computer = Computer.currentComputer();
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
}
