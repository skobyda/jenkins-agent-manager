package io.jenkins.plugins.agentManager;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import io.jenkins.plugins.agentManager.View.*;
import io.jenkins.plugins.agentManager.View.Action;

import java.util.ArrayList;
import java.util.List;

public class ActionRunner {
    private static List <BuildEntry> filterEntriesByCondition(AbstractBuild run, TaskListener listener, Launcher launcher, List <BuildEntry> entries) {
        List <BuildEntry> filtered = new ArrayList<>();
        for (BuildEntry entry : entries) {
            Condition condition = entry.getCondition();

            if (condition.conditionPasses(listener, launcher, run))
                filtered.add(entry);
        }

        return filtered;
    }

    private static List <BuildEntry> getActionsByPhase(AbstractBuild run, TaskListener listener, Launcher launcher, Boolean isPreBuild) {
        List <BuildEntry> filteredList = getEntriesByType(PostBuildEntry.class, listener);
        if (isPreBuild) {
            return getEntriesByType(PreBuildEntry.class, listener);
        }

        return filterEntriesByCondition(run, listener, launcher, filteredList);
    }

    private static List <BuildEntry> getEntriesByType(Class matchingClass, TaskListener listener) {
        List <BuildEntry> filtered = new ArrayList<>();
        List <BuildEntry> actions = getAllNodeActions();

        for (BuildEntry action : actions) {
            if (action.getClass().equals(matchingClass)) {
                filtered.add(action);
            }
        }

        return filtered;
    }

    private static List<BuildEntry> getAllNodeActions() {
        Node node = Computer.currentComputer().getNode();
        NodePropertyImpl actionNodeProperty = node.getNodeProperties().get(NodePropertyImpl.class);
        // TODO null handling
        // TODO, test also on project which do not have node assigned for computer which doesn't have agentManager setups
        return actionNodeProperty.getEntries();
    }

    public static void act(Launcher launcher, TaskListener listener, AbstractBuild run, FilePath workspace, Boolean isPreBuild) {
        List <BuildEntry> entries = getActionsByPhase(run, listener, launcher, isPreBuild);

        for (BuildEntry entry : entries) {
            Action action = entry.getAction();

            // TODO error handling
            action.runAction(listener, launcher, run);
        }
    }
}
