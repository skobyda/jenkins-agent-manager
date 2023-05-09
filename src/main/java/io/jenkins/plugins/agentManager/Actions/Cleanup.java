package io.jenkins.plugins.agentManager.Actions;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;

import java.io.IOException;
import java.util.Iterator;

public abstract class Cleanup implements Action {
    @Override
    public String getName() {
        return "Cleanup";
    }
    public void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) {
        FilePath workspace = run.getWorkspace();

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
