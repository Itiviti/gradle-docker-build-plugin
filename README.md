# gradle-docker-build-plugin
[![Build Status](https://travis-ci.org/Itiviti/gradle-docker-build-plugin.svg?branch=master)](https://travis-ci.org/Itiviti/gradle-docker-build-plugin)  [![GitHub license](https://img.shields.io/github/license/Itiviti/gradle-opencover-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0)

Plugin for building docker images

## Usage
* `docker-build` does not add any task, it is useful if you would like to add build tags manually
* images can be pushed by `publish` task

```groovy
apply 'docker-base'

docker {
    registry = 'your registry host' // optional
    imageName = 'your image name' // applies to all DockerBuildTask if set
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