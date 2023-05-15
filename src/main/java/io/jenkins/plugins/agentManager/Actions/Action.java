package io.jenkins.plugins.agentManager.Actions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.model.TaskListener;

public interface Action {
    /** User-friendly name of the action
     *
     * @return user-friendly name of the action
     */
    String getName();

    /** Execute the given condition. If there is a fatal failure during the condition execution, an exception with sensible error message should be thrown
     *
     * @param listener
     * @param launcher
     * @param run
     * @param computer
     * @throws Exception
     */
    void runAction(TaskListener listener, Launcher launcher, AbstractBuild run, Computer computer) throws Exception;
}
