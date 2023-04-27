package io.jenkins.plugins.agentManager.View.Conditions;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.Condition;

public abstract class Result implements Condition {
    private Boolean success;
    private Boolean failure;
    private Boolean aborted;
    private Boolean unstable;
    private Boolean notBuilt;

    public Result(Boolean success, Boolean failure, Boolean aborted, Boolean unstable, Boolean notBuilt) {
        System.out.println("Result");
        System.out.println(success);
        System.out.println(failure);
        System.out.println(aborted);
        System.out.println(unstable);
        System.out.println(notBuilt);
        this.success = success;
        this.failure = failure;
        this.aborted = aborted;
        this.unstable = unstable;
        this.notBuilt = notBuilt;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getFailure() {
        return failure;
    }

    public void setFailure(Boolean failure) {
        this.failure = failure;
    }

    public Boolean getAborted() {
        return aborted;
    }

    public void setAborted(Boolean aborted) {
        this.aborted = aborted;
    }

    public Boolean getUnstable() {
        return unstable;
    }

    public void setUnstable(Boolean unstable) {
        this.unstable = unstable;
    }

    public Boolean getNotbuild() {
        return notBuilt;
    }

    public void setNotbuild(Boolean notBuilt) {
        this.notBuilt = notBuilt;
    }

    @Override
    public String getName() {
        return "Result";
    }

    @Override
    public boolean conditionPasses(TaskListener listener, Launcher launcher, AbstractBuild run) {
        hudson.model.Result result = run.getResult();
        listener.getLogger().println("RESULT");
        listener.getLogger().println(result.toString());

        // result is only set after all post-build actions have run, as post-build actions also may fail
        // so the result == null means that build has not encountered any error yet
        // if (result == null) {
        //     return getScriptsByType("SUCCESS", listener);
        // }
        // Mentioned above doesn't fit anymore. That used to work only if we extend BuildWrapper or BuildStep. Extending BuildListener we have a meaningful output

        Boolean ret = false;
        if (getSuccess())
            ret |= result == hudson.model.Result.SUCCESS;
        if (getFailure())
            ret |= result == hudson.model.Result.FAILURE;
        if (getAborted())
            ret |= result == hudson.model.Result.ABORTED;
        if (getUnstable())
            ret |= result == hudson.model.Result.UNSTABLE;
        if (getNotbuild())
            ret |= result == hudson.model.Result.NOT_BUILT;

        return ret;
    }
}
