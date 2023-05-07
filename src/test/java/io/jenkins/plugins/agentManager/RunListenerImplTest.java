package io.jenkins.plugins.agentManager;

import hudson.model.*;
import hudson.slaves.DumbSlave;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.LoggerRule;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.logging.Level;

import static org.mockito.Mockito.*;

public class RunListenerImplTest {
    @Mock
    private Run run;
    @Mock
    private ActionRunner actionRunner;
    @Mock
    private Computer computer;

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Rule
    public LoggerRule logger = new LoggerRule();

    @Test
    public void testOnCompleted() throws Exception {
        logger.capture(3).record("io.jenkins.plugins.agentManager.RunListenerImpl", Level.ALL);
        // JenkinsRule jenkinsRule;
        // DumbSlave agent = jenkinsRule.createSlave();
        Computer computer = mock(Computer.class);
        ActionRunner actionRunner = mock(ActionRunner.class);
        RunListenerImpl myRunListener = new RunListenerImpl();

        // Set up the mock Run object to return expected values
        // :w
        // when(Computer.currentComputer()).thenReturn(computer);
        doNothing().when(actionRunner).actPostBuild(computer);

        // Call the onCompleted method with the mock Run object
        myRunListener.onCompleted(run, null);

        // Assert that the RunListenerImpl object correctly handles the Run object
        // and performs the expected actions in the onCompleted method
        // For example, check that the build was marked as successful in a database or external system
    }

}
