package io.jenkins.plugins.agentManager;

import hudson.model.FreeStyleProject;
import hudson.slaves.DumbSlave;
import io.jenkins.plugins.agentManager.BuildEntries.BuildEntry;
import org.jvnet.hudson.test.JenkinsRule;

import java.util.Arrays;
import java.util.List;


public class TestHelper {
    public static DumbSlave setupSlave(JenkinsRule jenkinsRule, List<BuildEntry> buildEntryList) throws Exception {
        NodePropertyImpl nodeProperty = new NodePropertyImpl(buildEntryList);

        DumbSlave slave = jenkinsRule.createSlave();
        slave.setNodeProperties(Arrays.asList(nodeProperty));

        return slave;
    }

    public static FreeStyleProject setupProject(JenkinsRule jenkinsRule, DumbSlave slave) throws Exception {
        FreeStyleProject project = jenkinsRule.createFreeStyleProject();
        project.setAssignedNode(slave);

        return project;
    }
}
