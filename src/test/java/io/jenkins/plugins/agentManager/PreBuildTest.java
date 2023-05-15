package io.jenkins.plugins.agentManager;

import hudson.model.*;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.PreBuild.SetOfflinePreBuild;
import io.jenkins.plugins.agentManager.Actions.PreBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.BuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.PreBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.PreBuild.NoConditionPreBuild;
import io.jenkins.plugins.agentManager.Conditions.PreBuildCondition;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

public class PreBuildTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    private FreeStyleProject setupProject(List<BuildEntry> buildEntryList) throws Exception {
        NodePropertyImpl nodeProperty = new NodePropertyImpl(buildEntryList);

        DumbSlave slave = jenkinsRule.createSlave();
        slave.setNodeProperties(Arrays.asList(nodeProperty));

        FreeStyleProject project = jenkinsRule.createFreeStyleProject();
        project.setAssignedNode(slave);

        return project;
    }

    @Test
    public void testPreBuildEntries() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        PreBuildCondition preBuildCondition = new NoConditionPreBuild();
        PreBuildAction preBuildAction = new SetOfflinePreBuild();
        PreBuildEntry preBuildEntry = new PreBuildEntry(preBuildCondition, preBuildAction);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(preBuildEntry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        FreeStyleBuild build = jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        assertTrue(logger.getMessages().contains("PreBuild actions completed"));
        jenkinsRule.assertBuildStatusSuccess(build);
    }
}
