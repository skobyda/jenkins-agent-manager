package io.jenkins.plugins.agentManager;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.util.RunList;
import io.jenkins.plugins.agentManager.ScriptRunner.BashScriptRunner;
import io.jenkins.plugins.agentManager.ScriptRunner.GroovyScriptRunner;
import io.jenkins.plugins.agentManager.ScriptRunner.ScriptRunner;
import jenkins.model.Jenkins;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ActionRunner {
    private static List <ActionInstance> getRelevantScripts(Run<?,?> run, TaskListener listener, Boolean isPreBuild) {
        if (isPreBuild) {
            return getActionsMatchingTriggers("Before", listener);
        }

        Result result = run.getResult();
        listener.getLogger().println(result);
        listener.getLogger().println(result.toString());

        // result is only set after all post-build actions have run, as post-build actions also may fail
        // so the result == null means that build has not encountered any error yet
        // if (result == null) {
        //     return getScriptsMatchingTriggers("SUCCESS", listener);
        // }

        // Mentioned above doesn't fit anymore. That used to work only if we extend BuildWrapper or BuildStep. Extending BuildListener we have a meaningful output
        if (result == Result.SUCCESS) {
            return getActionsMatchingTriggers("Success", listener);
        }

        if (result == Result.FAILURE) {
            return getActionsMatchingTriggers("Failure", listener);
        }

        // TODO
        // result == Result.UNSTABLE or result == Result.ABRUPTED
        return new ArrayList<>();
    }

    private static List <ActionInstance> getActionsMatchingTriggers(String trigger, TaskListener listener) {
        listener.getLogger().println(trigger);
        listener.getLogger().println("HERE");

        List <ActionInstance> filtered = new ArrayList<>();
        List <ActionInstance> actions = getAllNodeActions();

        for (ActionInstance action : actions) {
            ActionInstance.Trigger actionTrigger = action.getTrigger();

            listener.getLogger().println("HELLO");
            listener.getLogger().println(actionTrigger.name);
            if (trigger.equals(actionTrigger.name)) {
                filtered.add(action);
            }
        }

        listener.getLogger().println(filtered);
        return filtered;
    }

    private static List <ActionInstance> getAllNodeActions() {
        Node node = Computer.currentComputer().getNode();
        ActionNodeProperty actionNodeProperty = node.getNodeProperties().get(ActionNodeProperty.class);
        // TODO null handling
        // TODO, test also on project which do not have node assigned for computer which doesn't have agentManager setups
        return actionNodeProperty.getActionInstances();
    }

    public static void act(Launcher launcher, TaskListener listener, Run<?,?> run, FilePath workspace, Boolean isPreBuild) {
        List <ActionInstance> actions = getRelevantScripts(run, listener, isPreBuild);
        listener.getLogger().println("RELEVANT SCRIPTS");
        listener.getLogger().println(actions);

        for (ActionInstance action : actions) {
            listener.getLogger().println("Acting upon action");
            listener.getLogger().println(action);
            // TODO runScript should be a property of script?
            switch(action.getAction().name) {
                case "CustomScript":
                    runScript(launcher, listener, (ActionInstance.CustomScript) action.getAction());
                    break;
                case "Cleanup":
                    cleanup(launcher, listener, run, workspace);
                    break;
                default:
                    // TODO fix error handling
                    throw new IllegalArgumentException("Invalid action: " + action.getAction().name);
            }
        }
    }

    private static void runScript(Launcher launcher, TaskListener listener, ActionInstance.CustomScript script) {
        // TODO
        // Use factory here
        ScriptRunner runner = null;
        listener.getLogger().println(script.getScriptText());
        if ("BASH".equals(script.getLanguage())) {
            runner = new BashScriptRunner();
        } else if ("GROOVY".equals(script.getLanguage())) {
            runner = new GroovyScriptRunner();
        }
        listener.getLogger().println(runner);

        runner.run(launcher, listener, script);
    }

    private static void cleanup(Launcher launcher, TaskListener listener, Run run, FilePath workspace) {
        listener.getLogger().println("Cleanup");
        AbstractBuild build = (AbstractBuild) run;
        FilePath workspacePath = build.getWorkspace();
        listener.getLogger().println(build.getWorkspace());

        try {
            workspacePath.deleteRecursive();
        } catch (IOException e) {
            // TODO
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // TODO
            throw new RuntimeException(e);
        }

        // TODO clean all the other finished workspaces
        /*
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