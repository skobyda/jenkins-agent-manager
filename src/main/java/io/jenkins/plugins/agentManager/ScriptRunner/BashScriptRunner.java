package io.jenkins.plugins.agentManager.ScriptRunner;

import hudson.Launcher;
import hudson.model.TaskListener;
import io.jenkins.plugins.agentManager.View.ActionInstance;

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

    public int executeScript(Launcher launcher, TaskListener listener, String scriptContent) throws IOException, InterruptedException {
        listener.getLogger().println(scriptContent);

        List parsedScript = parseScript(scriptContent);
        return launcher.launch().cmds(parsedScript).stdout(listener).join();
    }

    public void run(Launcher launcher, TaskListener listener, ActionInstance.Action.CustomScript script) {
        String scriptContent = script.getScriptText();

        try {
            executeScript(launcher, listener, scriptContent);
        } catch (IOException e) {
            // TODO e.printStackTrace(listener.fatalError());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            // TODO
            throw new RuntimeException(e);
        }
    }

    public boolean evaluateCondition(Launcher launcher, TaskListener listener, ActionInstance.Condition.Script script) {
        String scriptContent = script.getScriptText();
        listener.getLogger().println(scriptContent);

        boolean condition = true;
        try {
            if (executeScript(launcher, listener, scriptContent) != 0)
                condition = false;
        } catch (IOException e) {
            // TODO
            condition = false;
        } catch (InterruptedException e) {
        // TODO
            condition = false;
        }

        return condition;
    }
}
