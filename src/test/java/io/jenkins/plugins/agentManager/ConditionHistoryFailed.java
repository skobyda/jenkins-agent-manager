package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Result;
import hudson.slaves.DumbSlave;
import hudson.tasks.Shell;
import io.jenkins.plugins.agentManager.Actions.PostBuild.ShellScriptPostBuild;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.PostBuild.HistoryFailedPostBuild;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

public class ConditionHistoryFailed {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testHistoryFailedPassed() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        DumbSlave slave = jenkinsRule.createSlave();
        FreeStyleProject freeStyleProject = jenkinsRule.createFreeStyleProject();
        freeStyleProject.setAssignedNode(slave);
        freeStyleProject.getBuildersList().add(new Shell("exit 1"));
        jenkinsRule.buildAndAssertStatus(Result.FAILURE, freeStyleProject);

        PostBuildCondition condition = new HistoryFailedPostBuild(2);
        PostBuildAction action = new ShellScriptPostBuild("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        NodePropertyImpl nodeProperty = new NodePropertyImpl(Arrays.asList(entry));
        slave.setNodeProperties(Arrays.asList(nodeProperty));
        jenkinsRule.buildAndAssertStatus(Result.FAILURE, freeStyleProject);

        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as true", condition.getName())));
        assertTrue(logger.getMessages().contains(String.format("Action '%s' finished successfully", action.getName())));
    }

    @Test
    public void testHistoryFailedNotEnoughRuns() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        DumbSlave slave = jenkinsRule.createSlave();
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);
        freeStyleProject.getBuildersList().add(new Shell("exit 1"));
        jenkinsRule.buildAndAssertStatus(Result.FAILURE, freeStyleProject);

        // Try to check last 5 runs, but project has only 2 runs in history, so the condition should evaluate as false
        PostBuildCondition condition = new HistoryFailedPostBuild(5);
        PostBuildAction action = new ShellScriptPostBuild("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        NodePropertyImpl nodeProperty = new NodePropertyImpl(Arrays.asList(entry));
        slave.setNodeProperties(Arrays.asList(nodeProperty));
        jenkinsRule.buildAndAssertStatus(Result.FAILURE, freeStyleProject);

        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as false", condition.getName())));
    }

    @Test
    public void testHistoryFailedNotPassed() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        PostBuildCondition condition = new HistoryFailedPostBuild(1);
        PostBuildAction action = new ShellScriptPostBuild("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);

        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        FreeStyleBuild build = jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as false", condition.getName())));
        jenkinsRule.assertBuildStatusSuccess(build);
    }
}
