package io.jenkins.plugins.agentManager.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.ComputerSet;
import hudson.model.TaskListener;
import hudson.node_monitors.DiskSpaceMonitor;
import hudson.node_monitors.DiskSpaceMonitorDescriptor;

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
        long thresholdSpaceMB;
        if (getUnit().equals("GB"))
            thresholdSpaceMB = getSpace() * 1024; // make it MB
        else
            thresholdSpaceMB = getSpace();

        // Move to some upper function and share this variable
        Computer computer = Computer.currentComputer();
        DiskSpaceMonitor diskSpaceMonitor = ComputerSet.getMonitors().get(DiskSpaceMonitor.class);
        DiskSpaceMonitorDescriptor.DiskSpace freeSpace = diskSpaceMonitor.getFreeSpace(computer);
        String availableSpaceInGB = freeSpace.getGbLeft();
        Float availableSpaceInGBFloat = Float.parseFloat(availableSpaceInGB);
        Long availableSpaceInMB = Math.round(availableSpaceInGBFloat / 1024.0);

        return thresholdSpaceMB <= availableSpaceInMB;
    }
}
