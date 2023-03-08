package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.Launcher;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class ScriptExecutor extends Builder implements SimpleBuildStep {
    private final String name;

    @DataBoundConstructor
    public ScriptExecutor(String name) {
        this.name = name;
    }

    public static List<String> parseScript(String script) {
        List<String> arguments = new ArrayList<>();
        Scanner scanner = new Scanner(script);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineArguments = line.split(" ");
            for (String argument : lineArguments) {
                arguments.add(argument);
            }
        }
        return arguments;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, EnvVars env, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        BuildListener buildListener = (BuildListener) listener;

        Node node = Computer.currentComputer().getNode();
        ScriptNodeProperty scriptProperty = node.getNodeProperties().get(ScriptNodeProperty.class);
        if (scriptProperty != null) {
            List<ScriptNodeProperty.ScriptInstance> scripts = scriptProperty.getScripts();
            for (ScriptNodeProperty.ScriptInstance script : scripts) {
                if ("SUCCESS".equals(script.getTrigger()) || "FAIL".equals(script.getTrigger())) { // TODO make it a bit nicer
                    buildListener.getLogger().println("SCRIPT IS RUN 2");
                    String scriptContent = script.getScript();
                    // String language = script.getLanguage();
                    // TODO implement different handling for groovy scripts
                    List parsedScript = parseScript(scriptContent);
                    launcher.launch().cmds(parsedScript).stdout(listener).join();
                }
            }
        }
    }

    @Symbol("script")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Post-build script";
        }
    }
}
