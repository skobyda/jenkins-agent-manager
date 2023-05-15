package io.jenkins.plugins.agentManager;

import hudson.Launcher;
import hudson.model.*;
import io.jenkins.plugins.agentManager.Actions.Action;
import io.jenkins.plugins.agentManager.BuildEntries.BuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.DuringBuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.PreBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.Condition;
import io.jenkins.plugins.agentManager.Utils.Time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ActionRunner {
    private static final Logger LOGGER = Logger.getLogger(RunListenerImpl.class.getName());
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

            try {
                if (conditionPasses(condition))
                    filtered.add(entry);
            } catch (Exception e) {
                LOGGER.severe(String.format("Failed to evaluate condition %s", condition.getName()));
            }
        }

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
        assert node != null;
        NodePropertyImpl actionNodeProperty = node.getNodeProperties().get(NodePropertyImpl.class);

        if (actionNodeProperty == null)
            return Arrays.asList();

        return actionNodeProperty.getEntries();
    }

    private boolean conditionPasses(Condition condition) {
        boolean ret = false;
        try {
            ret = condition.conditionPasses(listener, launcher, build);
            LOGGER.info(String.format("Condition '%s' evaluated as %s", condition.getName(), ret));
        } catch (Exception e) {
            LOGGER.severe(String.format("Failed to evaluate condition '%s': %s", condition.getName(), e.toString()));
        }

        return ret;
    }

    private void runAction(Action action, Computer computer) {
        try {
            action.runAction(listener, launcher, build, computer);
            LOGGER.info(String.format("Action '%s' finished successfully", action.getName()));
        } catch (Exception e) {
            LOGGER.severe(String.format("Failed to run action '%s': %s", action.getName(), e.toString()));
        }
    }

    private void act(List <BuildEntry> entries, Computer computer) {
        for (BuildEntry entry : entries) {
            Action action = entry.getAction();

            runAction(action, computer);
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
    private void evaluateAndRun(DuringBuildEntry entry, ScheduledExecutorService executorService, Computer computer) throws Exception {
        if (entry.getActionPerformed())
            return;

        Condition condition = entry.getCondition();
        if (conditionPasses(condition)) {
            Action action = entry.getAction();
            runAction(action, computer);

            if (!entry.getLoop()) {
                // There is no way for us to cancel the Scheduled task from here.
                // So instead we set it so the action is not performed again
                // Alternatively we can also throw exception:
                // https://stackoverflow.com/questions/4909824/stop-a-periodic-task-from-within-the-task-itself-running-in-a-scheduledexecutors/4910682d
                entry.setActionPerformed();
            }

            LOGGER.info(String.format("Performed action '%s' with condition '%s' during the build", action.getName(), condition.getName()));
        }
    }

    public List<ScheduledFuture<?>> actDuringBuild(ScheduledExecutorService executorService, Computer computer) {
        List <BuildEntry> entries = getEntriesByType(DuringBuildEntry.class);

        List <ScheduledFuture<?>> futureList = new ArrayList<>();
        for (BuildEntry entry : entries) {
            DuringBuildEntry duringBuildEntry = (DuringBuildEntry) entry;

            long timeout = duringBuildEntry.getTime();
            String timeoutUnit = duringBuildEntry.getUnit();
            long timeoutInMillis = Time.convertToMilliseconds(timeout, timeoutUnit);

            Runnable periodicAction = () -> {
                try {
                    evaluateAndRun(duringBuildEntry, executorService, computer);
                } catch (Exception e) {
                    LOGGER.severe(String.format("Failed to run action %s with a condition %s", duringBuildEntry.getAction().getName(), duringBuildEntry.getCondition().getName()));
                }
            };
            ScheduledFuture<?> future = executorService.scheduleAtFixedRate(periodicAction, 0, timeoutInMillis, TimeUnit.MILLISECONDS);
            futureList.add(future);
        }

        return futureList;
    }
}
