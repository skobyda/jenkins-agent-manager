package io.jenkins.plugins.agentManager;

import hudson.Launcher;
import hudson.model.*;
import io.jenkins.plugins.agentManager.Actions.Action;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.BuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.DuringBuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.PreBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.Condition;
import io.jenkins.plugins.agentManager.Utils.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ActionRunner {
    private final Launcher launcher;
    private final TaskListener listener;
    private final AbstractBuild build;

    public ActionRunner(Launcher launcher, TaskListener listener, AbstractBuild build) {
        this.launcher = launcher;
        this.listener = listener;
        this.build = build;
    }

    private List <BuildEntry> getEntriesPassingCondition(List <BuildEntry> entries) {
        List <BuildEntry> filtered = new ArrayList<>();
        for (BuildEntry entry : entries) {
            Condition condition = entry.getCondition();

            if (condition.conditionPasses(listener, launcher, build))
                filtered.add(entry);
        }

        // TODO we can get rid of filtering if entry had an "perform" method which runs an action when condition passes
        return filtered;
    }

    private List <BuildEntry> getEntriesByType(Class matchingClass) {
        List <BuildEntry> filtered = new ArrayList<>();
        List <BuildEntry> entries = getAllNodeEntries();

        for (BuildEntry entry : entries) {
            if (entry.getClass().equals(matchingClass)) {
                filtered.add(entry);
            }
        }

        return filtered;
    }

    private List<BuildEntry> getAllNodeEntries() {
        Node node = Computer.currentComputer().getNode();
        NodePropertyImpl actionNodeProperty = node.getNodeProperties().get(NodePropertyImpl.class);
        // TODO null handling
        // TODO, test also on project which do not have node assigned for computer which doesn't have agentManager setups
        return actionNodeProperty.getEntries();
    }

    private void act(List <BuildEntry> entries, Computer computer) {
        for (BuildEntry entry : entries) {
            Action action = entry.getAction();

            // TODO error handling
            action.runAction(listener, launcher, build, computer);
        }
    }

    public void actPreBuild(Computer computer) {
        List <BuildEntry> entries = getEntriesByType(PreBuildEntry.class);
        List <BuildEntry> filteredEntries = getEntriesPassingCondition(entries);

        act(filteredEntries, computer);
    }

    public void actPostBuild(Computer computer) {
        List <BuildEntry> entries = getEntriesByType(PostBuildEntry.class);
        List <BuildEntry> filteredEntries = getEntriesPassingCondition(entries);

        act(filteredEntries, computer);
    }

    // Move this to a BuildEntry class
    private void evaluateAndRun(DuringBuildEntry entry, ScheduledExecutorService executorService, Computer computer) {
        if (entry.getActionPerformed())
            return;

        Condition condition = entry.getCondition();
        // TODO wrap with try
        if (condition.conditionPasses(listener, launcher, build)) {
            Action action = entry.getAction();
            // TODO wrap with try
            // TODO pass computer here
            action.runAction(listener, launcher, build, computer);

            if (!entry.getLoop()) {
                // There is no way for us to cancel the Scheduled task from here.
                // So instead we set it so the action is not performed again
                entry.setActionPerformed();
            }
        }
    }

    public List<ScheduledFuture> actDuringBuild(ScheduledExecutorService executorService, Computer computer) {
        List <BuildEntry> entries = getEntriesByType(DuringBuildEntry.class);

        List <ScheduledFuture> futureList = new ArrayList<>();
        for (BuildEntry entry : entries) {
            DuringBuildEntry duringBuildEntry = (DuringBuildEntry) entry;

            long timeout = duringBuildEntry.getTime();
            String timeoutUnit = duringBuildEntry.getUnit();
            long timeoutInMillis = Time.convertToMilliseconds(timeout, timeoutUnit);

            Runnable periodicAction = () -> evaluateAndRun(duringBuildEntry, executorService, computer);
            ScheduledFuture future = executorService.scheduleAtFixedRate(periodicAction, 0, timeoutInMillis, TimeUnit.MILLISECONDS);
            futureList.add(future);
        }

        return futureList;
    }
}
