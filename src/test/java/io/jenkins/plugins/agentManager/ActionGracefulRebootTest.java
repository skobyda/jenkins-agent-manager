package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleProject;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.PostBuild.GracefulReboot;
import io.jenkins.plugins.agentManager.Actions.PostBuild.Reboot;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.PostBuild.NoCondition;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.logging.Level;

public class ActionGracefulRebootTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testGracefulReboot() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        PostBuildCondition condition = new NoCondition();
        PostBuildAction action = new GracefulReboot();
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        // TODO figure how how to run tests for reboot without actually rebooting the machine where the tests are running
        // jenkinsRule.buildAndAssertSuccess(freeStyleProject);
        // assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as true", condition.getName())));
    }
}
