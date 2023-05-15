package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Node;
import hudson.model.Result;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.DuringBuild.SetOfflineDuringBuild;
import io.jenkins.plugins.agentManager.Actions.DuringBuild.StopBuildDuringBuild;
import io.jenkins.plugins.agentManager.Actions.PostBuild.StopBuildPostBuild;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.DuringBuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.DuringBuild.NoConditionDuringBuild;
import io.jenkins.plugins.agentManager.Conditions.DuringBuildCondition;
import io.jenkins.plugins.agentManager.Utils.Time;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActionStopBuildTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testStopBuild() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        DuringBuildCondition condition = new NoConditionDuringBuild();
        DuringBuildAction action = new StopBuildDuringBuild();
        DuringBuildEntry entry = new DuringBuildEntry(1L, Time.getMillisecondsString(), false, condition, action);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        // Assert build was stop therefore it was marked as ABORTED
        jenkinsRule.buildAndAssertStatus(Result.ABORTED, freeStyleProject);
        assertTrue(logger.getMessages().contains(String.format("Action '%s' finished successfully", action.getName())));
    }
}
