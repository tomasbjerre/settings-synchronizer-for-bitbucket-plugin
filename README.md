# Settings Synchronizer for Bitbucket [![Build Status](https://travis-ci.org/tomasbjerre/settings-synchronizer-for-bitbucket-plugin.svg?branch=master)](https://travis-ci.org/tomasbjerre/settings-synchronizer-for-bitbucket-plugin)

Synchronize repository settings in Atlassian Bitbucket Server.

Settings can be made on repository level.

![Repo Settings](https://raw.githubusercontent.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/master/sandbox/repoadmin.png)

Settings can also be made on a global level.

![Globl Repo Settings](https://raw.githubusercontent.com/tomasbjerre/settings-synchronizer-for-bitbucket-plugin/master/sandbox/globalrepoadmin.png)

## Developer instructions
Prerequisites:

* Atlas SDK [(installation instructions)](https://developer.atlassian.com/docs/getting-started/set-up-the-atlassian-plugin-sdk-and-build-a-project).
* JDK 1.8 or newer

Generate Eclipse project:
```
atlas-compile eclipse:eclipse
```

Package the plugin:
```
atlas-package
```

Run Bitbucket, with the plugin, on localhost:
```
export MAVEN_OPTS=-Dplugin.resource.directories=`pwd`/src/main/resources
mvn bitbucket:run
```

You can also remote debug on port 5005 with:
```
atlas-debug
```

Make a release [(detailed instructions)](https://developer.atlassian.com/docs/common-coding-tasks/development-cycle/packaging-and-releasing-your-plugin):
```
mvn -B release:prepare mvn release:perform
```
