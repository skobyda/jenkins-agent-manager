package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.DuringBuild.SetOffline;
import io.jenkins.plugins.agentManager.Actions.DuringBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.BuildEntry;
import io.jenkins.plugins.agentManager.BuildEntries.DuringBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.DuringBuild.NoCondition;
import io.jenkins.plugins.agentManager.Conditions.DuringBuildCondition;
import io.jenkins.plugins.agentManager.Utils.Time;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DuringBuildTest {
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
    public void testDuringBuildEntriesInLoop() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        DuringBuildCondition duringBuildCondition = new NoCondition();
        DuringBuildAction duringBuildAction = new SetOffline();
        DuringBuildEntry duringBuildEntry = new DuringBuildEntry(1L, Time.getMillisecondsString(), true, duringBuildCondition, duringBuildAction);
        FreeStyleProject freeStyleProject = setupProject(Arrays.asList(duringBuildEntry));

        FreeStyleBuild build = jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        // We set action to run in loop until the build finishes, so it should have been run more than once
        long countActionPerformed = 0;
        for (String message : logger.getMessages()) {
            if (message.equals(String.format("Performed action '%s' with condition '%s' during the build", duringBuildAction.getName(), duringBuildCondition.getName()))) {
                countActionPerformed++;
            }
        }

        assertTrue(countActionPerformed > 1);
        jenkinsRule.assertBuildStatusSuccess(build);
    }

    @Test
    public void testDuringBuildEntriesRunOnce() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        DuringBuildCondition duringBuildCondition = new NoCondition();
        DuringBuildAction duringBuildAction = new SetOffline();
        DuringBuildEntry duringBuildEntry = new DuringBuildEntry(1L, Time.getMillisecondsString(), false, duringBuildCondition, duringBuildAction);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(duringBuildEntry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        FreeStyleBuild build = jenkinsRule.buildAndAssertSuccess(freeStyleProject);

        // We set action to run in loop until the build finishes, so it should have been run more than once
        long countActionPerformed = 0;
        for (String message : logger.getMessages()) {
            if (message.equals(String.format("Performed action '%s' with condition '%s' during the build", duringBuildAction.getName(), duringBuildCondition.getName()))) {
                countActionPerformed++;
            }
        }

        assertEquals(countActionPerformed, 1);
        jenkinsRule.assertBuildStatusSuccess(build);
    }
}
