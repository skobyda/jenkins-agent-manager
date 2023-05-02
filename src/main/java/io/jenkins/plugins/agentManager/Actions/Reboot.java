package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.Utils.ScriptRunner;

public abstract class Reboot implements Action {
    @Override
    public String getName() {
        return "Reboot";
    }

    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        // TODO batch
        ScriptRunner runner = new ScriptRunner();

        String scriptContent;

        if (computer.isUnix())
            scriptContent = "shutdown -r now";
        else
            scriptContent = "shutdown -r -f -t 0";

        runner.run(launcher, listener, scriptContent);
    }
        /* private static void reboot(Launcher launcher, TaskListener listener, Run run, FilePath workspace) {
            listener.getLogger().println("Reboot");
            Computer computer = Computer.currentComputer();

            // if (computer != null)
            //     TODO

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

            // TODO graceful reboot
            // One way to achieve reboot is to call getBuilds() for computer, then get estimated time for finish for each build, and wait in cycle for builds to finish
            // Once all the builds finish, we can do a graceful reboot
        } */
}
