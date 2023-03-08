package io.jenkins.plugins.agentManager;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildWrapper;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PreBuildScriptWrapper extends BuildWrapper {
    @DataBoundConstructor
    public PreBuildScriptWrapper() {
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
    public Environment setUp(AbstractBuild build, final Launcher launcher, BuildListener listener) throws IOException,
            InterruptedException {
        BuildListener buildListener = (BuildListener) listener;
        buildListener.getLogger().println("Running pre-build script");

        Node node = Computer.currentComputer().getNode();
        ScriptNodeProperty scriptProperty = node.getNodeProperties().get(ScriptNodeProperty.class);

        if (scriptProperty != null) {
            List<ScriptNodeProperty.ScriptInstance> scripts = scriptProperty.getScripts();
            for (ScriptNodeProperty.ScriptInstance script : scripts) {
                if (script.getTrigger() == "BEFORE") {
                    String scriptContent = script.getScript();
                    // String language = script.getLanguage();
                    // TODO implement different handling for groovy scripts
                    List parsedScript = parseScript(scriptContent);
                    launcher.launch().cmds(parsedScript).stdout(listener).join();
                }
            }
        }

        // return environment
        return new Environment() {};
    }

    @Extension
    public static final class DescriptorImpl extends Descriptor<BuildWrapper> {

        /** This human readable name is used in the configuration screen. */
        public String getDisplayName() {
            // TODO localization
            return "Run buildstep before SCM runs";
        }
    }

}