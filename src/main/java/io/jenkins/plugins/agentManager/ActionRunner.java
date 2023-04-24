package io.jenkins.plugins.agentManager;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import io.jenkins.plugins.agentManager.View.*;
import io.jenkins.plugins.agentManager.View.Action;

import java.util.ArrayList;
import java.util.List;

public class ActionRunner {
    private static List <BuildEntry> filterScriptsByCondition(AbstractBuild run, TaskListener listener, Launcher launcher, List <BuildEntry> entries) {
        List <BuildEntry> filtered = new ArrayList<>();
        for (BuildEntry entry : entries) {
            Condition condition = entry.getCondition();

            listener.getLogger().println("condition");
            listener.getLogger().println(condition);
            if (condition.conditionPasses(listener, launcher, run))
                filtered.add(entry);
        }

        listener.getLogger().println("filterScriptsByCondition");
        listener.getLogger().println(filtered);
        return filtered;
    }

    private static List <BuildEntry> filterActionsByClass(AbstractBuild run, TaskListener listener, Boolean isPreBuild) {
        if (isPreBuild) {
            return getActionsByType(PreBuildEntry.class, listener);
        }

        Result result = run.getResult();
        listener.getLogger().println(result);
        listener.getLogger().println(result.toString());

        // result is only set after all post-build actions have run, as post-build actions also may fail
        // so the result == null means that build has not encountered any error yet
        // if (result == null) {
        //     return getScriptsByType("SUCCESS", listener);
        // }

        // Mentioned above doesn't fit anymore. That used to work only if we extend BuildWrapper or BuildStep. Extending BuildListener we have a meaningful output
        if (result == Result.SUCCESS) {
            return getActionsByType(PostBuildEntry.class, listener);
        }

        // TODO difference based on result
        if (result == Result.FAILURE) {
            return getActionsByType(PostBuildEntry.class, listener);
        }

        // TODO
        // result == Result.UNSTABLE or result == Result.ABRUPTED
        return new ArrayList<>();
    }

    private static List <BuildEntry> getRelevantActions(AbstractBuild run, TaskListener listener, Launcher launcher, Boolean isPreBuild) {
        List <BuildEntry> filteredList = filterActionsByClass(run, listener, isPreBuild);

        listener.getLogger().println("getRelevantActions");
        listener.getLogger().println(filteredList);

        return filterScriptsByCondition(run, listener, launcher, filteredList);
    }

    private static List <BuildEntry> getActionsByType(Class matchingClass, TaskListener listener) {
        listener.getLogger().println(matchingClass);
        listener.getLogger().println("HERE");

        List <BuildEntry> filtered = new ArrayList<>();
        List <BuildEntry> actions = getAllNodeActions();

        for (BuildEntry action : actions) {
            listener.getLogger().println("HELLO");
            listener.getLogger().println(action.getClass());
            if (action.getClass().equals(matchingClass)) {
                filtered.add(action);
            }
        }

        listener.getLogger().println(filtered);
        return filtered;
    }

    private static List<BuildEntry> getAllNodeActions() {
        Node node = Computer.currentComputer().getNode();
        NodePropertyImpl actionNodeProperty = node.getNodeProperties().get(NodePropertyImpl.class);
        // TODO null handling
        // TODO, test also on project which do not have node assigned for computer which doesn't have agentManager setups
        return actionNodeProperty.getEntries();
    }

    public static void actPreBuild(Launcher launcher, TaskListener listener, AbstractBuild run, FilePath workspace) {
        listener.getLogger().println("actPreBuild");
        List <BuildEntry> entries = getRelevantActions(run, listener, launcher, true);
        listener.getLogger().println(entries);

        for (BuildEntry entry : entries) {
            listener.getLogger().println("actPreBuild: entry");
            listener.getLogger().println(entry);
            // TODO runScript should be a property of script?
            Action action = entry.getAction();
            listener.getLogger().println("actPreBuild: action");
            listener.getLogger().println(action);

            // TODO error handling
            action.runAction(listener, launcher, run);
        }
    }

    public static void actPostBuild(Launcher launcher, TaskListener listener, AbstractBuild run, FilePath workspace) {
        listener.getLogger().println("actPostBuild");
        List <BuildEntry> entries = getRelevantActions(run, listener, launcher, false);
        listener.getLogger().println(entries);

        for (BuildEntry entry : entries) {
            listener.getLogger().println("actPostBuild: entry");
            listener.getLogger().println(entry);
            // TODO runScript should be a property of script?
            Action action = entry.getAction();

            listener.getLogger().println("actPostBuild: action");
            listener.getLogger().println(action);
            // TODO error handling
            action.runAction(listener, launcher, run);
        }
    }
}
