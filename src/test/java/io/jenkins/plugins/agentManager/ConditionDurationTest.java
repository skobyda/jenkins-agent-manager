package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.PostBuild.ShellScript;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.PostBuild.Duration;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import io.jenkins.plugins.agentManager.Utils.Time;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ConditionDurationTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testDurationPassed() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        PostBuildCondition condition = new Duration("Build took more than", 1, Time.getMillisecondsString());
        PostBuildAction action = new ShellScript("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);

        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        FreeStyleBuild build = jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as true", condition.getName())));
        jenkinsRule.assertBuildStatusSuccess(build);
    }

    @Test
    public void testDurationNotPassed() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        PostBuildCondition condition = new Duration("Build took less than", 1, Time.getMillisecondsString());
        PostBuildAction action = new ShellScript("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);

        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        FreeStyleBuild build = jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as false", condition.getName())));
        jenkinsRule.assertBuildStatusSuccess(build);
    }
}
