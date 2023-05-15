package io.jenkins.plugins.agentManager.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;

public interface Condition {
    /** User-friendly name of the conditio
     *
     * @return user-friendly name of the condition
     */
    String getName();

    /** Evaluates whether the given condition passes or not
     *
     * @param listener
     * @param launcher
     * @param run
     * @return true of condition passes, false if it does not
     * @throws Exception
     */
    boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) throws Exception;
}
