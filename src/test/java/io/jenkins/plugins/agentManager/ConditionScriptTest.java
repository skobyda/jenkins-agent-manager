package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleProject;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.Actions.PostBuild.ShellScript;
import io.jenkins.plugins.agentManager.Actions.PostBuildAction;
import io.jenkins.plugins.agentManager.BuildEntries.PostBuildEntry;
import io.jenkins.plugins.agentManager.Conditions.PostBuild.Script;
import io.jenkins.plugins.agentManager.Conditions.PostBuildCondition;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

public class ConditionScriptTest {
    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testScriptPass() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        // Spawn script which will return zero value (thus pass). A simple echo script will do fine
        PostBuildCondition condition = new Script("echo 'passing condition'");
        PostBuildAction action = new ShellScript("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        jenkinsRule.buildAndAssertSuccess(freeStyleProject);
        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as true", condition.getName())));
    }

    @Test
    public void testScriptFail() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        // Spawn script which will return non-zero value (thus fail) A simple script "false" will do fine
        PostBuildCondition condition = new Script("false");
        PostBuildAction action = new ShellScript("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        jenkinsRule.buildAndAssertSuccess(freeStyleProject);
        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as false", condition.getName())));
    }

    @Test
    public void testScriptMultiline() throws Exception {
        logger.capture(42).record(RunListenerImpl.class, Level.ALL);

        // Spawn script which will return non-zero value (thus fail) A simple script "false" will do fine
        PostBuildCondition condition = new Script("true;\ntrue;\nfalse");
        PostBuildAction action = new ShellScript("echo 'hello tests'");
        PostBuildEntry entry = new PostBuildEntry(condition, action);
        DumbSlave slave = TestHelper.setupSlave(jenkinsRule, Arrays.asList(entry));
        FreeStyleProject freeStyleProject = TestHelper.setupProject(jenkinsRule, slave);

        jenkinsRule.buildAndAssertSuccess(freeStyleProject);
        assertTrue(logger.getMessages().contains(String.format("Condition '%s' evaluated as false", condition.getName())));
    }
}
