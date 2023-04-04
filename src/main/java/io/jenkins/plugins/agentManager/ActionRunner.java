package io.jenkins.plugins.agentManager;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.slaves.OfflineCause;
import hudson.util.RunList;
import io.jenkins.plugins.agentManager.ScriptRunner.BashScriptRunner;
import io.jenkins.plugins.agentManager.ScriptRunner.BatchScriptRunner;
import io.jenkins.plugins.agentManager.ScriptRunner.ScriptRunner;
import io.jenkins.plugins.agentManager.View.ActionInstance;
import io.jenkins.plugins.agentManager.View.ActionNodeProperty;
import io.jenkins.plugins.agentManager.View.Condition;
import io.jenkins.plugins.agentManager.View.Trigger;
import io.jenkins.plugins.agentManager.View.Action;
import org.jvnet.localizer.ResourceBundleHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActionRunner {
    private static boolean conditionDuration(Condition.Duration duration, Run<?,?> run) {
        long buildDuration = run.getDuration();
        String durationCondition = duration.getDurationCondition();
        String unit = duration.getUnit();
        long time = duration.getTime();
        long expectedDurationInMilliseconds;

        if (unit.equals("milliseconds"))
            expectedDurationInMilliseconds = time;
        else if (unit.equals("seconds"))
            expectedDurationInMilliseconds = time * 1000;
        else if (unit.equals("minutes"))
            expectedDurationInMilliseconds = time * 1000 * 60;
        else
            return false;
            // TODO add exception or error message

        boolean returnVal;
        if (durationCondition.equals("Build took more than"))
            returnVal = buildDuration > expectedDurationInMilliseconds;
        else
            returnVal = buildDuration < expectedDurationInMilliseconds;

        return returnVal;
    }

    private static boolean conditionScript(Condition.Script script, TaskListener listener, Launcher launcher) {
        ScriptRunner runner = null;
        listener.getLogger().println(script.getScriptText());
        if ("BASH".equals(script.getLanguage())) {
            runner = new BashScriptRunner();
        } else if ("GROOVY".equals(script.getLanguage())) {
            runner = new BatchScriptRunner();
        }
        listener.getLogger().println(runner);

        return runner.evaluateCondition(launcher, listener, script);
    }

    private static boolean conditionHistory(Condition.History history, TaskListener listener, Launcher launcher) {
        Computer computer = Computer.currentComputer();
        RunList<? extends Run<?, ?>> runList = computer.getBuilds();
        int quantity = history.getQuantity();

        System.out.println(quantity);
        System.out.println(history.getHistoryCondition());
        System.out.println("Entering cycle");
        for (int i = 0; i < quantity; i++) {
            // If there is not enough runs to evaluate condition, pass the condition
            if (!runList.iterator().hasNext())
                return true;

            Run run = runList.iterator().next();
        }

        return false;
    }

    private static List <ActionInstance> filterScriptsByCondition(Run<?,?> run, TaskListener listener,  Launcher launcher, List <ActionInstance> list) {
        List <ActionInstance> filtered = new ArrayList<>();
        for (ActionInstance action : list) {
            Condition condition = action.getCondition();

            switch(condition.getName()) {
                case "Everytime":
                    filtered.add(action);
                    break;
                case "Duration":
                    if (conditionDuration((Condition.Duration) condition, run))
                        filtered.add(action);
                    break;
                case "Script":
                    if (conditionScript((Condition.Script) condition, listener, launcher))
                        filtered.add(action);
                    break;
                case "History":
                    if (conditionHistory((Condition.History) condition, listener, launcher))
                        filtered.add(action);
                    break;
                default:
                    // TODO fix error handling
                    throw new IllegalArgumentException("Invalid action: " + action.getAction().getName());
            }
        }

        listener.getLogger().println(filtered);
        return filtered;
    }

    private static List <ActionInstance> filterScriptsByTrigger(Run<?,?> run, TaskListener listener, Boolean isPreBuild) {
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

    private static List <ActionInstance> getRelevantScripts(Run<?,?> run, TaskListener listener, Launcher launcher, Boolean isPreBuild) {
        List <ActionInstance> filteredList = filterScriptsByTrigger(run, listener, isPreBuild);

        return filterScriptsByCondition(run, listener, launcher, filteredList);
    }

    private static List <ActionInstance> getActionsMatchingTriggers(String trigger, TaskListener listener) {
        listener.getLogger().println(trigger);
        listener.getLogger().println("HERE");

        List <ActionInstance> filtered = new ArrayList<>();
        List <ActionInstance> actions = getAllNodeActions();

        for (ActionInstance action : actions) {
            Trigger actionTrigger = action.getTrigger();

            listener.getLogger().println("HELLO");
            listener.getLogger().println(actionTrigger.getName());
            if (trigger.equals(actionTrigger.getName())) {
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
        List <ActionInstance> actions = getRelevantScripts(run, listener, launcher, isPreBuild);
        listener.getLogger().println("RELEVANT SCRIPTS");
        listener.getLogger().println(actions);

        for (ActionInstance action : actions) {
            listener.getLogger().println("Acting upon action");
            listener.getLogger().println(action);
            // TODO runScript should be a property of script?
            switch(action.getAction().getName()) {
                case "CustomScript":
                    runScript(launcher, listener, (Action.CustomScript) action.getAction());
                    break;
                case "Cleanup":
                    cleanup(launcher, listener, run, workspace);
                    break;
                case "Reboot":
                    // reboot(launcher, listener, run, workspace);
                    break;
                case "SetOffline":
                    setOffline(listener, run);
                    break;
                default:
                    // TODO fix error handling
                    throw new IllegalArgumentException("Invalid action: " + action.getAction().getName());
            }
        }
    }

    private static void runScript(Launcher launcher, TaskListener listener, Action.CustomScript script) {
        // TODO
        // Use factory here
        ScriptRunner runner = null;
        listener.getLogger().println(script.getScriptText());
        if ("BASH".equals(script.getLanguage())) {
            runner = new BashScriptRunner();
        } else if ("GROOVY".equals(script.getLanguage())) {
            runner = new BatchScriptRunner();
        }
        listener.getLogger().println(runner);

        runner.run(launcher, listener, script);
    }

    private static void getRunTime(TaskListener listener, Run run) {
        run.getDuration();
    }

    private static void setOffline(TaskListener listener, Run run) {
        listener.getLogger().println("Set offline");
        Computer computer = Computer.currentComputer();

        // if (computer != null)
        //     TODO

        // TODO localization
        ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);
        User user = User.current();
        int buildNumber = run.getNumber();
        String buildName = run.getFullDisplayName();
        String nodeName = computer.getNode().getDisplayName();
        String offlineMessage = String.format("Taking node '%s' offline temporarily. Triggered by build '%s' number '%d'", nodeName, buildName, buildNumber);

        // TODO research if ByCLI should be used instead
        computer.setTemporarilyOffline(true, new OfflineCause.ByCLI(offlineMessage));
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

        // One way to achieve reboot is to call getBuilds() for computer, then get estimated time for finish for each build, and wait in cycle for builds to finish
        // Once all the builds finish, we can do a graceful reboot
    } */

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
