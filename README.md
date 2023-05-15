# Adaptive Jenkins Agent Plugin

This is the Adaptive Jenkins Agent Plugin for Jenkins.

## Build

To build it and spawn tests:

    $ mvn clean install 

# Running the plugin locally

To run the plugin locally:

    $ mvn hpi:run -Dport="8081" 

This will create a Jenkins instance at "localhost:8081". You can use your browser to log in there

# Configuring build actions of an agent

1. Create an agent node, as described in [jenkins documentation](https://www.jenkins.io/doc/book/managing/nodes/)
2. Configure a label for such agent
3. After adding an agent "My agent" to the Jenkins user interface, go to
4. Dashboard -> Nodes -> My Agent
5. Then click on the "Configure", scroll down, check "Configure build actions"

Now you can add build actions

# Running a build

If you want to try it out with a dummy project, you can try it with this freestyle project:

1. Click on "Create new Item" and choose freestyle project and click "OK"
2. "Restrict where this project can be run" and input the label you have chosen for your agent
3. In the "Build" section add a build step "Execute shell"
4. You can put some arbitrary shell which will represent the build, e.g. `echo 'hello'` for succesful build or `rm \dev\null` for a failed build
5. Save the project

Then to run a build:

1. In the project's page, click on "Build now"
2. You can introspect logs to see if agent's actions were spawned

Create a freestyle project, as described in [jenkins documentation](https://www.jenkins.io/doc/book/managing/nodes/)
