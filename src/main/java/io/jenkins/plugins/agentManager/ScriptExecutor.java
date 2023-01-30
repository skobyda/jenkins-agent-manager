package io.jenkins.plugins.agentManager;

import hudson.Launcher;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.model.*;
import hudson.tasks.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import jenkins.tasks.SimpleBuildStep;

public class ScriptExecutor extends Builder implements SimpleBuildStep {
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
        List<String> arguments = Arrays.asList("echo", "$PWD");
        Node node = Computer.currentComputer().getNode();
        ScriptNodeProperty scriptProperty = node.getNodeProperties().get(ScriptNodeProperty.class);

        if (scriptProperty != null) {
            String postBuildScript = scriptProperty.getScript();
            List parsedScript = parseScript(postBuildScript);
            launcher.launch().cmds(parsedScript).stdout(listener).join();
        }
    }
}
