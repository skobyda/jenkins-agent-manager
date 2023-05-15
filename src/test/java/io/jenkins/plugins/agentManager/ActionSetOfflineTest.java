package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleProject;
import hudson.model.Node;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.PostBuild.RebootPostBuild;
import io.jenkins.plugins.agentManager.Actions.PostBuild.SetOfflinePostBuild;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.PostBuild.NoConditionPostBuild;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

public class ActionSetOfflineTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testSetOffline() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        PostBuildCondition condition = new NoConditionPostBuild();
        PostBuildAction action = new SetOfflinePostBuild();
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        jenkinsRule.buildAndAssertSuccess(freeStyleProject);
        // Assert action was successful
        assertTrue(logger.getMessages().contains(String.format("Action '%s' finished successfully", action.getName())));
        Node node = slave;
        // Assert agent was taken offline
        assertTrue(node.toComputer().isOffline());
    }
}
