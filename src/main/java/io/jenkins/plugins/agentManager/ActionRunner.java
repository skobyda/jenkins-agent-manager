package io.jenkins.plugins.agentManager;

import hudson.Launcher;
import hudson.model.*;
import io.jenkins.plugins.agentManager.ScriptRunner.BashScriptRunner;
import io.jenkins.plugins.agentManager.ScriptRunner.GroovyScriptRunner;
import io.jenkins.plugins.agentManager.ScriptRunner.ScriptRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActionRunner {
    private static List <ScriptNodeProperty.ScriptInstance> getRelevantScripts(Run<?,?> run, TaskListener listener, Boolean isPreBuild) {
        if (isPreBuild) {
            return getScriptsMatchingTriggers("BEFORE");
        }

        Result result = run.getResult();

        // result is only set after all post-build actions have run, as post-build actions also may fail
        // so the result == null means that build has not encountered any error yet
        if (result == null) {
            return getScriptsMatchingTriggers("SUCCESS");
        }

        if (result == Result.FAILURE) {
            return getScriptsMatchingTriggers("FAILURE");
        }

        // TODO
        // result == Result.UNSTABLE or result == Result.ABRUPTED
        return new ArrayList<>();
    }

    private static List <ScriptNodeProperty.ScriptInstance> getScriptsMatchingTriggers(String trigger) {
        List <ScriptNodeProperty.ScriptInstance> filtered = new ArrayList<>();
        List <ScriptNodeProperty.ScriptInstance> scripts = getAllNodeScripts();

        for (ScriptNodeProperty.ScriptInstance script : scripts) {
            String scriptTrigger = script.getTrigger();

            if (trigger.equals(scriptTrigger)) {
                filtered.add(script);
            }
        }

        return filtered;
    }

    private static List <ScriptNodeProperty.ScriptInstance> getAllNodeScripts() {
        Node node = Computer.currentComputer().getNode();
        ScriptNodeProperty scriptProperty = node.getNodeProperties().get(ScriptNodeProperty.class);
        // TODO null handling
        // TODO, test also on project which do not have node assigned for computer which doesn't have agentManager setups
        return scriptProperty.getScripts();
    }

    public static void act(Launcher launcher, TaskListener listener, Run<?,?>  run, Boolean isPreBuild) {
        List <ScriptNodeProperty.ScriptInstance> scripts = getRelevantScripts(run, listener, isPreBuild);

        for (ScriptNodeProperty.ScriptInstance script : scripts) {
            // TODO runScript should be a property of script?
            runScript(launcher, listener, script);
        }
    }

    private static void runScript(Launcher launcher, TaskListener listener, ScriptNodeProperty.ScriptInstance script) {
        // TODO
        // Use factory here
        ScriptRunner runner = null;
        listener.getLogger().println(script.getLanguage());
        if ("BASH".equals(script.getLanguage())) {
            runner = new BashScriptRunner();
        } else if ("GROOVY".equals(script.getLanguage())) {
            runner = new GroovyScriptRunner();
        }
        listener.getLogger().println(runner);

        runner.run(launcher, listener, script);
    }
}