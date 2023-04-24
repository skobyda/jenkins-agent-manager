package io.jenkins.plugins.agentManager.View.Actions;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Action;

import java.io.IOException;

public abstract class Cleanup implements Action {
    @Override
    public String getName() {
        return "Cleanup";
    }
    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run) {
        FilePath workspace = run.getWorkspace();
        AbstractBuild build = run;
        listener.getLogger().println("Cleanup");
        listener.getLogger().println(build.getWorkspace());

        try {
            workspace.deleteRecursive();
        } catch (IOException e) {
            // TODO
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // TODO
            throw new RuntimeException(e);
        }
            /*
            // TODO clean all the other finished workspaces
            Computer computer = Computer.currentComputer();

            // computer.setTemporarilyOffline(true, new hudson.slaves.OfflineCause.ByCLI("disk cleanup"));
            Iterator<Run> runListIterator = computer.getBuilds().iterator();
            Iterator<Run> runListIterator = computer.getBuilds();

            while (runListIterator.hasNext()) {
                Run run = runListIterator.next();

                if (run.isBuilding())
                    continue;

                // TODO check if workspace is in use

                workspacePath = node.getWorkspaceFor(item)
                if (workspacePath == null) {
                    continue
                }
                customWorkspace = item.getCustomWorkspace()
                if (customWorkspace != null) {
                    workspacePath = node.getRootPath().child(customWorkspace)
                }

                pathAsString = workspacePath.getRemote()
                if (workspacePath.exists()) {
                    workspacePath.deleteRecursive()
                } else {
                }
            }

            computer.setTemporarilyOffline(false, null)
             */
    }
}
