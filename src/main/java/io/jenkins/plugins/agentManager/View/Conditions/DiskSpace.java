package io.jenkins.plugins.agentManager.View.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.ComputerSet;
import hudson.model.TaskListener;
import hudson.node_monitors.DiskSpaceMonitor;
import hudson.node_monitors.DiskSpaceMonitorDescriptor;
import io.jenkins.plugins.agentManager.View.Condition;

public abstract class DiskSpace implements Condition {
    private final long space;
    private final String unit;

    public long getSpace() {
        return space;
    }

    public String getUnit() {
        return unit;
    }

    public DiskSpace(long space, String unit) {
        this.space = space;
        this.unit = unit;
    }

    @Override
    public String getName() {
        return "DiskSpace";
    }

    @Override
    public boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) {
        listener.getLogger().println("DiskSpace");
        listener.getLogger().println(getSpace());
        listener.getLogger().println(getUnit());
        long thresholdSpaceMB;
        if (getUnit().equals("GB"))
            thresholdSpaceMB = getSpace() * 1024; // make it MB
        else
            thresholdSpaceMB = getSpace();

        // Move to some upper function and share this variable
        Computer computer = Computer.currentComputer();
        DiskSpaceMonitor diskSpaceMonitor = ComputerSet.getMonitors().get(DiskSpaceMonitor.class);
        DiskSpaceMonitorDescriptor.DiskSpace freeSpace = diskSpaceMonitor.getFreeSpace(computer);
        long availableSpaceInMB = freeSpace.getFreeSize() * 1024 * 1024;

        return thresholdSpaceMB <= availableSpaceInMB;
    }
}
