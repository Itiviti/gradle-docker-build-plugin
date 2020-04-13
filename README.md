# gradle-docker-build-plugin
[![Build Status](https://dev.azure.com/ngyukman/ngyukman/_apis/build/status/Itiviti.gradle-docker-build-plugin?branchName=master)](https://dev.azure.com/ngyukman/ngyukman/_build/latest?definitionId=2&branchName=master)  [![GitHub license](https://img.shields.io/github/license/Itiviti/gradle-docker-build-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Plugin for building docker images

## Usage
* plugin does not add any task, it is useful if you would like to add build tags manually
* All images built can be pushed by `publish` task

To apply the plugin:
```groovy
plugins {
    id 'com.itiviti.docker-build' version '1.0.0'
}
```

or

```groovy
buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath 'com.itiviti.gradle:gradle-docker-build-plugin:1.0.0'
    }
}

apply 'com.itiviti.docker-build'
```

or

```groovy


docker {
    registry = 'your registry host' // optional
    imageName = 'your image name'   // applies to all DockerBuildTask if set
}

tasks.register('dockerBuildLTS', com.itiviti.gradle.DockerBuildTask) {
    tags = [ '3.0' ]
    workingDir './3.0'
}

tasks.register('dockerBuildLatest', com.itiviti.gradle.DockerBuildTask) {
    tags = [ 'latest', '4.1' ]
    workingDir './4.1'
}
```