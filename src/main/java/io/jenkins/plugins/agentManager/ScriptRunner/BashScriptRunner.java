package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.ScriptNodeProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BashScriptRunner extends ScriptRunner {
    private static List<String> parseScript(String script) {
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

    public void run(Launcher launcher, TaskListener listener, ScriptNodeProperty.ScriptInstance script) {
        String scriptContent = script.getScript();
        listener.getLogger().println(scriptContent);

        List parsedScript = parseScript(scriptContent);
        try {
            launcher.launch().cmds(parsedScript).stdout(listener).join();
        } catch (IOException e) {
            // TODO
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // TODO
            throw new RuntimeException(e);
        }
    }
}
