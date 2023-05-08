package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.PostBuild.SetOffline;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.BuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.PostBuild.NoCondition;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

public class PostBuildTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();
    @Test
    public void testPostBuildEntries() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        PostBuildCondition postBuildCondition = new NoCondition();
        PostBuildAction postBuildAction = new SetOffline();
        PostBuildEntry postBuildEntry = new PostBuildEntry(postBuildCondition, postBuildAction);

        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(postBuildEntry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        FreeStyleBuild build = jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        assertTrue(logger.getMessages().contains("Post-build actions completed"));
        jenkinsRule.assertBuildStatusSuccess(build);
    }
}
