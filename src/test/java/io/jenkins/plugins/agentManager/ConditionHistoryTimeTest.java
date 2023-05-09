package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleProject;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.PostBuild.ShellScript;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.PostBuild.HistoryTime;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

public class ConditionHistoryTimeTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testHistoryTimePass() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        DumbSlave slave = jenkinsRule.createSlave();
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);
        jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        // Set average time so high that every previous build will have lower build duration
        PostBuildCondition condition = new HistoryTime(2, 1000000); // averageTime in milliseconnds
        PostBuildAction action = new ShellScript("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        NodePropertyImpl nodeProperty = new NodePropertyImpl(Arrays.asList(entry));
        slave.setNodeProperties(Arrays.asList(nodeProperty));
        jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as true", condition.getName())));
    }

    @Test
    public void testHistoryTimeFail() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        DumbSlave slave = jenkinsRule.createSlave();
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);
        jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        // Set average time so low that no previous build will have lower build duration
        PostBuildCondition condition = new HistoryTime(2, 1); // averageTime in milliseconnds
        PostBuildAction action = new ShellScript("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        NodePropertyImpl nodeProperty = new NodePropertyImpl(Arrays.asList(entry));
        slave.setNodeProperties(Arrays.asList(nodeProperty));
        jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as false", condition.getName())));
    }

    @Test
    public void testHistoryTimeNotEnoughRuns() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        DumbSlave slave = jenkinsRule.createSlave();
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);
        jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        // Try to check last 5 runs, but project has only 2 runs in history, so the condition should evaluate as false
        PostBuildCondition condition = new HistoryTime(5, 1);
        PostBuildAction action = new ShellScript("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        NodePropertyImpl nodeProperty = new NodePropertyImpl(Arrays.asList(entry));
        slave.setNodeProperties(Arrays.asList(nodeProperty));
        jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as false", condition.getName())));
    }
}
