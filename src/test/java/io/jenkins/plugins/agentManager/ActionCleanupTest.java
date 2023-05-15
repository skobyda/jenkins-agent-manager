package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Node;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.PostBuild.CleanupPostBuild;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActionCleanupTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testCleanup() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        PostBuildCondition condition = new NoConditionPostBuild();
        PostBuildAction action = new CleanupPostBuild();
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        FreeStyleBuild build = jenkinsRule.buildAndAssertSuccess(freeStyleProject);
        // Assert action Cleanup run succesfully
        assertTrue(logger.getMessages().contains(String.format("Action '%s' finished successfully", action.getName())));
        // Assert workspace doesn't exist therefore it was deleted succesfully
        assertFalse(build.getWorkspace().exists());
    }
}
