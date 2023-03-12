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

    public void runScript(Launcher launcher, BuildListener listener) {
        Node node = Computer.currentComputer().getNode();
        ScriptNodeProperty scriptProperty = node.getNodeProperties().get(ScriptNodeProperty.class);

        if (scriptProperty != null) {

            List<ScriptNodeProperty.ScriptInstance> scripts = scriptProperty.getScripts();
            for (ScriptNodeProperty.ScriptInstance script : scripts) {

                listener.getLogger().println(script.getTrigger());
                // TODO better getTrigger
                if (script.getTrigger() == "BEFORE" || script.getTrigger() == "FAIL" || script.getTrigger() == "SUCCESS") {

                    String scriptContent = script.getScript();
                    listener.getLogger().println(scriptContent);

                    // String language = script.getLanguage();
                    // TODO implement different handling for groovy scripts
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
        }
    }

    @Override
    public Environment setUp(AbstractBuild build, final Launcher launcher, BuildListener listener) throws IOException,
            InterruptedException {
        BuildListener buildListener = (BuildListener) listener;
        buildListener.getLogger().println("Running pre-build script");

        runScript(launcher, listener);

        // return environment
        return new AgentManagerEnvironment(launcher) {};
    }

    @Override
    public void preCheckout(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
        // TODO run setup here if we don't want user to manually select it in the build step
        // If you want your BuildWrapper extension point to always be applied to a Jenkins job, without requiring the user to select it as a build step, you can do so by overriding the preCheckout method in your BuildWrapper implementation.
        // The preCheckout method is called by Jenkins before a build begins, and allows you to modify the build environment or perform other tasks before the build actually starts. By overriding this method and implementing your desired behavior, you can ensure that your BuildWrapper extension point is always applied to the build.
    }


    @Extension
    public static final class DescriptorImpl extends Descriptor<BuildWrapper> {

        /** This human readable name is used in the configuration screen. */
        public String getDisplayName() {
            // TODO localization
            return "Pre-build script";
        }
    }

    public class AgentManagerEnvironment extends BuildWrapper.Environment {
        Launcher launcher;

        @DataBoundConstructor
        public AgentManagerEnvironment(Launcher launcher) {
            this.launcher = launcher;
        }

        @Override
        public boolean tearDown(AbstractBuild build, BuildListener listener) {
            listener.getLogger().println("Running tear-down");

            runScript(launcher, listener);

            // TODO return false if build cannot continue
            return true;
        }
    }
}